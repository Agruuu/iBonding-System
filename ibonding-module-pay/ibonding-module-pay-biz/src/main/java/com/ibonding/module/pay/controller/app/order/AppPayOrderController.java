package com.ibonding.module.pay.controller.app.order;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.framework.pay.core.enums.channel.PayChannelEnum;
import com.ibonding.module.pay.controller.admin.order.vo.PayOrderRespVO;
import com.ibonding.module.pay.controller.admin.order.vo.PayOrderSubmitRespVO;
import com.ibonding.module.pay.controller.app.order.vo.AppPayOrderSubmitReqVO;
import com.ibonding.module.pay.controller.app.order.vo.AppPayOrderSubmitRespVO;
import com.ibonding.module.pay.convert.order.PayOrderConvert;
import com.ibonding.module.pay.dal.dataobject.order.PayOrderDO;
import com.ibonding.module.pay.enums.order.PayOrderStatusEnum;
import com.ibonding.module.pay.framework.pay.core.WalletPayClient;
import com.ibonding.module.pay.service.order.PayOrderService;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

import static com.ibonding.framework.common.pojo.CommonResult.success;
import static com.ibonding.framework.common.util.servlet.ServletUtils.getClientIP;
import static com.ibonding.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static com.ibonding.framework.web.core.util.WebFrameworkUtils.getLoginUserType;

@Tag(name = "User APP - Payment Order")
@RestController
@RequestMapping("/pay/order")
@Validated
@Slf4j
public class AppPayOrderController {

    @Resource
    private PayOrderService payOrderService;

    @GetMapping("/get")
    @Operation(summary = "Get Payment Order")
    @Parameters({
            @Parameter(name = "id", description = "编号", required = true, example = "1024"),
            @Parameter(name = "sync", description = "是否同步", example = "true")
    })
    public CommonResult<PayOrderRespVO> getOrder(@RequestParam("id") Long id,
                                                 @RequestParam(value = "sync", required = false) Boolean sync) {
        PayOrderDO order = payOrderService.getOrder(id);
        // sync 仅在等待支付
        if (Boolean.TRUE.equals(sync) && PayOrderStatusEnum.isWaiting(order.getStatus())) {
            payOrderService.syncOrderQuietly(order.getId());
            // 重新查询，因为同步后，可能会有变化
            order = payOrderService.getOrder(id);
        }
        return success(BeanUtils.toBean(order, PayOrderRespVO.class));
    }

    @PostMapping("/submit")
    @Operation(summary = "Submit Payment Order")
    public CommonResult<AppPayOrderSubmitRespVO> submitPayOrder(@RequestBody AppPayOrderSubmitReqVO reqVO) {
        // 1. 钱包支付事，需要额外传 user_id 和 user_type
        if (Objects.equals(reqVO.getChannelCode(), PayChannelEnum.WALLET.getCode())) {
            Map<String, String> channelExtras = reqVO.getChannelExtras() == null ?
                    Maps.newHashMapWithExpectedSize(2) : reqVO.getChannelExtras();
            channelExtras.put(WalletPayClient.USER_ID_KEY, String.valueOf(getLoginUserId()));
            channelExtras.put(WalletPayClient.USER_TYPE_KEY, String.valueOf(getLoginUserType()));
            reqVO.setChannelExtras(channelExtras);
        }

        // 2. 提交支付
        PayOrderSubmitRespVO respVO = payOrderService.submitOrder(reqVO, getClientIP());
        return success(PayOrderConvert.INSTANCE.convert3(respVO));
    }

}
