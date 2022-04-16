package com.fscut.courier.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fscut.courier.dao.OrderDao;
import com.fscut.courier.dao.UserOrderSenderDao;
import com.fscut.courier.model.dto.OrderDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.Order;
import com.fscut.courier.model.po.UserOrderSender;
import com.fscut.courier.model.vo.OrderVO;
import com.fscut.courier.model.vo.factory.OrderVOFactory;
import com.fscut.courier.service.CommonService;
import com.fscut.courier.service.OrderService;
import com.fscut.courier.utils.OrderStatusEnum;
import com.fscut.courier.utils.PayStatusEnum;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.toolkit.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.fscut.courier.utils.ConstValue.ORDER_LIST;
import static com.fscut.courier.utils.ConstValue.PAGE_TOTAL;
import static java.util.stream.Collectors.toList;

/**
 * @author lxw
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserOrderSenderDao userOrderSenderDao;
    @Autowired
    private CommonService commonService;

    /**
     * 普通用户下单
     *
     * @param orderDTO 订单信息
     */
    @Override
    public void placeOrder(OrderDTO orderDTO) {
        // 判断用户是否存在
        commonService.userExist(orderDTO.getUserId());
        // 订单信息
        Order order = new Order();
        order.setTakeUserName(orderDTO.getTakeUserName());
        order.setPhone(orderDTO.getPhone());
        order.setCourierNumber(orderDTO.getCourierNumber());
        order.setCourierAddress(orderDTO.getCourierAddress());
        order.setNote(orderDTO.getNote());
        order.setBounty(orderDTO.getBounty());
        order.setPayStatus(PayStatusEnum.HAVE_PAY.getStatus());
        order.setOrderStatus(OrderStatusEnum.NOT_ORDER.getStatus());
        orderDao.insert(order);
        // 用户-订单-配送员
        UserOrderSender userOrderSender = new UserOrderSender();
        userOrderSender.setUserId(orderDTO.getUserId());
        userOrderSender.setOrderId(order.getId());
        userOrderSenderDao.insert(userOrderSender);
    }

    /**
     * 普通用户-我的订单
     *
     * @param pageDTO 订单查询条件
     * @return
     */
    @Override
    public Map<String, Object> userOrder(PageDTO pageDTO) {
        // 判断用户是否存在
        commonService.userExist(pageDTO.getUserId());
        // 查询与当前用户有关的订单
        QueryWrapper<UserOrderSender> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", pageDTO.getUserId());
        List<UserOrderSender> userOrderSenderList = userOrderSenderDao.selectList(queryWrapper);
        List<Integer> orderIdList = userOrderSenderList.stream().map(UserOrderSender::getOrderId).collect(toList());
        // 获取订单
        LambdaQueryWrapper<Order> querylambdaWrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        querylambdaWrapper.eq(ObjectUtils.isNotNull(pageDTO.getOrderStatus()), Order::getOrderStatus, pageDTO.getOrderStatus())
                .between(ObjectUtils.isNotNull(pageDTO.getStartTime())
                        && ObjectUtils.isNotNull(pageDTO.getEndTime()), Order::getCreateTime, pageDTO.getStartTime(), pageDTO.getEndTime())
                .in(ObjectUtils.isNotNull(orderIdList), Order::getId, orderIdList);
        // 构造返回数据
        List<OrderVO> orderVOList = new ArrayList<>();
        // 分页
        Page<Order> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<Order> orderPage = orderDao.selectPage(page, querylambdaWrapper);
        orderPage.getRecords().forEach(order -> {
            OrderVO orderVO = OrderVOFactory.createOrderVO(order);
            orderVOList.add(orderVO);
        });
        return ImmutableMap.<String, Object>builder()
                .put(PAGE_TOTAL, page.getTotal())
                .put(ORDER_LIST, orderVOList)
                .build();
    }

    /**
     * 普通用户-删除订单
     *
     * @param orderDTO 订单信息
     * @return
     */
    @Override
    public void userDeleteOrder(OrderDTO orderDTO) {
        // 判断用户是否存在
        commonService.userExist(orderDTO.getUserId());
        // 删除订单表
        orderDao.deleteById(orderDTO.getOrderId());
        // 删除用户-订单-配送员信息表
        QueryWrapper<UserOrderSender> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", orderDTO.getUserId())
                .eq("order_id", orderDTO.getOrderId());
        userOrderSenderDao.delete(queryWrapper);
    }

    /**
     * 普通用户-取消订单
     *
     * @param orderId 订单id
     * @return
     */
    @Override
    public void userCancelOrder(Integer orderId) {
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("order_status", OrderStatusEnum.CANCEL_ORDER.getStatus())
                .eq("id", orderId);
        orderDao.update(null, updateWrapper);
    }

    /**
     * 配送员-接单大厅
     *
     * @param pageDTO 分页信息
     * @return
     */
    @Override
    public Map<String, Object> orderHall(PageDTO pageDTO) {
        // 获取已支付-未接单的订单
        LambdaQueryWrapper<Order> querylambdaWrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        querylambdaWrapper
                .eq(Order::getPayStatus, PayStatusEnum.HAVE_PAY.getStatus())
                .eq(Order::getOrderStatus, OrderStatusEnum.NOT_ORDER.getStatus())
                .between(ObjectUtils.isNotNull(pageDTO.getStartTime())
                        && ObjectUtils.isNotNull(pageDTO.getEndTime()), Order::getCreateTime, pageDTO.getStartTime(), pageDTO.getEndTime());
        // 构造返回数据
        List<OrderVO> orderVOList = new ArrayList<>();
        // 分页
        Page<Order> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<Order> orderPage = orderDao.selectPage(page, querylambdaWrapper);
        orderPage.getRecords().forEach(order -> {
            OrderVO orderVO = OrderVOFactory.createSenderOrderVO(order);
            orderVOList.add(orderVO);
        });
        return ImmutableMap.<String, Object>builder()
                .put(PAGE_TOTAL, page.getTotal())
                .put(ORDER_LIST, orderVOList)
                .build();
    }

    /**
     * 配送员-接单,配送,完成.
     *
     * @param orderDTO 订单信息
     * @return
     */
    @Override
    public void receiveOrder(OrderDTO orderDTO) {
        // 修改订单状态
        UpdateWrapper<Order> orderWrapper = new UpdateWrapper<>();
        orderWrapper.set("order_status", orderDTO.getOrderStatus())
                .eq("id", orderDTO.getOrderId());
        orderDao.update(null, orderWrapper);
        // 修改用户-订单-配送员中间表
        UpdateWrapper<UserOrderSender> userOrderSenderUpdateWrapper = new UpdateWrapper<>();
        userOrderSenderUpdateWrapper.set("sender_id", orderDTO.getUserId())
                .eq("order_id", orderDTO.getOrderId());
        userOrderSenderDao.update(null, userOrderSenderUpdateWrapper);
    }

    /**
     * 配送员-我的订单
     *
     * @param pageDTO 分页信息
     * @return
     */
    @Override
    public Map<String, Object> senderOrder(PageDTO pageDTO) {
        // 查询与当前用户有关的订单
        QueryWrapper<UserOrderSender> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sender_id", pageDTO.getUserId());
        List<UserOrderSender> userOrderSenderList = userOrderSenderDao.selectList(queryWrapper);
        List<Integer> orderIdList = userOrderSenderList.stream().map(UserOrderSender::getOrderId).collect(toList());
        // 获取订单
        LambdaQueryWrapper<Order> querylambdaWrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        querylambdaWrapper.eq(ObjectUtils.isNotNull(pageDTO.getOrderStatus()), Order::getOrderStatus, pageDTO.getOrderStatus())
                .between(ObjectUtils.isNotNull(pageDTO.getStartTime())
                        && ObjectUtils.isNotNull(pageDTO.getEndTime()), Order::getCreateTime, pageDTO.getStartTime(), pageDTO.getEndTime())
                .in(ObjectUtils.isNotNull(orderIdList), Order::getId, orderIdList)
                .orderByAsc(Order::getOrderStatus);

        // 构造返回数据
        List<OrderVO> orderVOList = new ArrayList<>();
        // 分页
        Page<Order> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<Order> orderPage = orderDao.selectPage(page, querylambdaWrapper);
        orderPage.getRecords().forEach(order -> {
            OrderVO orderVO = OrderVOFactory.createSenderOrderVO1(order);
            orderVOList.add(orderVO);
        });
        return ImmutableMap.<String, Object>builder()
                .put(PAGE_TOTAL, page.getTotal())
                .put(ORDER_LIST, orderVOList)
                .build();
    }

    /**
     * 配送员-删除订单
     *
     * @param orderDTO 订单信息
     * @return
     */
    @Override
    public void senderDeleteOrder(OrderDTO orderDTO) {
        // 删除订单表
        orderDao.deleteById(orderDTO.getOrderId());
        // 删除用户-订单-配送员信息表
        QueryWrapper<UserOrderSender> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", orderDTO.getUserId())
                .eq("order_id", orderDTO.getOrderId());
        userOrderSenderDao.delete(queryWrapper);
    }
}
