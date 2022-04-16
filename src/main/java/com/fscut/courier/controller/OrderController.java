package com.fscut.courier.controller;

import com.fscut.courier.model.dto.OrderDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.service.OrderService;
import com.fscut.courier.utils.ResultUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

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
     * @param pageDTO  订单查询条件
     * @return
     */
    @PostMapping("user")
    public ResultUtil.Result userOrder(@RequestBody @Validated(PageDTO.User.class) PageDTO pageDTO) {

        return ok(orderService.userOrder(pageDTO));
    }
    /**
     * 普通用户-删除订单
     *
     * @param orderDTO  订单信息
     * @return
     */
    @PostMapping("user/delete")
    public ResultUtil.Result userDeleteOrder(@RequestBody @Validated(OrderDTO.UserDelete.class) OrderDTO orderDTO) {
        orderService.userDeleteOrder(orderDTO);
        return ok();
    }
    @GetMapping("cancel/{orderId}")
    public ResultUtil.Result userCancelOrder(@PathVariable("orderId") Integer orderId){
        orderService.userCancelOrder(orderId);
        return ok();
    }


}
