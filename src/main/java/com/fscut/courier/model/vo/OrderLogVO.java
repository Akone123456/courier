package com.fscut.courier.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author lxw
 */
@Data
@Builder
public class OrderLogVO {
    /**
     * 订单日志id
     */
    private Integer orderLogId;
    /**
     * 订单编号id
     */
    private String OrderId;
    /**
     * 日志内容
     */
    private String content;
}
