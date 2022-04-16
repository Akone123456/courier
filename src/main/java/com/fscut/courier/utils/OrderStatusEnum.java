package com.fscut.courier.utils;

/**
 * @author lxw
 */
public enum OrderStatusEnum {
    NOT_ORDER("未接单", 1),
    HAVE_ORDER("已接单", 2),
    DISTRIBUTION("配送中", 3),
    FINISH_ORDER("完成订单", 4),
    CANCEL_ORDER("取消订单", 5);
    private final String desc;
    private final Integer status;

    public String getDesc() {
        return desc;
    }

    public Integer getStatus() {
        return status;
    }

    OrderStatusEnum(String desc, Integer status) {
        this.desc = desc;
        this.status = status;
    }
}
