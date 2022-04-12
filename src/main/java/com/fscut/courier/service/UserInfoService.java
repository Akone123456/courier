package com.fscut.courier.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fscut.courier.bean.UserInfo;
import com.fscut.courier.dao.UserInfoDao;
import com.fscut.courier.utils.MessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserInfoService extends ServiceImpl<UserInfoDao, UserInfo> {

    @Autowired
    UserInfoDao userInfoDao;


    /**
     * 分页展示普通用户
     * @param pageIndex
     * @param pageSize
     * @param o
     * @return
     */
    public MessUtil page(Integer pageIndex, Integer pageSize, UserInfo o){

        MessUtil messUtil=new MessUtil();
        PageHelper.startPage(pageIndex,pageSize,"id desc");
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = Wrappers.lambdaQuery();
        //条件查询
        if (Objects.nonNull(o.getPhone()) && o.getPhone().trim().length()>0 ) {
            lambdaQueryWrapper.eq(UserInfo ::getPhone, o.getPhone());
        }
        if (Objects.nonNull(o.getUsername()) && o.getUsername().trim().length()>0 ) {
            lambdaQueryWrapper.like(UserInfo ::getUsername, o.getUsername());
        }
        if (Objects.nonNull(o.getStatus()) ) {
            lambdaQueryWrapper.eq(UserInfo ::getStatus, o.getStatus());
        }
        List<UserInfo> userInfoList = userInfoDao.selectList(lambdaQueryWrapper);
        PageInfo<UserInfo> pageInfo=new PageInfo(userInfoList,pageSize);
        messUtil.setObj(pageInfo);
        return  messUtil;
    }

    public MessUtil login(UserInfo o) {
        MessUtil messUtil = new MessUtil();
        UserInfo userInfo = userInfoDao.selectOne(new QueryWrapper<>(o));
        if(userInfo != null){//说明账号密码输入正确
            messUtil.setObj(userInfo);
            messUtil.setStatus(1);
            messUtil.setMsg("登录成功");
        }else{
            messUtil.setStatus(0);
            messUtil.setMsg("登录失败");
        }
        return messUtil;
    }


    public List<UserInfo> getList(UserInfo o) {
        List<UserInfo> li =userInfoDao.selectList(new QueryWrapper<>(o));
        return li;

    }
}
