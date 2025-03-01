package com.ibonding.module.infra.framework.web.config;

import com.ibonding.framework.swagger.config.IbondingSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * infra 模块的 web 组件的 Configuration
 *
 * @author Agaru
 */
@Configuration(proxyBeanMethods = false)
public class InfraWebConfiguration {

    /**
     * infra 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi infraGroupedOpenApi() {
        return IbondingSwaggerAutoConfiguration.buildGroupedOpenApi("infra");
    }

}
