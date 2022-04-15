package com.fscut.courier.controller;

import com.fscut.courier.model.dto.AdminDTO;
import com.fscut.courier.model.dto.SenderDTO;
import com.fscut.courier.model.dto.UserInfoDTO;
import com.fscut.courier.model.po.UserInfo;
import com.fscut.courier.service.AdminService;
import com.fscut.courier.service.SenderService;
import com.fscut.courier.service.UserInfoService;
import com.fscut.courier.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.fscut.courier.utils.ResultUtil.ok;


/**
 * @author lxw
 */
@Validated
@RestController
@RequestMapping("center")
public class CenterController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SenderService senderService;
    @Autowired
    private AdminService adminService;

    /**
     * 普通用户个人中心
     */
    @GetMapping("user/display/{userId}")
    public ResultUtil.Result userDisplay(@PathVariable("userId") Integer userId) {

        return ok(userInfoService.display(userId));
    }

    /**
     * 配送员个人中心
     */
    @GetMapping("sender/display/{userId}")
    public ResultUtil.Result senderDisplay(@PathVariable("userId") Integer userId) {

        return ok(senderService.display(userId));
    }

    /**
     * 管理员个人中心
     */
    @GetMapping("admin/display/{userId}")
    public ResultUtil.Result adminDisplay(@PathVariable("userId") Integer userId) {

        return ok(adminService.display(userId));
    }

    /**
     * 普通用户修改信息
     *
     * @param userInfoDTO 用户信息
     * @return
     */
    @PostMapping("user/updateAccount")
    public ResultUtil.Result userUpdateAccount(@RequestBody @Validated({UserInfoDTO.Update.class}) UserInfoDTO userInfoDTO) {
        userInfoService.updateAccount(userInfoDTO);
        return ok();
    }

    /**
     * 配送员修改信息
     *
     * @param senderDTO 配送员用户信息
     * @return
     */
    @PostMapping("sender/updateAccount")
    public ResultUtil.Result senderUpdateAccount(@RequestBody @Validated({SenderDTO.Update.class}) SenderDTO senderDTO) {
        senderService.updateAccount(senderDTO);
        return ok();
    }

    /**
     * 普通用户修改密码
     *
     * @param userInfoDTO 用户信息
     * @return
     */
    @PostMapping("user/updatePswd")
    public ResultUtil.Result userUpdatePswd(@RequestBody @Validated({UserInfoDTO.UpdatePswd.class}) UserInfoDTO userInfoDTO) {
        userInfoService.updatePswd(userInfoDTO);
        return ok();
    }

    /**
     * 配送员修改密码
     *
     * @param senderDTO 配送员用户信息
     * @return
     */
    @PostMapping("sender/updatePswd")
    public ResultUtil.Result senderUpdatePswd(@RequestBody @Validated({SenderDTO.UpdatePswd.class}) SenderDTO senderDTO) {
        senderService.updatePswd(senderDTO);
        return ok();
    }

    /**
     * 管理员修改密码
     *
     * @param adminDTO 管理员用户信息
     * @return
     */
    @PostMapping("admin/updatepswd")
    public ResultUtil.Result AdminUpdatePswd(@RequestBody @Validated({AdminDTO.UpdatePswd.class}) AdminDTO adminDTO) {
        adminService.updatePswd(adminDTO);
        return ok();
    }
}
