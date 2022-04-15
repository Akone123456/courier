package com.fscut.courier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fscut.courier.model.dto.AddressDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.Address;
import com.fscut.courier.model.vo.AddressVO;

import java.util.List;

/**
 * @author lxw
 */
public interface AddressService extends IService<Address> {
    /**
     * 添加收货地址
     *
     * @param addressDTO 收获地址信息
     */
    void addAddress(AddressDTO addressDTO);

    /**
     * 展示收货地址
     *
     * @param pageDTO 分页信息
     */
    List<AddressVO> displayAddress(PageDTO pageDTO);

    /**
     * 修改收货地址
     *
     * @param addressDTO 收获地址信息
     */
    void updateAddress(AddressDTO addressDTO);
}
