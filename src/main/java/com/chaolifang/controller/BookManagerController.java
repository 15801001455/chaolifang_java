package com.chaolifang.controller;

import com.chaolifang.domain.BookManagerDTO;
import com.chaolifang.dto.BookManagerSearchDTO;
import com.chaolifang.result.BaseResult;
import com.chaolifang.result.DataTablesResult;
import com.chaolifang.service.BookManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public BaseResult addBookManager(@RequestBody BookManagerDTO dto){
        return bookManagerService.addBookManager(dto);
    }

    @RequestMapping(value = "/deleteBookManager",method = RequestMethod.GET)
    public BaseResult deleteBookManager(@RequestParam String id){
        return bookManagerService.deleteBookManager(id);
    }
}
