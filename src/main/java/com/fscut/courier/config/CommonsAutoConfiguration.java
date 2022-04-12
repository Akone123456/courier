package com.fscut.courier.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({TxProperties.class})
public class CommonsAutoConfiguration {
    @Bean
    public TxSmsTemplate txSmsTemplate(TxProperties txProperties) {
        return new TxSmsTemplate(txProperties);
    }
}
