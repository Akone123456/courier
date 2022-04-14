package com.fscut.courier.controller;

import com.fscut.courier.config.TxSmsTemplate;
import com.fscut.courier.utils.MessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信验证码公共control
 */
@RestController
@CrossOrigin
public class SendSmsController {

   public static Map<String,String> map =new HashMap<>();
    @Autowired
    private TxSmsTemplate txSmsTemplate;
    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @RequestMapping("/sendSms")
    public MessUtil sendSms(String phone){
        MessUtil messUtil = new MessUtil();
        try {

            //随机生成验证码并发送
            String code=(int)((Math.random()*9+1)*100000)+"";
           // txSmsTemplate.sendMesModel(phone, code);
            System.out.println("发送的验证码："+code);
            map.put(phone,code);
            messUtil.setStatus(1);
            messUtil.setMsg("发送成功");

        } catch (Exception e) {
            e.printStackTrace();
            messUtil.setStatus(0);
            messUtil.setMsg("发送异常");
        }
        return  messUtil;
    }
}
