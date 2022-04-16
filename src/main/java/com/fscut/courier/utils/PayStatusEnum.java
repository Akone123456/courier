package com.fscut.courier.utils;

import sun.dc.pr.PRError;

/**
 * @author lxw
 */
public enum PayStatusEnum {
    NOT_PAY("未支付",1),
    HAVE_PAY("已支付",2),
    HAVE_REFUND("已退款",3)
    ;
    private final String desc;
    private final Integer status;

    public String getDesc() {
        return desc;
    }

    public Integer getStatus() {
        return status;
    }

    PayStatusEnum(String desc, Integer status) {
        this.desc = desc;
        this.status = status;
    }
}
