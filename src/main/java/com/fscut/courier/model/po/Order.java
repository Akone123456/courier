package com.fscut.courier.model.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fscut.courier.model.po.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lxw
 */
@Data
@TableName("orders")
public class Order extends BaseEntity {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 取件人
     */
    private String takeUserName;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 快递单号
     */
    private String courierNumber;
    /**
     * 快递收件地址
     */
    private String courierAddress;
    /**
     * 备注
     */
    private String note;
    /**
     * 赏金
     */
    private BigDecimal bounty;
    /**
     * 订单状态（未接单1，已接单2，配送中3，完成订单4，取消订单5）
     */
    private Integer orderStatus;
    /**
     * 支付状态（未支付1，已支付2，已退款3）
     */
    private Integer payStatus;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDeleted;

}
