package com.ibonding.module.pay.framework.web.config;

import com.ibonding.framework.swagger.config.IbondingSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * pay 模块的 web 组件的 Configuration
 *
 * @author Agaru
 */
@Configuration(proxyBeanMethods = false)
public class PayWebConfiguration {

    /**
     * pay 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi payGroupedOpenApi() {
        return IbondingSwaggerAutoConfiguration.buildGroupedOpenApi("pay");
    }

}
