package com.chaolifang.controller;

import com.chaolifang.config.context.CurrentUserInfo;
import com.chaolifang.config.context.UserContext;
import com.chaolifang.dto.BookSearchDTO;
import com.chaolifang.pojo.Book;
import com.chaolifang.pojo.BookLog;
import com.chaolifang.result.BaseResult;
import com.chaolifang.result.DataTablesResult;
import com.chaolifang.service.BookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/book")
@Api(tags = "书籍操作")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/getBookList", method = RequestMethod.POST)
    public DataTablesResult<List<Book>> getBookList(@RequestBody BookSearchDTO searchDTO) {
        return bookService.getBookList(searchDTO);
    }

    @RequestMapping(value = "/getBookLogList", method = RequestMethod.GET)
    public BaseResult<List<BookLog>> getBookLogList(@RequestParam String id) {
        return bookService.getBookLogList(id);
    }

    @Transactional
    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public BaseResult<String> addBook(@RequestBody Book dto) {
        CurrentUserInfo user = UserContext.getUser();//这行就是测试拦截器里能否为需要登录的用户设置上当前的用户信息
        try {
            return bookService.addBook(dto);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return BaseResult.notOk(e.getMessage());
        }

    }

    @RequestMapping(value = "/updateBook", method = RequestMethod.POST)
    public BaseResult<String> updateBook(@RequestBody Book dto) {
        return bookService.updateBook(dto);
    }

    @RequestMapping(value = "/deleteBook", method = RequestMethod.GET)
    public BaseResult<String> deleteBook(@RequestParam String id) {
        return bookService.deleteBook(id);
    }
}
