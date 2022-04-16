package com.fscut.courier.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fscut.courier.model.po.Order;
import org.springframework.stereotype.Repository;

/**
 * @author lxw
 */
@Repository
public interface OrderDao extends BaseMapper<Order> {

}
