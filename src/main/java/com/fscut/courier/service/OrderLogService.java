package com.fscut.courier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.OrderLog;

import java.util.Map;

/**
 * @author lxw
 */
public interface OrderLogService extends IService<OrderLog> {
    Map<String, Object> orderLogDisplay(PageDTO pageDTO);
}
