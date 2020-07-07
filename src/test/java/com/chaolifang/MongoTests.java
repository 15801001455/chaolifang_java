package com.chaolifang;

import com.chaolifang.mongo.document.Book;
import com.chaolifang.util.ToolDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@SpringBootTest
public class MongoTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
        System.out.println("会启动整个项目");
    }

    @Test
    void testDropBook() {
        mongoTemplate.dropCollection(Book.class);
    }

    @Test
    void testInsertBook() {
        for (int i = 0; i < 20; i++) {
            Book book = new Book();
            book.setId(i + "");
            book.setName("儿歌" + i);
            Book insert = mongoTemplate.insert(book);
        }
    }

    @Test
    void testQueryBook() {
        //精确查找
        //List<Book> books = mongoTemplate.find(Query.query(Criteria.where("name").is("儿歌")), Book.class);
        //模糊查找
        //List<Book> books = mongoTemplate.find(Query.query(Criteria.where("name").regex("儿歌")), Book.class);
        //分页查找 PageRequest.of(page, size); 注意第一页的话page是0 第二页page传1
        Pageable pageable = PageRequest.of(1, 10);
        List<Book> books = mongoTemplate.find(Query.query(Criteria.where("name").regex("儿歌")).with(pageable), Book.class);
        //根据id查询的话下面两种都能查出来数据 _id或者id
        //List<Book> books = mongoTemplate.find(Query.query(Criteria.where("_id").is("1")), Book.class);
        //List<Book> books = mongoTemplate.find(Query.query(Criteria.where("id").is("1")), Book.class);
        //就和mysql中查询总数用
        long count = mongoTemplate.count(Query.query(Criteria.where("name").regex("儿歌")),Book.class);
        System.out.println("查询条件总数:" + count);
        for (Book book : books) {
            System.out.println(book.getId() + "," + book.getName() + "," + ToolDate.formatDateByFormat(book.getInsertTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        Page<Book> page = new PageImpl(books, pageable, count);

    }

    /**
     * 根据条件查找并删除
     */
    @Test
    void findAllAndRemove() {
        Query query = new Query();
        List<Book> books = mongoTemplate.findAllAndRemove(query, Book.class);
        for (Book book : books) {
            System.out.println(book.getId() + "," + book.getName() + "," + ToolDate.formatDateByFormat(book.getInsertTime(), "yyyy-MM-dd HH:mm:ss"));
        }
    }
}
