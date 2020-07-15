package com.chaolifang.mongo.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * 日志表没有设置id字段 发现insert的时候自动插入了一个mongo自己生成的id
 */
@Data
@Document(collection = "book_log")
public class BookLogMongo {
    @Field("bookId")
    private String bookId;

    @Field("userid")
    private String userid;

    @Field("username")
    private String username;
    @Field("comment")
    private String comment;
    @Field("insertTime")
    private Date insertTime = new Date();


}
