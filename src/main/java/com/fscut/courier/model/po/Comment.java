package com.fscut.courier.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fscut.courier.model.po.base.BaseEntity;
import lombok.Data;

/**
 * @author lxw
 */
@Data
@TableName("comments")
public class Comment extends BaseEntity {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
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
