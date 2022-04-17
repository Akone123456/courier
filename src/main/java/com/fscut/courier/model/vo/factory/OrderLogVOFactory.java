package com.fscut.courier.model.vo.factory;

import com.fscut.courier.model.po.OrderLog;
import com.fscut.courier.model.vo.OrderLogVO;

/**
 * @author lxw
 */

public class OrderLogVOFactory {
    private OrderLogVOFactory() {

    }

    public static OrderLogVO createOrderLogVO(OrderLog orderLog) {
        return OrderLogVO.builder()
                .orderLogId(orderLog.getId())
                .OrderId(orderLog.getOrderId())
                .content(orderLog.getContent())
                .build();
    }
}
