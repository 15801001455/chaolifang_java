package com.chaolifang.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class BookLog {
    private String id;//书籍编号
    private String name;//书籍名称
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
