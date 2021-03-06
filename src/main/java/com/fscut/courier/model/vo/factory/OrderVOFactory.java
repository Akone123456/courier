package com.fscut.courier.model.vo.factory;

import com.fscut.courier.model.po.Order;
import com.fscut.courier.model.vo.OrderVO;

/**
 * @author lxw
 */
public class OrderVOFactory {
    private OrderVOFactory() {

    }

    public static OrderVO createOrderVO(Order order, Integer evaluation) {
        return OrderVO.builder()
                .orderId(order.getOrderId())
                .courierNumber(order.getCourierNumber())
                .bounty(order.getBounty())
                .payStatus(order.getPayStatus())
                .orderStatus(order.getOrderStatus())
                .evaluation(evaluation)
                .createTime(order.getCreateTime())
                .build();
    }

    public static OrderVO createSenderOrderVO(Order order) {
        return OrderVO.builder()
                .orderId(order.getOrderId())
                .courierNumber(order.getCourierNumber())
                .takeUserName(order.getTakeUserName())
                .phone(order.getPhone())
                .courierAddress(order.getCourierAddress())
                .bounty(order.getBounty())
                .note(order.getNote())
                .createTime(order.getCreateTime())
                .build();
    }

    public static OrderVO createSenderOrderVO1(Order order) {
        return OrderVO.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .courierNumber(order.getCourierNumber())
                .takeUserName(order.getTakeUserName())
                .phone(order.getPhone())
                .courierAddress(order.getCourierAddress())
                .bounty(order.getBounty())
                .note(order.getNote())
                .createTime(order.getCreateTime())
                .build();
    }
}
