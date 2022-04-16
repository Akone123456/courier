package com.fscut.courier.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fscut.courier.dao.AddressDao;
import com.fscut.courier.dao.AddressUserInfoDao;
import com.fscut.courier.dao.UserInfoDao;
import com.fscut.courier.model.dto.AddressDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.Address;
import com.fscut.courier.model.po.AddressUserInfo;
import com.fscut.courier.model.po.UserInfo;
import com.fscut.courier.model.vo.AddressVO;
import com.fscut.courier.model.vo.factory.AddressVOFactory;
import com.fscut.courier.service.AddressService;
import com.fscut.courier.service.CommonService;
import com.fscut.courier.utils.ValidateUtil;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.ldap.PagedResultsControl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.fscut.courier.utils.ConstValue.*;

/**
 * @author lxw
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private AddressUserInfoDao addressUserInfoDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private CommonService commonService;

    /**
     * 添加收货地址
     *
     * @param addressDTO 收货地址信息
     */
    @Override
    public void addAddress(AddressDTO addressDTO) {
        // 判断用户是否存在
        commonService.userExist(addressDTO.getUserId());
        // 当前为默认地址，清除其他默认地址
        commonService.updateDefaultAddress(addressDTO.getIsDefault(), addressDTO.getUserId());
        // 保存address
        Address address = getAddress(addressDTO);
        addressDao.insert(address);
        // 保存address_userInfo
        AddressUserInfo addressUserInfo = new AddressUserInfo();
        addressUserInfo.setUserId(addressDTO.getUserId());
        addressUserInfo.setAddressId(address.getId());
        addressUserInfoDao.insert(addressUserInfo);
    }

    /**
     * 展示收货地址
     *
     * @param pageDTO 分页信息
     * @return
     */
    @Override
    public Map<String, Object> displayAddress(PageDTO pageDTO) {
        // 判断用户是否存在
        commonService.userExist(pageDTO.getUserId());
        // 分页查询
        Page<Address> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<Address> addressPage = addressDao.selectAddress(page, pageDTO.getUserId());
        // 封装VO
        List<AddressVO> addressVOList = new ArrayList<>();
        List<Address> addressList = addressPage.getRecords();
        addressList.forEach(t -> {
            AddressVO addressVO = AddressVOFactory.createAddressVO(t);
            addressVOList.add(addressVO);
        });
        return ImmutableMap.<String, Object>builder()
                .put(PAGE_TOTAL, page.getTotal())
                .put(ADDRESS_LIST, addressVOList)
                .build();
    }

    /**
     * 修改收货地址
     *
     * @param addressDTO 收获地址信息
     */
    @Override
    public void updateAddress(AddressDTO addressDTO) {
        // 判断用户是否存在
        commonService.userExist(addressDTO.getUserId());
        // 构建修改的字段
        Address address = getAddress(addressDTO);
        // 当前为默认地址，清除其他默认地址
        commonService.updateDefaultAddress(addressDTO.getIsDefault(), addressDTO.getUserId());
        // 条件构造器
        UpdateWrapper<Address> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", addressDTO.getAddressId());
        addressDao.update(address, updateWrapper);
    }

    /**
     * 删除收货地址
     *
     * @param addressDTO 收获地址信息
     */
    @Override
    public void deleteAddress(AddressDTO addressDTO) {
        // 判断用户是否存在
        commonService.userExist(addressDTO.getUserId());
        // 删除地址
        addressDao.deleteById(addressDTO.getAddressId());
        // 删除中间表
        QueryWrapper<AddressUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("address_id", addressDTO.getAddressId())
                .eq("user_id", addressDTO.getUserId());
        addressUserInfoDao.delete(queryWrapper);

    }

    @Override
    public AddressVO singleAddress(PageDTO pageDTO) {
        Address address = addressDao.selectById(pageDTO.getAddressId());
        return AddressVOFactory.createAddressVO(address);
    }

    @Override
    public List<AddressVO> getAddress(Integer userId) {
        List<Address> addressList = addressDao.selectAllByUserId(userId);
        List<AddressVO> addressVOList = new ArrayList<>();
        addressList.forEach(address -> {
            String addressDetail = address.getCity() + address.getAddress();
            AddressVO addressVO = AddressVOFactory.getAddressDetail(addressDetail);
            addressVOList.add(addressVO);
        });
        return addressVOList;
    }


    /**
     * 获取收货地址信息
     *
     * @param addressDTO 收货地址信息
     * @return
     */
    public Address getAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setConsignee(addressDTO.getConsignee());
        address.setPhone(addressDTO.getPhone());
        address.setCity(addressDTO.getCity());
        address.setAddress(addressDTO.getAddress());
        address.setLabel(addressDTO.getLabel());
        address.setIsDefault(addressDTO.getIsDefault());
        return address;
    }
}
