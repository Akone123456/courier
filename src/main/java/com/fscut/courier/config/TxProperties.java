package com.fscut.courier.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "txsms")
public class TxProperties {

    // AppId  1400开头的
    private int AppId;
    // 短信应用SDK AppKey
    private String AppKey;
    // 短信模板ID
    private int TemplateId;
    // 签名
    private String signName;
}
