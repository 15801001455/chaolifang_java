package com.chaolifang.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BookManagerSearchDTO {
    private Integer pageIndex; // 当前是第几页
    private Integer pageSize;  // 每页显示多少条
    private Date borrowTimeStart;
    private Date borrowTimeEnd;
    private String borrowTimeStartStr;
    private String borrowTimeEndStr;
    private String borrowPerson;
    private Integer borrowStatus;
}
