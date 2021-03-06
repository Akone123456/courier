package com.fscut.courier.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author lxw
 */
@Data
public class CommentDTO {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = {Save.class, UserDelete.class, AdminDelete.class})
    @Min(value = 1, message = "用户id小于1", groups = {Save.class, UserDelete.class, AdminDelete.class})
    private Integer userId;
    /**
     * 订单编号id
     */
    @NotNull(message = "订单id不为空", groups = {Save.class, UserDelete.class, AdminDelete.class})
    private String orderId;
    /**
     * 评价
     */
    @NotNull(message = "评价不能为空", groups = {Save.class})
    @Min(value = 1, message = "用户id小于1", groups = {Save.class})
    private Integer evaluation;
    /**
     * 评语
     */
    private String commentNote;
    /**
     * 评价id
     */
    @NotNull(message = "评价id不能为空", groups = {UserDelete.class, AdminDelete.class})
    @Min(value = 1, message = "评价id小于1", groups = {UserDelete.class, AdminDelete.class})
    private Integer commentId;

    public interface Save {

    }

    public interface UserDelete {

    }

    public interface AdminDelete {

    }


}
