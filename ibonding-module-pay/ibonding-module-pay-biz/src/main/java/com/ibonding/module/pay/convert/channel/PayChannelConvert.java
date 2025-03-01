package com.ibonding.module.pay.convert.channel;

import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.module.pay.controller.admin.channel.vo.PayChannelCreateReqVO;
import com.ibonding.module.pay.controller.admin.channel.vo.PayChannelRespVO;
import com.ibonding.module.pay.controller.admin.channel.vo.PayChannelUpdateReqVO;
import com.ibonding.module.pay.dal.dataobject.channel.PayChannelDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayChannelConvert {

    PayChannelConvert INSTANCE = Mappers.getMapper(PayChannelConvert.class);

    @Mapping(target = "config",ignore = true)
    PayChannelDO convert(PayChannelCreateReqVO bean);

    @Mapping(target = "config",ignore = true)
    PayChannelDO convert(PayChannelUpdateReqVO bean);

    @Mapping(target = "config",expression = "java(com.ibonding.framework.common.util.json.JsonUtils.toJsonString(bean.getConfig()))")
    PayChannelRespVO convert(PayChannelDO bean);

    PageResult<PayChannelRespVO> convertPage(PageResult<PayChannelDO> page);

}
