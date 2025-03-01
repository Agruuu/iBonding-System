package com.ibonding.module.pay.convert.demo;

import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.module.pay.controller.admin.demo.vo.order.PayDemoOrderCreateReqVO;
import com.ibonding.module.pay.controller.admin.demo.vo.order.PayDemoOrderRespVO;
import com.ibonding.module.pay.dal.dataobject.demo.PayDemoOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 示例订单 Convert
 *
 * @author Agaru
 */
@Mapper
public interface PayDemoOrderConvert {

    PayDemoOrderConvert INSTANCE = Mappers.getMapper(PayDemoOrderConvert.class);

    PayDemoOrderDO convert(PayDemoOrderCreateReqVO bean);

    PayDemoOrderRespVO convert(PayDemoOrderDO bean);

    PageResult<PayDemoOrderRespVO> convertPage(PageResult<PayDemoOrderDO> page);

}
