package com.fscut.courier.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fscut.courier.dao.OrderLogDao;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.OrderLog;
import com.fscut.courier.model.vo.OrderLogVO;
import com.fscut.courier.model.vo.factory.OrderLogVOFactory;
import com.fscut.courier.service.OrderLogService;
import com.fscut.courier.service.OrderService;
import com.google.common.collect.ImmutableMap;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fscut.courier.utils.ConstValue.ORDER_LOG_LIST;
import static com.fscut.courier.utils.ConstValue.PAGE_TOTAL;

/**
 * @author lxw
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderLogServiceImpl extends ServiceImpl<OrderLogDao, OrderLog> implements OrderLogService {
    @Autowired
    private OrderLogDao orderLogDao;

    /**
     * 订单日志
     *
     * @param pageDTO 分页信息
     * @return
     */
    @Override
    public Map<String, Object> orderLogDisplay(PageDTO pageDTO) {
        LambdaQueryWrapper<OrderLog> orderLogWrapper = new LambdaQueryWrapper<>();
        // 构造查询条件
        orderLogWrapper.eq(ObjectUtils.isNotNull(pageDTO.getOrderId()), OrderLog::getOrderId, pageDTO.getOrderId());
        // 分页
        Page<OrderLog> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<OrderLog> orderLogPage = orderLogDao.selectPage(page, orderLogWrapper);
        // 构造返回数据
        List<OrderLogVO> orderLogVOList = new ArrayList<>();
        orderLogPage.getRecords().forEach(orderLog -> {
            OrderLogVO orderLogVO = OrderLogVOFactory.createOrderLogVO(orderLog);
            orderLogVOList.add(orderLogVO);
        });
        return ImmutableMap.<String, Object>builder()
                .put(PAGE_TOTAL, orderLogPage.getTotal())
                .put(ORDER_LOG_LIST, orderLogVOList)
                .build();

    }
}
