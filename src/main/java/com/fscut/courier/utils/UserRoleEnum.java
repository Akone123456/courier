package com.fscut.courier.utils;

/**
 * @author lxw
 */
public enum UserRoleEnum {
    /**
     * 用户角色(权限)
     */
    ADMIN("管理员", "Admin"),
    USER("普通用户", "User"),
    SENDER("配送员", "Sender");

    private final String desc;
    private final String role;

    public String getDesc() {
        return desc;
    }

    public String getRole() {
        return role;
    }

    UserRoleEnum(String desc, String role) {
        this.desc = desc;
        this.role = role;
    }

}
