package com.fscut.courier.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lxw
 */
@Data
@Builder
public class OrderVO {
    /**
     * 订单编号
     */
    private Integer orderId;
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

}
