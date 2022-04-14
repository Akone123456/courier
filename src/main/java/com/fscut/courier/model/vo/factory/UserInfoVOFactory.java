package com.fscut.courier.model.vo.factory;

import com.fscut.courier.model.po.UserInfo;
import com.fscut.courier.model.vo.UserInfoVO;

import static com.fscut.courier.utils.ConstValue.*;

/**
 * @author lxw
 */
public class UserInfoVOFactory {
    private UserInfoVOFactory() {
    }

    public static UserInfoVO createTaskVo(UserInfo userInfo) {
        return UserInfoVO.builder()
                .userId(userInfo.getId())
                .userName(userInfo.getUsername())
                .phone(userInfo.getPhone())
                .note(userInfo.getNote())
                .build();
    }
}
