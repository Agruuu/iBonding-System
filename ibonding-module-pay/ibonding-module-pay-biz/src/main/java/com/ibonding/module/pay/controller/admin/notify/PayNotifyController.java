package com.ibonding.module.pay.controller.admin.notify;

import cn.hutool.core.collection.CollUtil;
import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.framework.pay.core.client.PayClient;
import com.ibonding.framework.pay.core.client.dto.order.PayOrderRespDTO;
import com.ibonding.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import com.ibonding.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import com.ibonding.module.pay.controller.admin.notify.vo.PayNotifyTaskDetailRespVO;
import com.ibonding.module.pay.controller.admin.notify.vo.PayNotifyTaskPageReqVO;
import com.ibonding.module.pay.controller.admin.notify.vo.PayNotifyTaskRespVO;
import com.ibonding.module.pay.convert.notify.PayNotifyTaskConvert;
import com.ibonding.module.pay.dal.dataobject.app.PayAppDO;
import com.ibonding.module.pay.dal.dataobject.notify.PayNotifyLogDO;
import com.ibonding.module.pay.dal.dataobject.notify.PayNotifyTaskDO;
import com.ibonding.module.pay.service.app.PayAppService;
import com.ibonding.module.pay.service.channel.PayChannelService;
import com.ibonding.module.pay.service.notify.PayNotifyService;
import com.ibonding.module.pay.service.order.PayOrderService;
import com.ibonding.module.pay.service.refund.PayRefundService;
import com.ibonding.module.pay.service.transfer.PayTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.ibonding.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ibonding.framework.common.pojo.CommonResult.success;
import static com.ibonding.framework.common.util.collection.CollectionUtils.convertList;
import static com.ibonding.module.pay.enums.ErrorCodeConstants.CHANNEL_NOT_FOUND;

@Tag(name = "Management Backend - Payment Callback Notify")
@RestController
@RequestMapping("/pay/notify")
@Validated
@Slf4j
public class PayNotifyController {

    @Resource
    private PayOrderService orderService;
    @Resource
    private PayRefundService refundService;
    @Resource
    private PayTransferService payTransferService;
    @Resource
    private PayNotifyService notifyService;
    @Resource
    private PayAppService appService;
    @Resource
    private PayChannelService channelService;

    @PostMapping(value = "/order/{channelId}")
    @Operation(summary = "Unified [Payment] callback for payment channel")
    @PermitAll
    public String notifyOrder(@PathVariable("channelId") Long channelId,
                              @RequestParam(required = false) Map<String, String> params,
                              @RequestBody(required = false) String body) {
        log.info("[notifyOrder][channelId({}) 回调数据({}/{})]", channelId, params, body);
        // 1. 校验支付渠道是否存在
        PayClient payClient = channelService.getPayClient(channelId);
        if (payClient == null) {
            log.error("[notifyOrder][渠道编号({}) 找不到对应的支付客户端]", channelId);
            throw exception(CHANNEL_NOT_FOUND);
        }

        // 2. 解析通知数据
        PayOrderRespDTO notify = payClient.parseOrderNotify(params, body);
        orderService.notifyOrder(channelId, notify);
        return "success";
    }

    @PostMapping(value = "/refund/{channelId}")
    @Operation(summary = "Unified [Refund] callback for payment channel")
    @PermitAll
    public String notifyRefund(@PathVariable("channelId") Long channelId,
                               @RequestParam(required = false) Map<String, String> params,
                               @RequestBody(required = false) String body) {
        log.info("[notifyRefund][channelId({}) 回调数据({}/{})]", channelId, params, body);
        // 1. 校验支付渠道是否存在
        PayClient payClient = channelService.getPayClient(channelId);
        if (payClient == null) {
            log.error("[notifyRefund][渠道编号({}) 找不到对应的支付客户端]", channelId);
            throw exception(CHANNEL_NOT_FOUND);
        }

        // 2. 解析通知数据
        PayRefundRespDTO notify = payClient.parseRefundNotify(params, body);
        refundService.notifyRefund(channelId, notify);
        return "success";
    }

    @PostMapping(value = "/transfer/{channelId}")
    @Operation(summary = "Unified [Transfer] callback for payment channel")
    @PermitAll
    public String notifyTransfer(@PathVariable("channelId") Long channelId,
                                 @RequestParam(required = false) Map<String, String> params,
                                 @RequestBody(required = false) String body) {
        log.info("[notifyTransfer][channelId({}) 回调数据({}/{})]", channelId, params, body);
        // 1. 校验支付渠道是否存在
        PayClient payClient = channelService.getPayClient(channelId);
        if (payClient == null) {
            log.error("[notifyTransfer][渠道编号({}) 找不到对应的支付客户端]", channelId);
            throw exception(CHANNEL_NOT_FOUND);
        }

        // 2. 解析通知数据
        PayTransferRespDTO notify = payClient.parseTransferNotify(params, body);
        payTransferService.notifyTransfer(channelId, notify);
        return "success";
    }

    @GetMapping("/get-detail")
    @Operation(summary = "Get Callback Notification Detail")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pay:notify:query')")
    public CommonResult<PayNotifyTaskDetailRespVO> getNotifyTaskDetail(@RequestParam("id") Long id) {
        PayNotifyTaskDO task = notifyService.getNotifyTask(id);
        if (task == null) {
            return success(null);
        }
        // 拼接返回
        PayAppDO app = appService.getApp(task.getAppId());
        List<PayNotifyLogDO> logs = notifyService.getNotifyLogList(id);
        return success(PayNotifyTaskConvert.INSTANCE.convert(task, app, logs));
    }

    @GetMapping("/page")
    @Operation(summary = "Get Callback Notify Pagination")
    @PreAuthorize("@ss.hasPermission('pay:notify:query')")
    public CommonResult<PageResult<PayNotifyTaskRespVO>> getNotifyTaskPage(@Valid PayNotifyTaskPageReqVO pageVO) {
        PageResult<PayNotifyTaskDO> pageResult = notifyService.getNotifyTaskPage(pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty());
        }
        // 拼接返回
        Map<Long, PayAppDO> appMap = appService.getAppMap(convertList(pageResult.getList(), PayNotifyTaskDO::getAppId));
        return success(PayNotifyTaskConvert.INSTANCE.convertPage(pageResult, appMap));
    }

}
