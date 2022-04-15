package com.fscut.courier.controller;

import com.fscut.courier.model.dto.AddressDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.Address;
import com.fscut.courier.model.vo.AddressVO;
import com.fscut.courier.service.AddressService;
import com.fscut.courier.utils.ResultUtil;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.fscut.courier.utils.ConstValue.ADDRESS_LIST;
import static com.fscut.courier.utils.ResultUtil.ok;

/**
 * @author lxw
 */
@Validated
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 添加收获地址
     *
     * @param addressDTO 收货地址信息
     * @return
     */
    @PostMapping("add")
    public ResultUtil.Result addAddress(@RequestBody @Validated(AddressDTO.Save.class) AddressDTO addressDTO) {
        addressService.addAddress(addressDTO);
        return ok();
    }

    /**
     * 展示收货地址
     *
     * @param pageDTO 分页信息
     * @return
     */
    @PostMapping("display")
    public ResultUtil.Result displayAddress(@RequestBody @Validated(PageDTO.Show.class) PageDTO pageDTO) {
        List<AddressVO> addressList = addressService.displayAddress(pageDTO);
        return ok(ImmutableMap.builder()
                .put(ADDRESS_LIST, addressList)
                .build());
    }

    /**
     * 修改收获地址
     *
     * @param addressDTO 收货地址信息
     * @return
     */
    @PostMapping("update")
    public ResultUtil.Result updateAddress(@RequestBody @Validated(AddressDTO.Update.class) AddressDTO addressDTO) {
        addressService.updateAddress(addressDTO);
        return ok();
    }
}
