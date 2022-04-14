package com.fscut.courier.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fscut.courier.model.dto.AdminDTO;
import com.fscut.courier.model.po.Admin;
import com.fscut.courier.dao.AdminDao;
import com.fscut.courier.model.po.Sender;
import com.fscut.courier.model.vo.AdminVO;
import com.fscut.courier.model.vo.SenderVO;
import com.fscut.courier.model.vo.factory.AdminVOFactory;
import com.fscut.courier.model.vo.factory.SenderVOFactory;
import com.fscut.courier.utils.MessUtil;
import com.fscut.courier.utils.SessionUtil;
import com.fscut.courier.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fscut.courier.utils.ConstValue.*;

@Service
public class AdminService extends ServiceImpl<AdminDao, Admin> {

    @Autowired
    AdminDao adminDao;

    public MessUtil login(Admin o) {
        MessUtil messUtil = new MessUtil();

        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<Admin>(o);

        Admin admin = adminDao.selectOne(adminQueryWrapper);

        if (admin == null) {
            messUtil.setStatus(0);
            messUtil.setMsg("账号密码错误");
        } else {
            admin.setLoginType("admin");
            SessionUtil.getSession().setAttribute(USER_ID, admin.getId());
            messUtil.setStatus(1);
            messUtil.setMsg("登陆成功");

            messUtil.setObj(admin);
        }

        return messUtil;
    }

    /**
     * 管理员个人中心
     *
     * @return
     */
    public AdminVO display() {
        // 获取用户id
        int userId = (Integer) SessionUtil.getSession().getAttribute(USER_ID);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        Admin admin = adminDao.selectOne(queryWrapper);
        // 判断用户是否存在
        ValidateUtil.logicalNotNull(admin, ADMIN_USER_NOT_EXIST);
        AdminVO adminVO = AdminVOFactory.createAdminVO(admin);
        return adminVO;
    }

    /**
     * 管理修改密码
     *
     * @param adminDTO 管理员用户信息
     */
    public void updatePswd(AdminDTO adminDTO) {
        UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
        // 原始密码比对
        String originalPassword = adminDTO.getOriginalPassword();
        Admin admin = adminDao.selectById(adminDTO.getUserId());
        // 判断用户是否存在
        ValidateUtil.logicalNotNull(admin, ADMIN_USER_NOT_EXIST);
        String password = admin.getPassword();
        ValidateUtil.logicalTrue(password.equals(originalPassword), ORIGINAL_PASSWORD);
        updateWrapper.set("password", adminDTO.getNewPassword())
                .eq("id", adminDTO.getUserId());
        adminDao.update(null, updateWrapper);
    }

}
