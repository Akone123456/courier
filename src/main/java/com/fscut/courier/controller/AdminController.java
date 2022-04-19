package com.fscut.courier.controller;

import com.fscut.courier.model.po.Admin;
import com.fscut.courier.service.AdminService;
import com.fscut.courier.utils.MessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;


    /**
     * 管理员登录和咨询师登录
     *
     * @param admin
     * @return
     */
    @RequestMapping("/login")
    public MessUtil login(Admin admin) {

        MessUtil mess = adminService.login(admin);
        return mess;
    }

    /**
     * 修改密码
     */

    @RequestMapping("/updateUpass")
    public MessUtil updateUpass(String oldp, String newp, String newp2, Admin o) {
        MessUtil resBody = new MessUtil();
        Admin oo = adminService.getById(o.getId());
        if (!oo.getPassword().equals(oldp)) {
            resBody.setStatus(0);
            resBody.setMsg("原密码错误");
            return resBody;
        }
        if (!newp.equals(newp2)) {
            resBody.setStatus(0);
            resBody.setMsg("密码不一致");
            return resBody;
        }
        o.setPassword(newp);

        adminService.saveOrUpdate(o);
        resBody.setStatus(1);
        resBody.setMsg("修改成功");
        return resBody;
    }
}
