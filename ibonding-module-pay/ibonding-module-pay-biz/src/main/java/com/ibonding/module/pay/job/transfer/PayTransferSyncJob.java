package com.ibonding.module.pay.job.transfer;

import cn.hutool.core.util.StrUtil;
import com.ibonding.framework.quartz.core.handler.JobHandler;
import com.ibonding.module.pay.service.transfer.PayTransferService;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * 转账订单的同步 Job
 *
 * 由于转账订单的转账结果，有些渠道是异步通知进行同步的，考虑到异步通知可能会失败（小概率），所以需要定时进行同步。
 *
 * @author Agaru
 */
@Component
public class PayTransferSyncJob implements JobHandler {

    @Resource
    private PayTransferService transferService;

    @Override
    public String execute(String param) {
        int count = transferService.syncTransfer();
        return StrUtil.format("同步转账订单 {} 个", count);
    }
}
