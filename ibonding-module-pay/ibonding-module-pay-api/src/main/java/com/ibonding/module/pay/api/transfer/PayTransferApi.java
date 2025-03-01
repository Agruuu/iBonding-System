package com.ibonding.module.pay.api.transfer;

import com.ibonding.module.pay.api.transfer.dto.PayTransferCreateReqDTO;

import com.ibonding.module.pay.api.transfer.dto.PayTransferRespDTO;
import jakarta.validation.Valid;

/**
 * 转账单 API 接口
 *
 * @author Agaru
 */
public interface PayTransferApi {

    /**
     * 创建转账单
     *
     * @param reqDTO 创建请求
     * @return 转账单编号
     */
    Long createTransfer(@Valid PayTransferCreateReqDTO reqDTO);

    /**
     * 获得转账单
     *
     * @param id 转账单编号
     * @return 转账单
     */
    PayTransferRespDTO getTransfer(Long id);

}
