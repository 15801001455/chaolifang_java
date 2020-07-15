package com.chaolifang.service.mongo;

import com.chaolifang.dto.BookSearchDTO;
import com.chaolifang.enuma.BorrowStatusEnum;
import com.chaolifang.mongo.document.BookLogMongo;
import com.chaolifang.mongo.document.BookMongo;
import com.chaolifang.result.BaseResult;
import com.chaolifang.result.DataTablesResult;
import com.chaolifang.util.ToolDate;
import com.mongodb.client.result.DeleteResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookMongoService {

    @Autowired
    private MongoTemplate bookMapper;

    public DataTablesResult getBookList(BookSearchDTO searchDTO) {
        Integer pageIndex = searchDTO.getPageIndex() == null ? 1 : searchDTO.getPageIndex();
        Integer pageSize = searchDTO.getPageSize() == null ? 10 : searchDTO.getPageSize();
        searchDTO.setPageIndex((pageIndex - 1) * pageSize); // mysql中是起始的行是多少行
        searchDTO.setPageSize(pageSize);
        Date borrowTimeStart = searchDTO.getBorrowTimeStart();
        Date borrowTimeEnd = searchDTO.getBorrowTimeEnd();
        Criteria criteria = new Criteria();
        criteria.and("isDel").is(1);

        /*if (borrowTimeStart != null) {
            criteria.andOperator(Criteria.where("borrowTime").gte(ToolDate.formatDateByFormat(borrowTimeStart, "yyyy-MM-dd") + " 00:00:00"),
                    Criteria.where("borrowTime").lte(ToolDate.formatDateByFormat(borrowTimeEnd, "yyyy-MM-dd") + " 23:59:59"));
        }*/
        if(borrowTimeStart != null && borrowTimeEnd != null){
            criteria.and("borrowTime").gte(ToolDate.parseDate(ToolDate.formatDateByFormat(borrowTimeStart, "yyyy-MM-dd") + " 00:00:00","yyyy-MM-dd HH:mm:ss"))
            .lte(ToolDate.parseDate(ToolDate.formatDateByFormat(borrowTimeEnd, "yyyy-MM-dd") + " 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }else if(borrowTimeStart != null && borrowTimeEnd == null){
            criteria.and("borrowTime").gte(ToolDate.parseDate(ToolDate.formatDateByFormat(borrowTimeStart, "yyyy-MM-dd") + " 00:00:00","yyyy-MM-dd HH:mm:ss"));
        }else if(borrowTimeStart == null && borrowTimeEnd != null){
            criteria.and("borrowTime").lte(ToolDate.parseDate(ToolDate.formatDateByFormat(borrowTimeEnd, "yyyy-MM-dd") + " 23:59:59","yyyy-MM-dd HH:mm:ss"));
        }

        String borrowPerson = searchDTO.getBorrowPerson();
        if(borrowPerson != null && !"".equals(borrowPerson)){
            criteria.and("borrowPerson").regex(borrowPerson);
        }
        Integer borrowStatus = searchDTO.getBorrowStatus();
        if(borrowStatus != null && borrowStatus > 0){
            criteria.and("borrowStatus").is(borrowStatus);
        }
        Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
        long count = bookMapper.count(Query.query(criteria),BookMongo.class);
        List<BookMongo> list = bookMapper.find(Query.query(criteria).with(pageable),BookMongo.class);
        DataTablesResult result = new DataTablesResult();
        result.setRecordsTotal(Integer.valueOf(count + ""));
        result.setData(list);
        result.setResult("ok");
        return result;
    }

    @Transactional // 加了事务注解后即使出现运行期异常(比如1/0)，数据也不会插入数据库
    public BaseResult addBook(BookMongo dto) {
        String id = dto.getId();
        if(StringUtils.isEmpty(id)){
            return BaseResult.notOk("书籍编号不能为空");
        }
        BookMongo book = bookMapper.findById(id, BookMongo.class);
        if (book != null) {
            return BaseResult.notOk("书籍编号重复");
        }
        dto.setBorrowStatus(BorrowStatusEnum.未出借.getIndex());
        BookMongo count = bookMapper.insert(dto);

        // 插入日志 现在是不知道谁插入的数据
        Subject subject = SecurityUtils.getSubject();
        String userName = subject.getPrincipal().toString();
        BookLogMongo bookLogMongo = new BookLogMongo();
        bookLogMongo.setUserid("1");
        bookLogMongo.setUsername(userName);
        bookLogMongo.setComment("初始化录入书籍");
        bookLogMongo.setBookId(id);
        BookLogMongo bookLogInsert = bookMapper.insert(bookLogMongo);
        if (count != null && bookLogInsert != null) {
            return BaseResult.ok();
        }
        return BaseResult.notOk("新增书籍失败");
    }

    public BaseResult updateBook(BookMongo dto) {
        BookMongo book = bookMapper.findById(dto.getId(), BookMongo.class);
        if (book == null) {
            return BaseResult.notOk("书籍编号不存在");
        }
        Integer borrowStatus = dto.getBorrowStatus();
        if(borrowStatus == BorrowStatusEnum.已出借.getIndex()){
            String borrowPerson = dto.getBorrowPerson();
            if(StringUtils.isEmpty(borrowPerson)){
                return BaseResult.notOk("请填写借阅人");
            }
        }
        Date borrowTime = dto.getBorrowTime();
        Date returnTime = dto.getReturnTime();
        if(borrowTime.compareTo(returnTime) > 0){
            return BaseResult.notOk("借阅时间不能大于归还时间");
        }
        dto.setUpdateTime(new Date());
        // 这里能看出来insert和save的区别了 但是不知道insertTime会不会又更新了 可以试试一会儿
        BookMongo save = bookMapper.save(dto);
        // 插入日志 现在是不知道谁插入的数据
        Subject subject = SecurityUtils.getSubject();
        String userName = subject.getPrincipal().toString();
        BookLogMongo bookLogMongo = new BookLogMongo();
        bookLogMongo.setUserid("1");
        bookLogMongo.setUsername(userName);
        //操作日志 操作人id:userid,操作人姓名:username,
        String comment = save.toString();
        bookLogMongo.setComment(comment);
        bookLogMongo.setBookId(dto.getId());
        BookLogMongo bookLogInsert = bookMapper.insert(bookLogMongo);
        if (save != null && bookLogInsert != null) {
            return BaseResult.ok();
        }
        return BaseResult.notOk("保存书籍失败");
    }

    public BaseResult deleteBook(String id) {
        BookMongo book = bookMapper.findById(id, BookMongo.class);
        if (book == null) {
            return BaseResult.notOk("书籍不存在");
        }
        Integer borrowStatus = book.getBorrowStatus();
        if (borrowStatus == null) {
            return BaseResult.notOk("书籍借阅状态没有,请联系管理员");
        }
        if (BorrowStatusEnum.已出借.getIndex() == borrowStatus) {
            return BaseResult.notOk("书籍已经借阅,无法删除,借阅人:" + book.getBorrowPerson());
        }
        book.setIsDel(9);//伪删除数据
        book.setUpdateTime(new Date());
        BookMongo save = bookMapper.save(book);
        if (save != null) {
            return BaseResult.ok();
        }
        return BaseResult.notOk("删除失败");
    }
}
