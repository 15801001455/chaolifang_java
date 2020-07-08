package com.chaolifang.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_book")
public class Book {
    private String id;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField("borrowTime")
    private Date borrowTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField("returnTime")
    private Date returnTime;
    @TableField("borrowPerson")
    private String borrowPerson;
    @TableField("borrowStatus")
    private Integer borrowStatus;
    private String mark;
    @TableField("insertTime")
    private Date insertTime = new Date();
    @TableField("updateTime")
    private Date updateTime;
}
