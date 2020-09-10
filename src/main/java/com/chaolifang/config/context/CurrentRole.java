package com.chaolifang.config.context;

public class CurrentRole {
    /*
     * 角色ID
     * */
    private Integer RoleID;
    /*
     * 角色名称
     * */
    private String RoleName;
    /*
     * 角色分组  取UA角色配置中 description 字段 如：运营、客服、销售
     * */
    private String Rogroup;
    /*
     * 业务线  集团名称
     * */
    private String GroupName;

    //update jyc 数据查看范围
    private Integer CheckId;

    public Integer getCheckId() {
        return CheckId;
    }

    public void setCheckId(Integer CheckId) {
        this.CheckId = CheckId;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public String getRogroup() {
        return Rogroup;
    }

    public void setRogroup(String rogroup) {
        Rogroup = rogroup;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public Integer getRoleID() {
        return RoleID;
    }

    public void setRoleID(Integer roleID) {
        RoleID = roleID;
    }
}
