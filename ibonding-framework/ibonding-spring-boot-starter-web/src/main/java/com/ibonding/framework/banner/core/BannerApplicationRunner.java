package com.ibonding.framework.banner.core;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.util.ClassUtils;

import java.util.concurrent.TimeUnit;

/**
 * 项目启动成功后，提供文档相关的地址
 *
 * @author Agaru
 */
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        ThreadUtil.execute(() -> {
            ThreadUtil.sleep(1, TimeUnit.SECONDS); // 延迟 1 秒，保证输出到结尾
            log.info("\n----------------------------------------------------------\n\t" +
                            "The project has been successfully launched.！\n\t" +
                            "API document: \t{} \n\t" +
                            "----------------------------------------------------------",
                    "http://127.0.0.1:48080/doc.html/");

            // 数据报表
            if (isNotPresent("com.ibonding.module.report.framework.security.config.SecurityConfiguration")) {
                System.out.println("[Report Module ibonding-module-report - disabled]");
            }
            // 支付平台
            if (isNotPresent("com.ibonding.module.pay.framework.pay.config.PayConfiguration")) {
                System.out.println("[Payment System ibonding-module-pay - disabled]");
            }
            // AI 大模型
            if (isNotPresent("com.ibonding.module.ai.framework.web.config.AiWebConfiguration")) {
                System.out.println("[AI Model ibonding-module-ai - disabled]");
            }
        });
    }

    private static boolean isNotPresent(String className) {
        return !ClassUtils.isPresent(className, ClassUtils.getDefaultClassLoader());
    }

}
