package com.fscut.courier.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fscut.courier.dao.OrderDao;
import com.fscut.courier.dao.SenderDao;
import com.fscut.courier.dao.UserInfoDao;
import com.fscut.courier.dao.UserOrderSenderDao;
import com.fscut.courier.model.dto.OrderDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.*;
import com.fscut.courier.model.vo.OrderVO;
import com.fscut.courier.model.vo.factory.OrderVOFactory;
import com.fscut.courier.service.CommonService;
import com.fscut.courier.service.OrderService;
import com.fscut.courier.service.UserInfoService;
import com.fscut.courier.utils.*;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.toolkit.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.fscut.courier.utils.ConstValue.*;
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
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private SenderDao senderDao;
    @Autowired
    private UserInfoService userInfoService;


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
        order.setOrderId(String.valueOf(System.currentTimeMillis()));
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
        userOrderSender.setOrderId(order.getOrderId());
        userOrderSenderDao.insert(userOrderSender);
        // 记录日志
        UserInfo userInfo = userInfoDao.selectById(orderDTO.getUserId());
        String content = "普通用户:" + userInfo.getUsername() + ",下单啦!!!";
        commonService.recordLog(order.getOrderId(), content);
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
        queryWrapper.eq("user_is_deleted", NOT_DELETED);
        List<UserOrderSender> userOrderSenderList = userOrderSenderDao.selectList(queryWrapper);
        List<String> orderIdList = userOrderSenderList.stream().map(UserOrderSender::getOrderId).collect(toList());
        // 构造返回数据
        List<OrderVO> orderVOList = new ArrayList<>();
        if (ObjectUtils.isNotNull(orderIdList)) {
            // 获取订单
            LambdaQueryWrapper<Order> querylambdaWrapper = new LambdaQueryWrapper<>();
            // 构建查询条件
            querylambdaWrapper.eq(ObjectUtils.isNotNull(pageDTO.getOrderStatus()), Order::getOrderStatus, pageDTO.getOrderStatus())
                    .between(ObjectUtils.isNotNull(pageDTO.getStartTime())
                            && ObjectUtils.isNotNull(pageDTO.getEndTime()), Order::getCreateTime, pageDTO.getStartTime(), pageDTO.getEndTime())
                    .in(ObjectUtils.isNotNull(orderIdList), Order::getOrderId, orderIdList);
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
        return ImmutableMap.<String, Object>builder()
                .put(PAGE_TOTAL, 0)
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
        // 用户删除订单,删除用户-订单-配送员信息表
        UpdateWrapper<UserOrderSender> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("user_is_deleted", DELETED)
                .eq("user_id", orderDTO.getUserId())
                .eq("order_id", orderDTO.getOrderId());
        userOrderSenderDao.update(null, updateWrapper);
        // 记录日志
        UserInfo userInfo = userInfoDao.selectById(orderDTO.getUserId());
        String content = "普通用户:" + userInfo.getUsername() + ",删除订单!!!";
        commonService.recordLog(orderDTO.getOrderId(), content);
    }

    /**
     * 普通用户-取消订单
     *
     * @param orderId 订单id
     * @return
     */
    @Override
    public void userCancelOrder(String orderId, Integer userId) {
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("order_status", OrderStatusEnum.CANCEL_ORDER.getStatus())
                .set("pay_status", PayStatusEnum.HAVE_REFUND.getStatus())
                .eq("order_id", orderId);
        orderDao.update(null, updateWrapper);
        // 记录日志
        UserInfo userInfo = userInfoDao.selectById(userId);
        String content = "普通用户:" + userInfo.getUsername() + ",取消订单!!!";
        commonService.recordLog(orderId, content);
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
                .eq("order_id", orderDTO.getOrderId());
        orderDao.update(null, orderWrapper);
        // 修改用户-订单-配送员中间表
        UpdateWrapper<UserOrderSender> userOrderSenderUpdateWrapper = new UpdateWrapper<>();
        userOrderSenderUpdateWrapper.set("sender_id", orderDTO.getUserId())
                .eq("order_id", orderDTO.getOrderId());
        userOrderSenderDao.update(null, userOrderSenderUpdateWrapper);
        // 记录日志
        Sender sender = senderDao.selectById(orderDTO.getUserId());
        String content = "";
        if (orderDTO.getOrderStatus().equals(OrderStatusEnum.HAVE_ORDER.getStatus())) {
            content = "配送员:" + sender.getUsername() + ",已经接单!!!";
        } else if (orderDTO.getOrderStatus().equals(OrderStatusEnum.DISTRIBUTION.getStatus())) {
            content = "配送员:" + sender.getUsername() + ",正在配送中!!!";
        } else if (orderDTO.getOrderStatus().equals(OrderStatusEnum.FINISH_ORDER.getStatus())) {
            content = "配送员:" + sender.getUsername() + ",完成订单!!!";
        }
        commonService.recordLog(orderDTO.getOrderId(), content);
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
        List<String> orderIdList = userOrderSenderList.stream().map(UserOrderSender::getOrderId).collect(toList());

        List<OrderVO> orderVOList = new ArrayList<>();

        if (ObjectUtils.isNotNull(orderIdList)) {
            // 获取订单
            LambdaQueryWrapper<Order> querylambdaWrapper = new LambdaQueryWrapper<>();
            // 构建查询条件
            querylambdaWrapper.eq(ObjectUtils.isNotNull(pageDTO.getOrderStatus()), Order::getOrderStatus, pageDTO.getOrderStatus())
                    .between(ObjectUtils.isNotNull(pageDTO.getStartTime())
                            && ObjectUtils.isNotNull(pageDTO.getEndTime()), Order::getCreateTime, pageDTO.getStartTime(), pageDTO.getEndTime())
                    .in(Order::getOrderId, orderIdList)
                    .orderByAsc(Order::getOrderStatus);


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
        return ImmutableMap.<String, Object>builder()
                .put(PAGE_TOTAL, 0)
                .put(ORDER_LIST, orderVOList)
                .build();
    }

    @Override
    public MessUtil faceMatch(UserFace userFace) {
        QueryWrapper<UserOrderSender> userOrderSenderWrapper = new QueryWrapper<>();
        userOrderSenderWrapper.eq("sender_id", userFace.getUserId())
                .eq("order_id", userFace.getOrderId());
        UserOrderSender userOrderSender = userOrderSenderDao.selectOne(userOrderSenderWrapper);
        UserInfo user = userInfoDao.selectById(userOrderSender.getUserId());
        user.setImg(userFace.getImg());
        MessUtil resBody = new MessUtil();
        resBody.setStatus(0);
        resBody.setMsg("系统不存在您的人脸或者您已被禁用-请注册登录并绑定人脸");
        if (user.getImg() != null) {
            byte[] bytes = ImageUtils.base64ToByte(user.getImg());
            FaceData faceData = null;
            try {
                faceData = FaceUtils.getFaceData(bytes);
            } catch (Exception e) {
                e.printStackTrace();
                resBody.setStatus(0);
                resBody.setMsg("未检测到人脸-请正对摄像头重新识别-也可能你的浏览器没唤起摄像头");
                return resBody;
            }


            //判断是否检测到人脸

            if (faceData == null || faceData.getValidateFace() != 0) {
                resBody.setStatus(0);
                resBody.setMsg("人脸检测失败-请正对摄像头");

            } else if (faceData.getValidatePoint() != 0) {
                resBody.setStatus(0);
                resBody.setMsg("获取人脸特征值失败-请重新采集");
            } else {//开始比对
                //先查出所有的启用的用户

                UserInfo o = new UserInfo();
                o.setStatus(1);
                List<UserInfo> list = userInfoService.getList(o);
                for (UserInfo uu : list) {
                    if (uu.getFaceData() != null) {
                        CompareFace compare = FaceUtils.compare(faceData.getFaceData(), uu.getFaceData());

                        if (compare.getScoreCode() != 0) {
                            resBody.setStatus(0);
                            resBody.setMsg("识别失败，请重新识别");
                        }

                        if (compare.getScore() >= 0.8) {
                            uu.setLoginType("userinfo");
                            SessionUtil.getSession().setAttribute(USER_ID, uu.getId());
                            resBody.setStatus(1);
                            //resBody.setObj(uu);
                            resBody.setMsg("识别成功,完成订单");
                            return resBody;

                        }

                    }
                }
            }
        }
        return resBody;
    }

    ///**
    // * 配送员-删除订单
    // *
    // * @param orderDTO 订单信息
    // * @return
    // */
    //@Override
    //public void senderDeleteOrder(OrderDTO orderDTO) {
    //    // 删除订单表
    //    orderDao.deleteById(orderDTO.getOrderId());
    //    // 删除用户-订单-配送员信息表
    //    QueryWrapper<UserOrderSender> queryWrapper = new QueryWrapper<>();
    //    queryWrapper.eq("user_id", orderDTO.getUserId())
    //            .eq("order_id", orderDTO.getOrderId());
    //    userOrderSenderDao.delete(queryWrapper);
    //}
}
