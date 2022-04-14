package com.fscut.courier.model.vo;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

import static com.fscut.courier.utils.ConstValue.*;

/**
 * @author lxw
 */
@Data
@Builder
public class SenderVO {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 用户账号
     */
    private String userName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 个人简介
     */
    private String note;

    public Map<String, Object> listVo() {
        return ImmutableMap.<String, Object>builder()
                .put(USER_ID, getUserId())
                .put(REAL_NAME, getRealName())
                .put(USER_NAME, getUserName())
                .put(PHONE, getPhone())
                .put(PHOTO, getPhone())
                .put(NOTE, getNote())
                .build();
    }
}
