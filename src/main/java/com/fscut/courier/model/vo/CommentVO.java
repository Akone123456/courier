package com.fscut.courier.model.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author lxw
 */
@Data
@Builder
public class CommentVO {
    /**
     * 评论id
     */
    private Integer commentId;
    /**
     * 订单编号id
     */
    private String orderId;
    /**
     * 配送员真实姓名
     */
    private String senderUserName;
    /**
     * 评价（好评1，中评2，差评3）
     */
    private Integer evaluation;
    /**
     * 评语
     */
    private String commentNote;
    /**
     * 评价时间
     */
    private Date createTime;

}
