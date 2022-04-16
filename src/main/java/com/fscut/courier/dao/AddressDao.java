package com.fscut.courier.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fscut.courier.model.po.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author lxw
 */
@Repository
public interface AddressDao extends BaseMapper<Address> {
    Page<Address> selectAddress(@Param("page") Page<Address> page, @Param("userId") Integer userId);
    void updateIsDefault(@Param("isDefault") Integer isDefault,@Param("userId")Integer userId);
    List<Address> selectAllByUserId(@Param("userId") Integer userId);
}
