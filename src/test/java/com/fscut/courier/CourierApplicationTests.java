package com.fscut.courier;

import com.fscut.courier.config.TxSmsTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CourierApplicationTests {
    @Autowired
    private TxSmsTemplate txSmsTemplate;

    /**
     * 腾讯云发送短信测试
     */
    @Test
    public void TxSmsTest(){
        // 参数1: 手机号(正文模板中的参数{1})
        // 参数2: 验证码(正文模板中的参数{2})
        String Msg = txSmsTemplate.sendMesModel("15336574540", "1285");
        // Msg不为null 发送成功
        // Msg为null  发送失败
        System.out.println(Msg);
    }

    @Test
    void contextLoads() {
    }

}
