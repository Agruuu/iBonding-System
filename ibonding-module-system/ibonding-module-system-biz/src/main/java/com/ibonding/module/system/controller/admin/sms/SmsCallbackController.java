package com.ibonding.module.system.controller.admin.sms;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.util.servlet.ServletUtils;
import com.ibonding.module.system.framework.sms.core.enums.SmsChannelEnum;
import com.ibonding.module.system.service.sms.SmsSendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;

import static com.ibonding.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - SMS Callback")
@RestController
@RequestMapping("/system/sms/callback")
public class SmsCallbackController {

    @Resource
    private SmsSendService smsSendService;

    @PostMapping("/aliyun")
    @PermitAll
    @Operation(summary = "Callback for Alibaba Cloud SMS", description = "Refer to the documentation at https://help.aliyun.com/document_detail/120998.html")
    public CommonResult<Boolean> receiveAliyunSmsStatus(HttpServletRequest request) throws Throwable {
        String text = ServletUtils.getBody(request);
        smsSendService.receiveSmsStatus(SmsChannelEnum.ALIYUN.getCode(), text);
        return success(true);
    }

    @PostMapping("/tencent")
    @PermitAll
    @Operation(summary = "Callback for Tencent Cloud SMS", description = "Refer to the documentation at https://cloud.tencent.com/document/product/382/52077")
    public CommonResult<Boolean> receiveTencentSmsStatus(HttpServletRequest request) throws Throwable {
        String text = ServletUtils.getBody(request);
        smsSendService.receiveSmsStatus(SmsChannelEnum.TENCENT.getCode(), text);
        return success(true);
    }


    @PostMapping("/huawei")
    @PermitAll
    @Operation(summary = "Callback for Huawei Cloud SMS", description = "Refer to the documentation at https://support.huaweicloud.com/api-msgsms/sms_05_0003.html")
    public CommonResult<Boolean> receiveHuaweiSmsStatus(@RequestBody String requestBody) throws Throwable {
        smsSendService.receiveSmsStatus(SmsChannelEnum.HUAWEI.getCode(), requestBody);
        return success(true);
    }

    @PostMapping("/qiniu")
    @PermitAll
    @Operation(summary = "Callback for Qiniu Cloud SMS", description = "Refer to the documentation at https://developer.qiniu.com/sms/5910/message-push")
    public CommonResult<Boolean> receiveQiniuSmsStatus(@RequestBody String requestBody) throws Throwable {
        smsSendService.receiveSmsStatus(SmsChannelEnum.QINIU.getCode(), requestBody);
        return success(true);
    }

}