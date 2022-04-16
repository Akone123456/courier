package com.fscut.courier.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * @author lxw
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressVO {
    /**
     * 收货地址id
     */
    private Integer addressId;
    /**
     * 收货人姓名
     */
    private String consignee;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 所在地区
     */
    private String city;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 标签（家，公司，学校，其他）
     */
    private String label;
    /**
     * 默认地址(0不默认，1默认)
     */
    private Integer isDefault;

    /**
     * 收货详细地址
     */
    private String addressDetail;
}
