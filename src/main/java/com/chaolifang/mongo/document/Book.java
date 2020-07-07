package com.chaolifang.mongo.document;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "book")
public class Book {
    private String id;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Field("borrowTime")
    private Date borrowTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Field("returnTime")
    private Date returnTime;
    @Field("borrowPerson")
    private String borrowPerson;
    @Field("borrowStatus")
    private Integer borrowStatus;
    private String mark;
    @Field("insertTime")
    private Date insertTime = new Date();
    @Field("updateTime")
    private Date updateTime;
}
