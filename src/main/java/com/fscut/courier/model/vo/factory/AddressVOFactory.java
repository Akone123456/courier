package com.fscut.courier.model.vo.factory;

import com.fscut.courier.model.po.Address;
import com.fscut.courier.model.vo.AddressVO;

/**
 * @author lxw
 */
public class AddressVOFactory {
    private AddressVOFactory() {

    }

    public static AddressVO createAddressVO(Address address) {
        return AddressVO.builder()
                .addressId(address.getId())
                .consignee(address.getConsignee())
                .phone(address.getPhone())
                .city(address.getCity())
                .address(address.getAddress())
                .label(address.getLabel())
                .isDefault(address.getIsDefault())
                .build();
    }
    public static AddressVO getAddressDetail(String addressDetail) {
        return AddressVO.builder()
                .addressDetail(addressDetail)
                .build();
    }
}
