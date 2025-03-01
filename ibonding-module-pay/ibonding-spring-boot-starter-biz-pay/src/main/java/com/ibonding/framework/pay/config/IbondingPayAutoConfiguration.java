package com.ibonding.framework.pay.config;

import com.ibonding.framework.pay.core.client.PayClientFactory;
import com.ibonding.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 支付配置类
 *
 * @author Agaru
 */
@AutoConfiguration
public class IbondingPayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
