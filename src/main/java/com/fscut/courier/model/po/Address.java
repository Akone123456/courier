package com.fscut.courier.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fscut.courier.model.po.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author lxw
 */
@Data
@TableName("address")
public class Address extends BaseEntity {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
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
     * 逻辑删除
     */
    @TableLogic
    private Integer isDeleted;


}
