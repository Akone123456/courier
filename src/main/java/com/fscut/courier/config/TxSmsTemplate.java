package com.fscut.courier.config;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;

import java.io.IOException;
@Slf4j
public class TxSmsTemplate {
    private TxProperties txProperties;

    public TxSmsTemplate(TxProperties txProperties) {
        this.txProperties = txProperties;
    }

    /**
     * 指定正文模板id发送短信
     * @param number 用户的手机号码
     * @return OK 成功  null 失败
     */
    public String sendMesModel(String number,String value) {
        try {
            // 接收生成的验证码，设置5分钟内填写
//            String[] params = new String[2];
//            params[0] = value;
//            params[1] = "5";
            String[] params = {value,"123","5"};

            // 构建短信发送器
            SmsSingleSender ssender = new SmsSingleSender(txProperties.getAppId(), txProperties.getAppKey());


            SmsSingleSenderResult result = ssender.sendWithParam("86", number,txProperties.getTemplateId(), params, txProperties.getSignName(), "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.print(result);

            return result.errMsg; //OK
        } catch (HTTPException e) {
            // HTTP响应码错误
            log.info("短信发送失败,HTTP响应码错误!");
            // e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            log.info("短信发送失败,json解析错误!");
            //e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            log.info("短信发送失败,网络IO错误!");
            // e.printStackTrace();
        }
        return null;
    }
}
