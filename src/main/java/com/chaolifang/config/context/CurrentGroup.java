package com.chaolifang.config.context;

import lombok.Data;

@Data
public class CurrentGroup {

    /*
     * 集团标记  如XF、ESF、3F、JJ
     * */
    private String GroupCode;
    /*
     * 集团名称 如新房集团、二手房集团
     * */
    private String GroupName;
}
