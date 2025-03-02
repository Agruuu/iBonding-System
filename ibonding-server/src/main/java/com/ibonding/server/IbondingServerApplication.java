package com.ibonding.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Project's Startup Class
 *
 * @author Agaru
 */
@SuppressWarnings("SpringComponentScan") // Ignore IDEA's Inability to Recognize ${ibonding.info.base - package}
@SpringBootApplication(scanBasePackages = {"${ibonding.info.base-package}.server", "${ibonding.info.base-package}.module"})
public class IbondingServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IbondingServerApplication.class, args);
//        new SpringApplicationBuilder(IbondingServerApplication.class)
//                .applicationStartup(new BufferingApplicationStartup(20480))
//                .run(args);
    }

}
