package com.fscut.courier.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lxw
 */
@Data
@TableName("user_order_sender")
public class UserOrderSender {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 配送员id
     */
    private Integer senderId;
    /**
     * 用户逻辑删除
     */
    private Integer userIsDeleted;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer orderIsDeleted;

}
