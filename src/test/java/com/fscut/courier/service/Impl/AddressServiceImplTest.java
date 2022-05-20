package com.fscut.courier.service.Impl;

import com.fscut.courier.dao.AddressDao;
import com.fscut.courier.dao.CodeDao;
import com.fscut.courier.dao.OrderDao;
import com.fscut.courier.model.po.Address;
import com.fscut.courier.model.po.Code;
import com.fscut.courier.model.po.Order;
import com.fscut.courier.service.CodeService;
import com.fscut.courier.utils.RedisUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.fscut.courier.utils.ConstValue.SMS_CODE_;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lxw
 */
@SpringBootTest
class AddressServiceImplTest {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private CodeDao codeDao;

    private final  static String phone = SMS_CODE_+"19951753185";
    private final  static int Num = 10;
    @Test
    public void  test01(){
        Long startTimeMysql = System.currentTimeMillis();
        for(int i=0;i<Num;i++){
            Code code = codeDao.selectById(1);
        }
        Long endTimeMysql = System.currentTimeMillis();

       Long startTimeRedis = System.currentTimeMillis();
       for(int i=0;i<Num;i++){
           String smscode_redis = redisUtils.get(phone);
         //  System.out.println(smscode_redis);
       }
       Long endTimeRedis = System.currentTimeMillis();
       /*--------------------------------------------------------*/


       System.out.println("查询Redis时间:"+(endTimeRedis-startTimeRedis));
       System.out.println("查询Mysql时间:"+(endTimeMysql-startTimeMysql));
    }

}