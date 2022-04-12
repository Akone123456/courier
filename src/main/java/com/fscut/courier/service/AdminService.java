package com.fscut.courier.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fscut.courier.bean.Admin;
import com.fscut.courier.dao.AdminDao;
import com.fscut.courier.utils.MessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends ServiceImpl<AdminDao, Admin> {

    @Autowired
    AdminDao adminDao;
    public MessUtil login(Admin o) {
        MessUtil messUtil=new MessUtil();

        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<Admin>(o);
        Admin  admin=adminDao.selectOne(adminQueryWrapper);

        if(admin ==null){
            messUtil.setStatus(0);
            messUtil.setMsg("账号密码错误");
        }else{
            admin.setLoginType("admin");
            messUtil.setStatus(1);
            messUtil.setMsg("登陆成功");

            messUtil.setObj(admin);
        }

        return  messUtil;
    }
}
