package com.fscut.courier.model.po;

import lombok.Data;

/**
 * @author lxw
 */
@Data
public class UserFace {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 订单编号
     */
    private String orderId;
    /**
     * base64位的图片
     */
    private String img;
}
