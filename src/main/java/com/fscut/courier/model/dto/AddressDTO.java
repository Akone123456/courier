package com.fscut.courier.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author lxw
 */
@Data
public class AddressDTO {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不为空", groups = {Save.class, Update.class, Delete.class})
    @Min(value = 1, message = "用户id不能小于1", groups = {Save.class, Update.class, Delete.class})
    private Integer userId;
    /**
     * 收货地址id
     */
    @NotNull(message = "收货地址id不为空", groups = {Update.class})
    @Min(value = 1, message = "收货地址id不能小于1", groups = {Update.class})
    private Integer addressId;
    /**
     * 收货人姓名
     */
    @NotNull(message = "收货人姓名不能为null值", groups = {Save.class, Update.class})
    @NotEmpty(message = "收货人姓名不能为空", groups = {Save.class, Update.class})
    private String consignee;
    /**
     * 手机号码
     */
    @NotNull(message = "手机号码不能为null值", groups = {Save.class, Update.class})
    @NotEmpty(message = "手机号码不能为空", groups = {Save.class, Update.class})
    private String phone;
    /**
     * 所在地区
     */
    @NotNull(message = "所在地区不能为null值", groups = {Save.class, Update.class})
    @NotEmpty(message = "所在地区不能为空", groups = {Save.class, Update.class})
    private String city;
    /**
     * 详细地址
     */
    @NotNull(message = "详细地址不能为null值", groups = {Save.class, Update.class})
    @NotEmpty(message = "详细地址不能为空", groups = {Save.class, Update.class})
    private String address;
    /**
     * 标签（家，公司，学校，其他）
     */
    @NotNull(message = "标签不能为空", groups = {Save.class, Update.class})
    private String label;
    /**
     * 默认地址(0不默认，1默认)
     */
    @NotNull(message = "默认地址不能为空", groups = {Save.class, Update.class})
    private Integer isDefault;

    public interface Save {

    }

    public interface Update {

    }

    public interface Delete {

    }

}
