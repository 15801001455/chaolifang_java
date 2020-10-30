package com.chaolifang.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@ApiModel(value = "书籍实体")
public class Book {
    @ApiModelProperty(value = "书籍编号")
    private String id;//书籍编号
    @ApiModelProperty(value = "书籍名称")
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
