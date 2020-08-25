package com.chaolifang.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Book {
    private String id;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date borrowTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date returnTime;
    private String borrowPerson;
    private Integer borrowStatus;
    private String mark;
    private Date insertTime = new Date();
    private Date updateTime;
}