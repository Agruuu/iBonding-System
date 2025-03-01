package com.ibonding.module.pay.convert.demo;

import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferCreateReqVO;
import com.ibonding.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferRespVO;
import com.ibonding.module.pay.dal.dataobject.demo.PayDemoTransferDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Agaru
 */
@Mapper
public interface PayDemoTransferConvert {

    PayDemoTransferConvert INSTANCE = Mappers.getMapper(PayDemoTransferConvert.class);

    PayDemoTransferDO convert(PayDemoTransferCreateReqVO bean);

    PageResult<PayDemoTransferRespVO> convertPage(PageResult<PayDemoTransferDO> pageResult);
}
