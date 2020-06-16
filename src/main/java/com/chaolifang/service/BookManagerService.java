package com.chaolifang.service;

import com.chaolifang.dao.BookManagerMapper;
import com.chaolifang.domain.BookManagerDTO;
import com.chaolifang.dto.BookManagerSearchDTO;
import com.chaolifang.enuma.BorrowStatusEnum;
import com.chaolifang.result.BaseResult;
import com.chaolifang.result.DataTablesResult;
import com.chaolifang.util.ToolDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookManagerService {
    @Autowired
    private BookManagerMapper bookManagerMapper;

    // 目前先不带查询条件
    public DataTablesResult getBookManagerList(BookManagerSearchDTO searchDTO){
        Integer pageIndex = searchDTO.getPageIndex() == null ? 1 : searchDTO.getPageIndex();
        Integer pageSize = searchDTO.getPageSize() == null ? 10 : searchDTO.getPageSize();
        searchDTO.setPageIndex((pageIndex-1) * pageSize); // mysql中是起始的行是多少行
        searchDTO.setPageSize(pageSize);
        Date borrowTimeStart = searchDTO.getBorrowTimeStart();
        Date borrowTimeEnd = searchDTO.getBorrowTimeEnd();
        if(borrowTimeStart != null){
            searchDTO.setBorrowTimeStartStr(ToolDate.formatDateByFormat(borrowTimeStart,"yyyy-MM-dd HH:mm:ss"));
        }
        if(borrowTimeEnd != null){
            searchDTO.setBorrowTimeEndStr(ToolDate.formatDateByFormat(borrowTimeEnd,"yyyy-MM-dd HH:mm:ss"));
        }
        Integer count = bookManagerMapper.getBookManagerCount(searchDTO);
        List<BookManagerDTO> list = bookManagerMapper.getBookManagerList(searchDTO);
        DataTablesResult result = new DataTablesResult();
        result.setRecordsTotal(count);
        result.setData(list);
        result.setResult("ok");
        return result;
    }

    public BaseResult addBookManager(BookManagerDTO dto) {
        BookManagerDTO book = bookManagerMapper.selectById(dto.getId());
        if(book != null){
            return BaseResult.notOk("书籍编号重复");
        }
        dto.setBorrowStatus(BorrowStatusEnum.未出借.getIndex());
        Integer count = bookManagerMapper.addBookManager(dto);
        if(count >= 0){
            return BaseResult.ok();
        }
        return BaseResult.notOk("新增书籍失败");
    }

    public BaseResult deleteBookManager(String id) {
        BookManagerDTO book = bookManagerMapper.selectById(id);
        if(book == null){
            return BaseResult.notOk("书籍不存在");
        }
        Integer borrowStatus = book.getBorrowStatus();
        if(borrowStatus == null){
            return BaseResult.notOk("书籍借阅状态没有,请联系管理员");
        }
        if(BorrowStatusEnum.已出借.getIndex() == borrowStatus){
            return BaseResult.notOk("书籍已经借阅,无法删除,借阅人:" + book.getBorrowPerson());
        }
        Integer count = bookManagerMapper.deleteById(id);
        if(count >= 0){
            return BaseResult.ok();
        }
        return BaseResult.notOk("删除失败");
    }
}
