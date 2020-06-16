package com.chaolifang.controller;

import com.chaolifang.domain.BookManagerDTO;
import com.chaolifang.dto.BookManagerSearchDTO;
import com.chaolifang.result.DataTablesResult;
import com.chaolifang.service.BookManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/bookManager")
public class BookManagerController {
    @Autowired
    private BookManagerService bookManagerService;

    @RequestMapping(value = "/getBookManagerList",method = RequestMethod.POST)
    public DataTablesResult getBookManagerList(@RequestBody BookManagerSearchDTO searchDTO){
        return bookManagerService.getBookManagerList(searchDTO);
    }

    @RequestMapping(value = "/addBookManager",method = RequestMethod.POST)
    public int addBookManager(BookManagerDTO dto){
        return bookManagerService.addBookManager(dto);
    }
}
