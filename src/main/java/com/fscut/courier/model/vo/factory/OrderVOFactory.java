package com.fscut.courier.model.vo.factory;

import com.fscut.courier.model.po.Order;
import com.fscut.courier.model.vo.OrderVO;

/**
 * @author lxw
 */
public class OrderVOFactory {
    private OrderVOFactory() {

    }

    public static OrderVO createOrderVO(Order order) {
        return OrderVO.builder()
                .orderId(order.getId())
                .courierNumber(order.getCourierNumber())
                .bounty(order.getBounty())
                .payStatus(order.getPayStatus())
                .orderStatus(order.getOrderStatus())
                .createTime(order.getCreateTime())
                .build();
    }
}
