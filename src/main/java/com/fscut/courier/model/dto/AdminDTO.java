package com.fscut.courier.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author lxw
 */
@Data
public class AdminDTO {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = {UpdatePswd.class})
    @Min(value = 1, message = "用户id小于1", groups = {UpdatePswd.class})
    private Integer userId;

    /**
     * 原始密码
     */
    @NotNull(message = "原始密码不能为空", groups = {UpdatePswd.class})
    private String originalPassword;
    /**
     * 新密码
     */
    @NotNull(message = "新密码不能为空", groups = {UpdatePswd.class})
    private String newPassword;

    /**
     * 确认密码
     */
    @NotNull(message = "确认密码不能为空", groups = {UpdatePswd.class})
    private String confirmPassword;

    public interface Update {

    }

    public interface UpdatePswd {

    }
}
