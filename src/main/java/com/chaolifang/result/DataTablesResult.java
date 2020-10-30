package com.chaolifang.result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Api(tags = "列表返回类")
public class DataTablesResult<T> extends BaseResult<T> implements Serializable {
    @ApiModelProperty(value = "总数")
    private int recordsTotal;
}
