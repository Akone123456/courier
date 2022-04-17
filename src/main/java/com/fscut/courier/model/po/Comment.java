package com.fscut.courier.model.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lxw
 */
@Data
@TableName("comments")
public class Comment {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 配送员id
     */
    private Integer senderId;
    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 评价（好评1，中评2，差评3）
     */
    private Integer evaluation;
    /**
     * 评语
     */
    private String commentNote;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDeleted;
}
