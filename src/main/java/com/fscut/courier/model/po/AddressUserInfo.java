package com.fscut.courier.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.naming.ldap.PagedResultsControl;

/**
 * @author lxw
 */
@Data
@TableName("address_userinfo")
public class AddressUserInfo {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 收货地址id
     */
    private Integer addressId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDeleted;
}
