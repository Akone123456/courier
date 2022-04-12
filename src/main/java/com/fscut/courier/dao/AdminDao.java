package com.fscut.courier.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fscut.courier.bean.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface AdminDao extends BaseMapper<Admin> {

}
