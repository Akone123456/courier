package com.fscut.courier.control;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fscut.courier.bean.Sender;
import com.fscut.courier.service.SenderService;
import com.fscut.courier.utils.MessUtil;
import com.sun.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 管理端-配送员control控制层
 */
@RestController
@RequestMapping("/admin/sender")
@CrossOrigin
public class SenderControl extends SendSmsControl {


    @Autowired
    SenderService senderService;



    /**
     * 分页查询配送员
     * @param pageIndex
     * @param pageSize
     * @param o
     * @return
     */
    @RequestMapping("/page")
    public MessUtil page(@RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, Sender o){
        return senderService.page(pageIndex, pageSize,o);
    }

    /**
     * 账号密码登录
     * @param o
     * @return
     */
    @PostMapping("/loginByPassword")
    public MessUtil loginByPassword(Sender o) {
        MessUtil messUtil = new MessUtil();
        Sender employ = null;
        try {
            employ = senderService.getOne(new QueryWrapper<>(o));
            if (employ == null){
                messUtil.setStatus(0);
                messUtil.setMsg("账号密码错误，请重新登录");
            }else {
                employ.setLoginType("sender");
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
     * @param phone
     * @param smscode
     * @return
     */
    @RequestMapping("/loginByCode")
    public MessUtil loginByCode(String phone ,String smscode){
        MessUtil messUtil = new MessUtil();
        //判断有没有这个用户
        try {
            Sender s= new Sender() ;
            s.setPhone(phone);
            Sender one = senderService.getOne(new QueryWrapper<>(s));
            if(one == null){
                messUtil.setStatus(0);
                messUtil.setMsg("管理员没添加此手机号为配送员");
            }else{
                //判断验证码是否正确
               if(map.get(phone)!=null &&map.get(phone).equals(smscode)) {
                   one.setLoginType("sender");
                   messUtil.setStatus(1);
                   messUtil.setObj(one);
                   messUtil.setMsg("登录成功");
               }else{
                   messUtil.setStatus(0);
                   messUtil.setMsg("验证码输入错误");
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
            messUtil.setStatus(0);
            messUtil.setMsg("系统错误");
        }
        return  messUtil;
    }







    /*
     * 编号是否唯一
     * */

    @RequestMapping("/existesUno")
    public MessUtil existesUno(Sender o){
        MessUtil m=new MessUtil();
        if(o.getUsername()==null||o.getUsername().trim().length()==0){
            m.setStatus(0);
            m.setMsg("请输入编号");
            return m;
        }
        Sender cc= new Sender();
        cc.setUsername(o.getUsername());
        Sender lu=senderService.getOne(new QueryWrapper<>(cc));
        if(o.getId()!=null){
            if(lu!=null&&!lu.getId().equals(o.getId())){
                m.setStatus(0);
                m.setMsg("编号已存在");
                return m;
            }
        }else{
            if(lu!=null){
                m.setStatus(0);
                m.setMsg("编号已存在");
                return m;
            }
        }
        m.setStatus(1);
        m.setMsg("编号可用");
        return m;

    }

    /**
     * 管理员端-添加配送员信息
     * @param o
     * @return
     */
    @PostMapping("/add")
    public MessUtil addUser(Sender o) {
        MessUtil MessUtil = new MessUtil();

        try {
            if(o.getId() == null){
                o.setCreateTime(DateUtils.DateTimeToString(new Date()));
                o.setPassword("123456");//默认密码
                o.setStatus(1);//启用状态
            }
            senderService.saveOrUpdate(o);
            MessUtil.setStatus(1);
            MessUtil.setMsg("操作成功");
        } catch (Exception e) {
            MessUtil.setStatus(0);
            MessUtil.setMsg("操作失败");
            e.printStackTrace();
        }
        return MessUtil;
    }

    @RequestMapping("/delete")
    public MessUtil deleteEmploy (Sender o){
        MessUtil MessUtil =  new MessUtil();
        try {
            senderService.removeById(o.getId());
            MessUtil.setStatus(1);
            MessUtil.setMsg("删除成功");
        } catch (Exception e) {
            MessUtil.setStatus(0);
            MessUtil.setMsg("删除失败");
            e.printStackTrace();
        }
        return MessUtil;
    }
  

    //修改配送员信息-主要是禁用启用
    @RequestMapping("/update")
    public MessUtil update(Sender o){

        MessUtil messUtil =new MessUtil();
        if(o.getId() != null){
            boolean b = senderService.saveOrUpdate(o);
            if (b == true){
                messUtil.setStatus(1);
                messUtil.setMsg("修改成功");
            }
        }
        return  messUtil;
    }
    @RequestMapping("/initUpass")
    public MessUtil initUpass(Integer id){
        MessUtil messUtil =new MessUtil();
        try {
            if(id != null){
                Sender byId = senderService.getById(id);
                byId.setPassword("123456");
                senderService.saveOrUpdate(byId);
                messUtil.setStatus(1);
                messUtil.setMsg("操作成功");

            }
        } catch (Exception e) {
            e.printStackTrace();
            messUtil.setStatus(0);
            messUtil.setMsg("操作失败");

        }
        return  messUtil;
    }

    /**
     * 修改密码
     */

    @RequestMapping("/updateUpass")
    public MessUtil updateUpass(String oldp, String newp, String newp2, Sender o){
        MessUtil resBody=new MessUtil();
        Sender oo=senderService.getById(o.getId());
        if(!oo.getPassword().equals(oldp)){
            resBody.setStatus(0);
            resBody.setMsg("原密码错误");
            return resBody;
        }
        if(!newp.equals(newp2)){
            resBody.setStatus(0);
            resBody.setMsg("密码不一致");
            return resBody;
        }
        o.setPassword(newp);

        senderService.saveOrUpdate(o);
        resBody.setStatus(1);
        resBody.setMsg("修改成功");
        return resBody;
    }


}
