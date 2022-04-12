package com.fscut.courier.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fscut.courier.bean.Sender;
import com.fscut.courier.dao.SenderDao;
import com.fscut.courier.utils.MessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SenderService extends ServiceImpl<SenderDao, Sender> {

    @Autowired
    SenderDao senderDao;


    /**
     * 分页展示配送员
     * @param pageIndex
     * @param pageSize
     * @param o
     * @return
     */
    public MessUtil page(Integer pageIndex, Integer pageSize, Sender o){

        MessUtil messUtil=new MessUtil();
        PageHelper.startPage(pageIndex,pageSize,"id desc");
        LambdaQueryWrapper<Sender> lambdaQueryWrapper = Wrappers.lambdaQuery();
        //条件查询
        if (Objects.nonNull(o.getPhone()) && o.getPhone().trim().length()>0 ) {
            lambdaQueryWrapper.eq(Sender ::getPhone, o.getPhone());
        }
        if (Objects.nonNull(o.getUsername()) && o.getUsername().trim().length()>0 ) {
            lambdaQueryWrapper.like(Sender ::getUsername, o.getUsername());
        }
        if (Objects.nonNull(o.getRealname()) && o.getRealname().trim().length()>0 ) {
            lambdaQueryWrapper.like(Sender ::getRealname, o.getRealname());
        }
        if (Objects.nonNull(o.getStatus()) ) {
            lambdaQueryWrapper.eq(Sender ::getStatus, o.getStatus());
        }
        List<Sender> senderList = senderDao.selectList(lambdaQueryWrapper);
        PageInfo<Sender> pageInfo=new PageInfo(senderList,pageSize);
        messUtil.setObj(pageInfo);
        return  messUtil;
    }

    public MessUtil login(Sender o) {
        MessUtil messUtil = new MessUtil();
        Sender sender = senderDao.selectOne(new QueryWrapper<>(o));
        if(sender != null){//说明账号密码输入正确
            messUtil.setObj(sender);
            messUtil.setStatus(1);
            messUtil.setMsg("登录成功");
        }else{
            messUtil.setStatus(0);
            messUtil.setMsg("登录失败");
        }
        return messUtil;
    }


}
