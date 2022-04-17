package com.fscut.courier.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fscut.courier.dao.AddressDao;
import com.fscut.courier.dao.OrderLogDao;
import com.fscut.courier.dao.UserInfoDao;
import com.fscut.courier.model.po.Address;
import com.fscut.courier.model.po.OrderLog;
import com.fscut.courier.model.po.UserInfo;
import com.fscut.courier.service.CommonService;
import com.fscut.courier.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fscut.courier.utils.ConstValue.*;

/**
 * @author lxw
 */
@Service
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
            addressDao.updateIsDefault(NOT_DEFAULT,userId);
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
    public void recordLog(String orderId,String content) {
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderId);
        orderLog.setContent(content);
        orderLogDao.insert(orderLog);
    }
}
