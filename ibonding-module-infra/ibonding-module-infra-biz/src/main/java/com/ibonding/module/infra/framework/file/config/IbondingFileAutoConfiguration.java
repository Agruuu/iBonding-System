package com.ibonding.module.infra.framework.file.config;

import com.ibonding.module.infra.framework.file.core.client.FileClientFactory;
import com.ibonding.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author Agaru
 */
@Configuration(proxyBeanMethods = false)
public class IbondingFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
