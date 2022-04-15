package com.fscut.courier.model.vo;

import com.fscut.courier.model.dto.UserInfoDTO;
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
public class UserInfoVO {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户账号
     */
    private String userName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 头像
     */
    private String photo;
    /**
     * 个人简介
     */
    private String note;

    public Map<String, Object> listVo() {
        return ImmutableMap.<String, Object>builder()
                .put(USER_ID, getUserId())
                .put(USER_NAME, getUserName())
                .put(PHONE, getPhone())
                .put(PHOTO, getPhoto())
                .put(NOTE, getNote())
                .build();
    }
}
