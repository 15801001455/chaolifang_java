package com.chaolifang.dto;

import lombok.Data;

@Data
public class BookManagerSearchDTO {
    private Integer pageIndex; // 当前是第几页
    private Integer pageSize;  // 每页显示多少条
}
