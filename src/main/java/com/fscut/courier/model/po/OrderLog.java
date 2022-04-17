package com.fscut.courier.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fscut.courier.model.po.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author lxw
 */
@Data
public class OrderLog extends BaseEntity {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 日志内容
     */
    private String content;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDeleted;


}
