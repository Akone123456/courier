package com.fscut.courier.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fscut.courier.dao.SenderDao;
import com.fscut.courier.model.dto.UserInfoDTO;
import com.fscut.courier.model.po.Sender;
import com.fscut.courier.model.vo.SenderVO;
import com.fscut.courier.model.vo.UserInfoVO;
import com.fscut.courier.model.vo.factory.SenderVOFactory;
import com.fscut.courier.model.vo.factory.UserInfoVOFactory;
import com.fscut.courier.utils.SessionUtil;
import com.fscut.courier.utils.ValidateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fscut.courier.model.po.UserInfo;
import com.fscut.courier.dao.UserInfoDao;
import com.fscut.courier.utils.MessUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

import static com.fscut.courier.utils.ConstValue.*;

@Service
public class UserInfoService extends ServiceImpl<UserInfoDao, UserInfo> {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private SenderDao senderDao;

    /**
     * 分页展示普通用户
     *
     * @param pageIndex
     * @param pageSize
     * @param o
     * @return
     */
    public MessUtil page(Integer pageIndex, Integer pageSize, UserInfo o) {

        MessUtil messUtil = new MessUtil();
        PageHelper.startPage(pageIndex, pageSize, "id desc");
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = Wrappers.lambdaQuery();
        //条件查询
        if (Objects.nonNull(o.getPhone()) && o.getPhone().trim().length() > 0) {
            lambdaQueryWrapper.eq(UserInfo::getPhone, o.getPhone());
        }
        if (Objects.nonNull(o.getUsername()) && o.getUsername().trim().length() > 0) {
            lambdaQueryWrapper.like(UserInfo::getUsername, o.getUsername());
        }
        if (Objects.nonNull(o.getStatus())) {
            lambdaQueryWrapper.eq(UserInfo::getStatus, o.getStatus());
        }
        List<UserInfo> userInfoList = userInfoDao.selectList(lambdaQueryWrapper);
        PageInfo<UserInfo> pageInfo = new PageInfo(userInfoList, pageSize);
        messUtil.setObj(pageInfo);
        return messUtil;
    }

    public MessUtil login(UserInfo o) {
        MessUtil messUtil = new MessUtil();
        UserInfo userInfo = userInfoDao.selectOne(new QueryWrapper<>(o));
        if (userInfo != null) {//说明账号密码输入正确
            messUtil.setObj(userInfo);
            messUtil.setStatus(1);
            messUtil.setMsg("登录成功");
        } else {
            messUtil.setStatus(0);
            messUtil.setMsg("登录失败");
        }
        return messUtil;
    }

    public List<UserInfo> getList(UserInfo o) {
        List<UserInfo> li = userInfoDao.selectList(new QueryWrapper<>(o));
        return li;

    }

    /**
     * 普通用户个人中心
     *
     * @return
     */
    public UserInfoVO display() {
        // 获取用户id
        int userId = (Integer) SessionUtil.getSession().getAttribute(USER_ID);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        UserInfo userInfo = userInfoDao.selectOne(queryWrapper);
        // 判断用户是否存在
        ValidateUtil.logicalNotNull(userInfo, USER_NOT_EXIST);
        UserInfoVO userInfoVo = UserInfoVOFactory.createTaskVo(userInfo);
        return userInfoVo;
    }

    /**
     * 普通用户修改信息
     *
     * @param userInfoDTO 用户信息
     */
    public void updateAccount(UserInfoDTO userInfoDTO) {
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("phone", userInfoDTO.getPhone())
                .set("note", userInfoDTO.getNote())
                .set("photo", userInfoDTO.getPhoto())
                .eq("id", userInfoDTO.getUserId());
        userInfoDao.update(null, updateWrapper);
    }

    /**
     * 普通用户修改密码
     *
     * @param userInfoDTO 用户信息
     */
    public void updatePswd(UserInfoDTO userInfoDTO) {
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        // 原始密码比对
        String originalPassword = userInfoDTO.getOriginalPassword();
        UserInfo userInfo = userInfoDao.selectById(userInfoDTO.getUserId());
        // 判断用户是否存在
        ValidateUtil.logicalNotNull(userInfo, USER_NOT_EXIST);
        String password = userInfo.getPassword();
        ValidateUtil.logicalTrue(password.equals(originalPassword), ORIGINAL_PASSWORD);
        updateWrapper.set("password", userInfoDTO.getNewPassword())
                .eq("id", userInfoDTO.getUserId());
        userInfoDao.update(null, updateWrapper);
    }

}
