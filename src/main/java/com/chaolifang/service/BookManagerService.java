package com.chaolifang.service;

import com.chaolifang.dao.BookManagerMapper;
import com.chaolifang.domain.BookManagerDTO;
import com.chaolifang.dto.BookManagerSearchDTO;
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
        Integer pageSize = searchDTO.getPageSize() == null ? 20 : searchDTO.getPageSize();
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

    public int addBookManager(BookManagerDTO dto) {
        return bookManagerMapper.addBookManager(dto);
    }
}
