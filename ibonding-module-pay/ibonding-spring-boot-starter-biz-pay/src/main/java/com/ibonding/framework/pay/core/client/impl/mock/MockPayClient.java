package com.ibonding.framework.pay.core.client.impl.mock;

import com.ibonding.framework.pay.core.client.dto.order.PayOrderRespDTO;
import com.ibonding.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.ibonding.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import com.ibonding.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import com.ibonding.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import com.ibonding.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import com.ibonding.framework.pay.core.client.impl.AbstractPayClient;
import com.ibonding.framework.pay.core.client.impl.NonePayClientConfig;
import com.ibonding.framework.pay.core.enums.channel.PayChannelEnum;
import com.ibonding.framework.pay.core.enums.transfer.PayTransferTypeEnum;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 模拟支付的 PayClient 实现类
 *
 * 模拟支付返回结果都是成功，方便大家日常流畅
 *
 * @author Agaru
 */
public class MockPayClient extends AbstractPayClient<NonePayClientConfig> {

    private static final String MOCK_RESP_SUCCESS_DATA = "MOCK_SUCCESS";

    public MockPayClient(Long channelId, NonePayClientConfig config) {
        super(channelId, PayChannelEnum.MOCK.getCode(), config);
    }

    @Override
    protected void doInit() {
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) {
        return PayOrderRespDTO.successOf("MOCK-P-" + reqDTO.getOutTradeNo(), "", LocalDateTime.now(),
                reqDTO.getOutTradeNo(), MOCK_RESP_SUCCESS_DATA);
    }

    @Override
    protected PayOrderRespDTO doGetOrder(String outTradeNo) {
        return PayOrderRespDTO.successOf("MOCK-P-" + outTradeNo, "", LocalDateTime.now(),
                outTradeNo, MOCK_RESP_SUCCESS_DATA);
    }

    @Override
    protected PayRefundRespDTO doUnifiedRefund(PayRefundUnifiedReqDTO reqDTO) {
        return PayRefundRespDTO.successOf("MOCK-R-" + reqDTO.getOutRefundNo(), LocalDateTime.now(),
                reqDTO.getOutRefundNo(), MOCK_RESP_SUCCESS_DATA);
    }

    @Override
    protected PayRefundRespDTO doGetRefund(String outTradeNo, String outRefundNo) {
        return PayRefundRespDTO.successOf("MOCK-R-" + outRefundNo, LocalDateTime.now(),
                outRefundNo, MOCK_RESP_SUCCESS_DATA);
    }

    @Override
    protected PayTransferRespDTO doParseTransferNotify(Map<String, String> params, String body) throws Throwable {
        throw new UnsupportedOperationException("未实现");
    }

    @Override
    protected PayRefundRespDTO doParseRefundNotify(Map<String, String> params, String body) {
        throw new UnsupportedOperationException("模拟支付无退款回调");
    }

    @Override
    protected PayOrderRespDTO doParseOrderNotify(Map<String, String> params, String body) {
        throw new UnsupportedOperationException("模拟支付无支付回调");
    }

    @Override
    protected PayTransferRespDTO doUnifiedTransfer(PayTransferUnifiedReqDTO reqDTO) {
        throw new UnsupportedOperationException("待实现");
    }

    @Override
    protected PayTransferRespDTO doGetTransfer(String outTradeNo, PayTransferTypeEnum type) {
        throw new UnsupportedOperationException("待实现");
    }

}
