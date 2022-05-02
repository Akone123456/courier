package com.fscut.courier.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fscut.courier.model.po.Role;
import com.fscut.courier.model.po.UserInfo;
import com.fscut.courier.service.UserInfoService;
import com.fscut.courier.system.Sys;
import com.fscut.courier.utils.*;
import com.sun.DateUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.fscut.courier.utils.ConstValue.*;

/**
 * 管理端-普通用户control控制层
 */
@RestController
@RequestMapping("/admin/userinfo")
//@CrossOrigin
public class UserInfoController extends SendSmsController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RedisUtils redisUtils;

    //验证账号唯一性
    @RequestMapping("valiUserName")
    public MessUtil valiUserName(String username) {
        MessUtil messUtil = new MessUtil();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        UserInfo one = userInfoService.getOne(new QueryWrapper<>(userInfo));
        if (one != null) {
            messUtil.setStatus(0);
            messUtil.setMsg("此账号已被注册");
        } else {
            messUtil.setStatus(1);
            messUtil.setMsg("此账号可用");
        }
        return messUtil;
    }

    /**
     * 注册用户
     *
     * @param o
     * @return
     */
    @RequestMapping("/save")
    public MessUtil save(UserInfo o) {
        MessUtil messUtil = new MessUtil();
        //先判断手机号是否已经注册
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(o.getPhone());
        UserInfo one = userInfoService.getOne(new QueryWrapper<>(userInfo));
        if (one != null) {//说明该手机号已经注册请直接登录
            messUtil.setStatus(0);
            messUtil.setMsg("该手机号已经注册请直接登录");
            return messUtil;
        } else {//去注册
            try {
                //判断验证码输入是否正确
                if (map.get(o.getPhone()) != null && map.get(o.getPhone()).equals(o.getSmscode())) {
                    o.setStatus(1);
                    o.setCreateTime(DateUtils.getNowDateString());
                    o.setPhoto(Sys.defaultFace);
                    //o.setRole(UserRoleEnum.USER.getRole());
                    userInfoService.save(o);
                    System.out.println("注册的用户：" + o);
                    o.setLoginType("userinfo");
                    messUtil.setStatus(1);
                    messUtil.setObj(o);
                    messUtil.setMsg("注册成功");
                } else {
                    messUtil.setStatus(0);
                    messUtil.setMsg("验证码错误");
                }


            } catch (Exception e) {
                e.printStackTrace();
                messUtil.setStatus(0);
                messUtil.setMsg("操作失败");
            }
        }
        return messUtil;

    }

    //修改头像或者手机号啥的或者账号  -还有启用禁用也用这方法
    @PostMapping("/update")
    public MessUtil update(UserInfo o) {
        MessUtil messUtil = new MessUtil();
        try {
            if (o.getId() != null) {
                userInfoService.saveOrUpdate(o);
                messUtil.setStatus(1);
                messUtil.setObj(o);
                messUtil.setMsg("修改成功");
            } else {
                messUtil.setStatus(0);
                messUtil.setMsg("操作不合法");
            }
        } catch (Exception e) {
            e.printStackTrace();
            messUtil.setStatus(0);
            messUtil.setMsg("系统异常");
        }
        return messUtil;

    }

    /**
     * 账号密码登录
     *
     * @param o
     * @return
     */
    @PostMapping("/loginByPassword")
    public MessUtil loginByPassword(UserInfo o, HttpSession session) {
        MessUtil messUtil = new MessUtil();
        UserInfo employ = null;
        try {
            employ = userInfoService.getOne(new QueryWrapper<>(o));
            if (ObjectUtils.isNull(employ)) {
                messUtil.setStatus(0);
                messUtil.setMsg("账号或密码错误");
            } else if (employ.getStatus() == DELETED) {
                messUtil.setStatus(0);
                messUtil.setMsg("此账号已被禁用");
            } else if (employ == null) {
                messUtil.setStatus(0);
                messUtil.setMsg("账号密码错误，请重新登录");
            } else {
                employ.setLoginType("userinfo");
                SessionUtil.getSession().setAttribute(USER_ID, employ.getId());
                messUtil.setStatus(1);
                messUtil.setObj(employ);
                messUtil.setMsg("登录成功");
            }
        } catch (Exception e) {
            messUtil.setStatus(0);
            messUtil.setMsg("系统错误");
            e.printStackTrace();
        }

        return messUtil;
    }

    /**
     * 短信验证码登录
     *
     * @param phone
     * @param smscode
     * @return
     */
    @RequestMapping("/loginByCode")
    public MessUtil loginByCode(String phone, String smscode) {
        MessUtil messUtil = new MessUtil();
        //判断有没有这个用户
        try {
            UserInfo s = new UserInfo();
            s.setPhone(phone);
            UserInfo one = userInfoService.getOne(new QueryWrapper<>(s));
            if (one.getStatus() == DELETED) {
                messUtil.setStatus(0);
                messUtil.setMsg("此账号已被禁用");
            } else if (one == null) {
                messUtil.setStatus(0);
                messUtil.setMsg("此手机号还没注册");
            } else {
                //判断验证码是否正确
                String smscode_redis = redisUtils.get(SMS_CODE_ + phone);
                if (smscode_redis.equals(smscode)) {
                    one.setLoginType("userinfo");
                    SessionUtil.getSession().setAttribute(USER_ID, one.getId());
                    messUtil.setStatus(1);
                    messUtil.setObj(one);
                    messUtil.setMsg("登录成功");
                } else {
                    messUtil.setStatus(0);
                    messUtil.setMsg("验证码输入错误");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            messUtil.setStatus(0);
            messUtil.setMsg("系统错误");
        }
        return messUtil;
    }


    /**
     * 添加人脸特征值-采集人脸信息到数据库
     *
     * @param user
     * @return
     */
    @RequestMapping("/addFaceData")
    public MessUtil addFaceData(UserInfo user) {


        MessUtil resBody = new MessUtil();
        if (user.getImg() != null && user.getId() != null) {//必须让前端把用户id和采集的用户base64位的图片传递过来
            byte[] bytes = ImageUtils.base64ToByte(user.getImg());//base64位图转成byte[]

            FaceData faceData = null;
            try {
                faceData = FaceUtils.getFaceData(bytes);
            } catch (Exception e) {
                e.printStackTrace();
                resBody.setStatus(0);
                resBody.setMsg("未检测到人脸-请正对摄像头重新识别-也可能你的浏览器没唤起摄像头");
                return resBody;
            }

            //判断是否检测到人脸

            if (faceData == null || faceData.getValidateFace() != 0) {
                resBody.setStatus(0);
                resBody.setMsg("人脸检测失败-请正对摄像头");

            } else if (faceData.getValidatePoint() != 0) {
                resBody.setStatus(0);
                resBody.setMsg("获取人脸特征值失败-请重新采集");
            } else {
                user.setFaceData(faceData.getFaceData());
                userInfoService.saveOrUpdate(user);
                resBody.setStatus(1);
                resBody.setMsg("您的人脸采集成功");
            }

        } else {
            resBody.setStatus(0);
            resBody.setMsg("摄像头未开启或者是未登陆等违规操作");
        }
        return resBody;

    }


    /**
     * 人脸识别比对登录
     *
     * @param user
     * @return
     */
    @RequestMapping("/faceMatch")
    public MessUtil searchUser(UserInfo user) {
        MessUtil resBody = new MessUtil();
        resBody.setStatus(0);
        resBody.setMsg("系统不存在您的人脸或者您已被禁用-请注册登录并绑定人脸");
        // UserInfo one = userInfoService.getOne(new QueryWrapper<>(user));
        if (user.getImg() != null) {
            byte[] bytes = ImageUtils.base64ToByte(user.getImg());
            FaceData faceData = null;
            try {
                faceData = FaceUtils.getFaceData(bytes);
            } catch (Exception e) {
                e.printStackTrace();
                resBody.setStatus(0);
                resBody.setMsg("未检测到人脸-请正对摄像头重新识别-也可能你的浏览器没唤起摄像头");
                return resBody;
            }


            //判断是否检测到人脸

            if (faceData == null || faceData.getValidateFace() != 0) {
                resBody.setStatus(0);
                resBody.setMsg("人脸检测失败-请正对摄像头");

            } else if (faceData.getValidatePoint() != 0) {
                resBody.setStatus(0);
                resBody.setMsg("获取人脸特征值失败-请重新采集");
            } else {//开始比对
                //先查出所有的启用的用户

                UserInfo o = new UserInfo();
                o.setStatus(0);
                List<UserInfo> list = userInfoService.getList(o);
                for (UserInfo uu : list) {
                    if (uu.getFaceData() != null) {
                        CompareFace compare = FaceUtils.compare(faceData.getFaceData(), uu.getFaceData());

                        if (compare.getScoreCode() != 0) {
                            resBody.setStatus(0);
                            resBody.setMsg("识别失败，请重新识别");
                        }

                        if (compare.getScore() >= 0.8) {
                            uu.setLoginType("userinfo");
                            SessionUtil.getSession().setAttribute(USER_ID, uu.getId());
                            resBody.setStatus(1);
                            resBody.setObj(uu);
                            resBody.setMsg("识别成功。欢迎" + uu.getUsername() + "进入系统");
                            return resBody;

                        }

                    }
                }
            }
        }
        return resBody;
    }


    /**
     * 分页查询用户
     *
     * @param pageIndex
     * @param pageSize
     * @param o
     * @return
     */
    @RequestMapping("/page")
    public MessUtil page(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, UserInfo o) {
        return userInfoService.page(pageIndex, pageSize, o);
    }

    /**
     * 修改密码
     */

    @RequestMapping("/updateUpass")
    public MessUtil updateUpass(String oldp, String newp, String newp2, UserInfo o) {
        MessUtil resBody = new MessUtil();
        UserInfo oo = userInfoService.getById(o.getId());
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

        userInfoService.saveOrUpdate(o);
        resBody.setStatus(1);
        resBody.setMsg("修改成功");
        return resBody;
    }

    /**
     * 上传头像
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/upimg")
    public MessUtil upimg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        MessUtil mess = new MessUtil();
        //上传之前先把之前的删了
        if (map.get("faceimg") != null && map.get("faceimg") != Sys.defaultFace) {
            UploadFile.deleteFile(request, map.get("faceimg"));
        }
        String img = UploadFile.upimg(file, request, Sys.Upimg.userinfo);

        map.put("faceimg", img);
        mess.setObj(img);
        mess.setStatus(1);
        mess.setMsg("头像上传成功");

        return mess;

    }


}
