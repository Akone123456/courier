package com.fscut.courier.controller;

import com.fscut.courier.dao.CodeDao;
import com.fscut.courier.model.po.Code;
import com.fscut.courier.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fscut.courier.utils.ConstValue.SMS_CODE_;

/**
 * @author lxw
 */
@RestController
@RequestMapping("test")
public class TestController {
    private final  static String phone = SMS_CODE_+"19951753185";
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private CodeDao codeDao;

    @GetMapping("redis")
    public  void testRedis(){
        String s = redisUtils.get(phone);
        System.out.println(s);
    }
    @GetMapping("mysql")
    public  void testMysql(){
        Code code = codeDao.selectById(1);
        System.out.println(code);
    }

}
