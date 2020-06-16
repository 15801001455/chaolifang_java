package com.chaolifang.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BookManagerDTO {
    private String id;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date borrowTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date returnTime;
    private String borrowPerson;
    private Integer borrowStatus;
    private String mark;
    private Date insertTime;
    private Date updateTime;
}
