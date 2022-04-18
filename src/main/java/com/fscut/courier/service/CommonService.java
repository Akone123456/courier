package com.fscut.courier.service;

import com.fscut.courier.model.dto.CommentDTO;
import com.fscut.courier.model.dto.PageDTO;

import javax.jws.Oneway;
import java.util.Map;

/**
 * @author lxw
 */
public interface CommonService {
    /**
     * 若为默认地址，修改其他默认地址
     *
     * @param isDefault 是否为默认地址
     * @param userId    用户id
     */
    void updateDefaultAddress(Integer isDefault, Integer userId);

    /**
     * 判断用户是否存在
     *
     * @param userId 用户id
     */
    void userExist(Integer userId);

    /**
     * 记录订单日志
     *
     * @param OrderId 订单id
     * @param content 内容
     */
    void recordLog(String OrderId, String content);


}
