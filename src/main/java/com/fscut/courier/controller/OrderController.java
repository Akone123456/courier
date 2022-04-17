package com.fscut.courier.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fscut.courier.dao.UserInfoDao;
import com.fscut.courier.dao.UserOrderSenderDao;
import com.fscut.courier.model.dto.OrderDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.UserFace;
import com.fscut.courier.model.po.UserInfo;
import com.fscut.courier.model.po.UserOrderSender;
import com.fscut.courier.service.OrderService;
import com.fscut.courier.service.UserInfoService;
import com.fscut.courier.utils.*;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

import java.util.List;

import static com.fscut.courier.utils.ConstValue.USER_ID;
import static com.fscut.courier.utils.ResultUtil.ok;

/**
 * @author lxw
 */
@Validated
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 普通用户下单
     *
     * @param orderDTO 订单信息
     * @return
     */
    @PostMapping("placeOrder")
    public ResultUtil.Result placeOrder(@RequestBody @Validated({OrderDTO.Save.class}) OrderDTO orderDTO) {
        orderService.placeOrder(orderDTO);
        return ok();
    }

    /**
     * 普通用户-我的订单
     *
     * @param pageDTO 订单查询条件
     * @return
     */
    @PostMapping("user")
    public ResultUtil.Result userOrder(@RequestBody @Validated(PageDTO.User.class) PageDTO pageDTO) {

        return ok(orderService.userOrder(pageDTO));
    }

    /**
     * 普通用户-删除订单
     *
     * @param orderDTO 订单信息
     * @return
     */
    @PostMapping("user/delete")
    public ResultUtil.Result userDeleteOrder(@RequestBody @Validated(OrderDTO.UserDelete.class) OrderDTO orderDTO) {
        orderService.userDeleteOrder(orderDTO);
        return ok();
    }

    /**
     * 普通用户-取消订单
     *
     * @param orderId 订单id
     * @return
     */
    @GetMapping("cancel/{orderId}/{userId}")
    public ResultUtil.Result userCancelOrder(@PathVariable("orderId") String orderId,
                                             @PathVariable("userId") Integer userId) {
        orderService.userCancelOrder(orderId, userId);
        return ok();
    }

    /**
     * 配送员-接单大厅
     *
     * @param pageDTO 分页信息
     * @return
     */
    @PostMapping("orderHall")
    public ResultUtil.Result orderHall(@RequestBody @Validated(PageDTO.OrderHall.class) PageDTO pageDTO) {
        return ok(orderService.orderHall(pageDTO));
    }

    /**
     * 配送员-接单,配送,完成.
     *
     * @param orderDTO 订单信息
     * @return
     */
    @PostMapping("receiveOrder")
    public ResultUtil.Result receiveOrder(@RequestBody @Validated(OrderDTO.SenderRecieve.class) OrderDTO orderDTO) {
        orderService.receiveOrder(orderDTO);
        return ok();
    }

    /**
     * 配送员-我的订单
     *
     * @param pageDTO 分页信息
     * @return
     */
    @PostMapping("sender")
    public ResultUtil.Result senderOrder(@RequestBody @Validated(PageDTO.User.class) PageDTO pageDTO) {
        return ok(orderService.senderOrder(pageDTO));
    }

    /**
     * 完成订单进行人脸比对
     */
    /**
     * 人脸识别比对登录
     *
     * @param
     * @return
     */
    @RequestMapping("/faceMatch")
    public MessUtil searchUser(UserFace userFace) {
        return orderService.faceMatch(userFace);
    }

    ///**
    // * 配送员-删除订单
    // *
    // * @param orderDTO 订单信息
    // * @return
    // */
    //@PostMapping("sender/delete")
    //public ResultUtil.Result senderDeleteOrder(@RequestBody @Validated(OrderDTO.UserDelete.class) OrderDTO orderDTO){
    //    orderService.senderDeleteOrder(orderDTO);
    //    return ok();
    //}
}
