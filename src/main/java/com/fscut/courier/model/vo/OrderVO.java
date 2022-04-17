package com.fscut.courier.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lxw
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderVO {
    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 快递单号
     */
    private String courierNumber;
    /**
     * 赏金
     */
    private BigDecimal bounty;
    /**
     * 支付状态
     */
    private Integer payStatus;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 下单时间
     */
    private Date createTime;
    /**
     * 取件人
     */
    private String takeUserName;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 快递收件地址
     */
    private String courierAddress;
    /**
     * 备注
     */
    private String note;


}
