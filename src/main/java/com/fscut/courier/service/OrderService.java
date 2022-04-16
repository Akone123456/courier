package com.fscut.courier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fscut.courier.model.dto.OrderDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.Order;
import com.fscut.courier.model.vo.OrderVO;

import java.util.List;
import java.util.Map;

/**
 * @author lxw
 */
public interface OrderService extends IService<Order> {
    /**
     * 通用户下单
     *
     * @param orderDTO 订单信息
     */
    void placeOrder(OrderDTO orderDTO);

    /**
     * 普通用户-我的订单
     *
     * @param pageDTO 订单查询条件
     * @return
     */
    Map<String, Object> userOrder(PageDTO pageDTO);

    /**
     * 普通用户-删除订单
     *
     * @param orderDTO  订单信息
     * @return
     */
    void userDeleteOrder(OrderDTO orderDTO);

    void userCancelOrder(Integer orderId);
}
