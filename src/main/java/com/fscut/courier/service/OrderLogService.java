package com.fscut.courier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.OrderLog;

import java.util.Map;

/**
 * @author lxw
 */
public interface OrderLogService extends IService<OrderLog> {
    /**
     * 订单日志
     *
     * @param pageDTO 分页信息
     * @return
     */
    Map<String, Object> orderLogDisplay(PageDTO pageDTO);
}
