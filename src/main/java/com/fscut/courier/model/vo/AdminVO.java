package com.fscut.courier.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author lxw
 */
@Data
@Builder
public class AdminVO {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户账号
     */
    private String userName;
}
