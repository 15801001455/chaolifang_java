package com.chaolifang.controller.mongo;

import com.chaolifang.dto.BookSearchDTO;
import com.chaolifang.mongo.document.BookMongo;
import com.chaolifang.result.BaseResult;
import com.chaolifang.result.DataTablesResult;
import com.chaolifang.service.mongo.BookMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("BookMongoController")
@RequestMapping(value = "/api/mongo/book")
public class BookMongoController {
    @Autowired
    private BookMongoService bookService;

    @RequestMapping(value = "/getBookList",method = RequestMethod.POST)
    public DataTablesResult getBookList(@RequestBody BookSearchDTO searchDTO){
        return bookService.getBookList(searchDTO);
    }

    @RequestMapping(value = "/addBook",method = RequestMethod.POST)
    public BaseResult addBook(@RequestBody BookMongo dto){
        return bookService.addBook(dto);
    }

    @RequestMapping(value = "/updateBook",method = RequestMethod.POST)
    public BaseResult updateBook(@RequestBody BookMongo dto){
        return bookService.updateBook(dto);
    }

    @RequestMapping(value = "/deleteBook",method = RequestMethod.GET)
    public BaseResult deleteBook(@RequestParam String id){
        return bookService.deleteBook(id);
    }
}
