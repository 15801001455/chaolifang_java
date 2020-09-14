package com.chaolifang.config.context;

import lombok.Data;

@Data
public class CurrentRole {
    /*
     * 角色ID
     * */
    private Integer roleId;
    /*
     * 角色名称
     * */
    private String roleName;
}
