package com.fscut.courier.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fscut.courier.model.dto.SenderDTO;
import com.fscut.courier.model.dto.UserInfoDTO;
import com.fscut.courier.model.po.UserInfo;
import com.fscut.courier.model.vo.SenderVO;
import com.fscut.courier.model.vo.UserInfoVO;
import com.fscut.courier.model.vo.factory.SenderVOFactory;
import com.fscut.courier.model.vo.factory.UserInfoVOFactory;
import com.fscut.courier.utils.SessionUtil;
import com.fscut.courier.utils.ValidateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fscut.courier.model.po.Sender;
import com.fscut.courier.dao.SenderDao;
import com.fscut.courier.utils.MessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.fscut.courier.utils.ConstValue.*;

@Service
public class SenderService extends ServiceImpl<SenderDao, Sender> {

    @Autowired
    private SenderDao senderDao;


    /**
     * 分页展示配送员
     *
     * @param pageIndex
     * @param pageSize
     * @param o
     * @return
     */
    public MessUtil page(Integer pageIndex, Integer pageSize, Sender o) {

        MessUtil messUtil = new MessUtil();
        PageHelper.startPage(pageIndex, pageSize, "id desc");
        LambdaQueryWrapper<Sender> lambdaQueryWrapper = Wrappers.lambdaQuery();
        //条件查询
        if (Objects.nonNull(o.getPhone()) && o.getPhone().trim().length() > 0) {
            lambdaQueryWrapper.eq(Sender::getPhone, o.getPhone());
        }
        if (Objects.nonNull(o.getUsername()) && o.getUsername().trim().length() > 0) {
            lambdaQueryWrapper.like(Sender::getUsername, o.getUsername());
        }
        if (Objects.nonNull(o.getRealname()) && o.getRealname().trim().length() > 0) {
            lambdaQueryWrapper.like(Sender::getRealname, o.getRealname());
        }
        if (Objects.nonNull(o.getStatus())) {
            lambdaQueryWrapper.eq(Sender::getStatus, o.getStatus());
        }
        List<Sender> senderList = senderDao.selectList(lambdaQueryWrapper);
        PageInfo<Sender> pageInfo = new PageInfo(senderList, pageSize);
        messUtil.setObj(pageInfo);
        return messUtil;
    }

    public MessUtil login(Sender o) {
        MessUtil messUtil = new MessUtil();
        Sender sender = senderDao.selectOne(new QueryWrapper<>(o));
        if (sender != null) {//说明账号密码输入正确
            messUtil.setObj(sender);
            messUtil.setStatus(1);
            messUtil.setMsg("登录成功");
        } else {
            messUtil.setStatus(0);
            messUtil.setMsg("登录失败");
        }
        return messUtil;
    }

    /**
     * 配送员个人中心
     *
     * @return
     */
    public SenderVO display() {
        // 获取用户id
        //int userId = (Integer) SessionUtil.getSession().getAttribute(USER_ID);
        QueryWrapper<Sender> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 4);
        Sender sender = senderDao.selectOne(queryWrapper);
        // 判断用户是否存在
        ValidateUtil.logicalNotNull(sender, SENDER_USER_NOT_EXIST);
        SenderVO SenderVo = SenderVOFactory.createSenderVo(sender);
        return SenderVo;
    }

    /**
     * 配送员修改信息
     *
     * @param senderDTO 配送员信息
     */
    public void updateAccount(SenderDTO senderDTO) {
        UpdateWrapper<Sender> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("phone", senderDTO.getPhone())
                .set("note", senderDTO.getNote())
                .eq("id", senderDTO.getUserId());
        senderDao.update(null, updateWrapper);
    }

    /**
     * 配送员修改密码
     *
     * @param senderDTO 配送员信息
     */
    public void updatePswd(SenderDTO senderDTO) {
        UpdateWrapper<Sender> updateWrapper = new UpdateWrapper<>();
        // 原始密码比对
        String originalPassword = senderDTO.getOriginalPassword();
        Sender sender = senderDao.selectById(senderDTO.getUserId());
        // 判断用户是否存在
        ValidateUtil.logicalNotNull(sender, SENDER_USER_NOT_EXIST);
        String password = sender.getPassword();
        ValidateUtil.logicalTrue(password.equals(originalPassword), ORIGINAL_PASSWORD);
        updateWrapper.set("password", senderDTO.getNewPassword())
                .eq("id", senderDTO.getUserId());
        senderDao.update(null, updateWrapper);
    }


}
