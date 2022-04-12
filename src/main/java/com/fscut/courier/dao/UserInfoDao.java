package com.fscut.courier.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fscut.courier.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfo> {

}
