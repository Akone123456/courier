package com.fscut.courier.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fscut.courier.dao.AddressDao;
import com.fscut.courier.model.po.Address;
import com.fscut.courier.service.CommonService;
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

    /**
     * 若为默认地址，修改其他
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
}
