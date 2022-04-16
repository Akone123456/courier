package com.fscut.courier.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author lxw
 */
@Data
public class PageDTO {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不为空", groups = {Show.class})
    @Min(value = 1, message = "用户id不能小于1", groups = {Show.class})
    private Integer userId;
    /**
     * 收货地址id
     */
    @NotNull(message = "用户id不为空", groups = {Show.class, Single.class})
    @Min(value = 1, message = "用户id不能小于1", groups = {Show.class, Single.class})
    private Integer addressId;
    /**
     * 页面大小
     */
    @NotNull(message = "页面大小不能为空", groups = {Show.class})
    @Min(value = 1, message = "页面大小不能小于1", groups = {Show.class})
    @Max(value = 10, message = "页面大小不能大于10", groups = {Show.class})
    private Integer pageSize;
    /**
     * 页码
     */
    @NotNull(message = "页码不能为空", groups = {Show.class})
    @Min(value = 1, message = "页码不能小于1", groups = {Show.class})
    @Max(value = 10, message = "页码不能大于10", groups = {Show.class})
    private Integer pageNum;

    public interface Show {

    }

    public interface Single {

    }
}
