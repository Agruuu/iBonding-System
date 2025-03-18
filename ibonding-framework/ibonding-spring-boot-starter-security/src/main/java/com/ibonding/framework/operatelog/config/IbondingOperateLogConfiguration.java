package com.ibonding.framework.operatelog.config;

import com.ibonding.framework.operatelog.core.service.LogRecordServiceImpl;
import com.mzt.logapi.service.ILogRecordService;
import com.mzt.logapi.starter.annotation.EnableLogRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 操作日志配置类
 *
 * @author Agaru
 */
@EnableLogRecord(tenant = "") // 貌似用不上 tenant 这玩意给个空好啦
@AutoConfiguration
@Slf4j
public class IbondingOperateLogConfiguration {

    @Bean
    @Primary
    public ILogRecordService iLogRecordServiceImpl() {
        return new LogRecordServiceImpl();
    }

}
