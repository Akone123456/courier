package com.fscut.courier.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author lxw
 */
@Data
public class PageDTO {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不为空", groups = {Show.class, User.class, Sender.class})
    @Min(value = 1, message = "用户id不能小于1", groups = {Show.class, User.class, Sender.class})
    private Integer userId;
    /**
     * 收货地址id
     */
    @NotNull(message = "收货地址id不为空", groups = {Single.class})
    @Min(value = 1, message = "收货地址id不能小于1", groups = {Single.class})
    private Integer addressId;
    /**
     * 页面大小
     */
    @NotNull(message = "页面大小不能为空", groups = {Show.class, User.class, Sender.class})
    @Min(value = 1, message = "页面大小不能小于1", groups = {Show.class, User.class, Sender.class})
    @Max(value = 10, message = "页面大小不能大于10", groups = {Show.class, User.class, Sender.class})
    private Integer pageSize;
    /**
     * 页码
     */
    @NotNull(message = "页码不能为空", groups = {Show.class, User.class, Sender.class})
    @Min(value = 1, message = "页码不能小于1", groups = {Show.class, User.class, Sender.class})
    @Max(value = 10, message = "页码不能大于10", groups = {Show.class, User.class, Sender.class})
    private Integer pageNum;

    /**
     * 订单状态
     */

    private Integer orderStatus;

    /**
     * 开始时间
     */

    private String startTime;

    /**
     * 结束时间
     */

    private String endTime;

    public interface Show {

    }

    public interface Single {

    }

    public interface User {

    }

    public interface Sender {

    }
}
