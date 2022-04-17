package com.fscut.courier.controller;

import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.service.OrderLogService;
import com.fscut.courier.utils.ResultUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fscut.courier.utils.ResultUtil.ok;

/**
 * @author lxw
 */
@Validated
@RestController
@RequestMapping("orderLog")
public class OrderLogController {

    @Autowired
    private OrderLogService orderLogService;

    @PostMapping("display")
    public ResultUtil.Result orderLogDisplay(@RequestBody @Validated({PageDTO.AdminOrder.class}) PageDTO pageDTO){
        return ok(orderLogService.orderLogDisplay(pageDTO));
    }
}
