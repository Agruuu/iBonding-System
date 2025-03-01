package com.ibonding.module.pay.api.notify.dto;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * 转账单的通知 Request DTO
 *
 * @author Agaru
 */
@Data
public class PayTransferNotifyReqDTO {

    // TODO Agaru：要不要改成 orderId 待定；
    /**
     * 商户转账单号
     */
    @NotEmpty(message = "商户转账单号不能为空")
    private String merchantTransferId;

    /**
     * 转账订单编号
     */
    @NotNull(message = "转账订单编号不能为空")
    private Long payTransferId;

}
