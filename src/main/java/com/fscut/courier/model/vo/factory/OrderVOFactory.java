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
    public static OrderVO createSenderOrderVO(Order order) {
        return OrderVO.builder()
                .orderId(order.getId())
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
                .orderId(order.getId())
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
