package com.fscut.courier.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author lxw
 */
@Data
public class OrderDTO {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不为空", groups = {Save.class, UserDelete.class})
    @Min(value = 1, message = "用户id不能小于1", groups = {Save.class, UserDelete.class})
    private Integer userId;
    /**
     * 订单id
     */

    @NotNull(message = "订单id不为空", groups = {UserDelete.class})
    @Min(value = 1, message = "订单id不能小于1", groups = {UserDelete.class})
    private Integer orderId;
    /**
     * 取件人姓名
     */
    @NotNull(message = "取件人姓名不能为null", groups = {Save.class})
    @NotEmpty(message = "取件人姓名不能为空", groups = {Save.class})
    private String takeUserName;
    /**
     * 联系电话
     */
    @NotNull(message = "联系电话不能为null", groups = {Save.class})
    @NotEmpty(message = "联系电话不能为空", groups = {Save.class})
    private String phone;
    /**
     * 快递单号
     */
    @NotNull(message = "快递单号不能为null", groups = {Save.class})
    @NotEmpty(message = "快递单号不能为空", groups = {Save.class})
    private String courierNumber;
    /**
     * 快递收件地址
     */
    @NotNull(message = "快递收件地址不能为null", groups = {Save.class})
    @NotEmpty(message = "快递收件地址不能为空", groups = {Save.class})
    private String courierAddress;
    /**
     * 备注
     */
    @NotNull(message = "备注信息不能为null", groups = {Save.class})
    private String note;
    /**
     * 赏金
     */
    @NotNull(message = "赏金不能为null", groups = {Save.class})
    private BigDecimal bounty;

    public interface Save {

    }

    public interface UserDelete {

    }
}
