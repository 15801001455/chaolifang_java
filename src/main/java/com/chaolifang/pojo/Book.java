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
    @ApiModelProperty(value = "借阅时间")
    private Date borrowTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value = "归还时间")
    private Date returnTime;
    @ApiModelProperty(value = "借阅人")
    private String borrowPerson;
    @ApiModelProperty(value = "借阅状态")
    private Integer borrowStatus;
    @ApiModelProperty(value = "注释")
    private String mark;
    @ApiModelProperty(value = "新增时间")
    private Date insertTime = new Date();
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
