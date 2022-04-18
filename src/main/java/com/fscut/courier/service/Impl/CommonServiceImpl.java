package com.fscut.courier.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fscut.courier.dao.*;
import com.fscut.courier.model.dto.CommentDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.*;
import com.fscut.courier.service.CommonService;
import com.fscut.courier.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fscut.courier.utils.ConstValue.*;
import static java.util.stream.Collectors.toList;

/**
 * @author lxw
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommonServiceImpl implements CommonService {
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private OrderLogDao orderLogDao;


    /**
     * 若为默认地址，修改其他默认地址
     *
     * @param isDefault 是否为默认地址
     * @param userId    用户id
     */
    @Override
    public void updateDefaultAddress(Integer isDefault, Integer userId) {
        if (DEFAULT.equals(isDefault)) {
            addressDao.updateIsDefault(NOT_DEFAULT, userId);
        }
    }

    @Override
    public void userExist(Integer userId) {
        UserInfo userInfo = userInfoDao.selectById(userId);
        ValidateUtil.logicalNotNull(userInfo, USER_NOT_EXIST);
    }

    /**
     * 记录订单日志
     *
     * @param orderId 订单id
     * @param content 内容
     */
    @Override
    public void recordLog(String orderId, String content) {
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderId);
        orderLog.setContent(content);
        orderLogDao.insert(orderLog);
    }

}
