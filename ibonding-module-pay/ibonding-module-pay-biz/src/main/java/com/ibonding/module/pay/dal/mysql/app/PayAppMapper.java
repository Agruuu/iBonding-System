package com.ibonding.module.pay.dal.mysql.app;

import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.framework.mybatis.core.mapper.BaseMapperX;
import com.ibonding.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ibonding.module.pay.controller.admin.app.vo.PayAppPageReqVO;
import com.ibonding.module.pay.dal.dataobject.app.PayAppDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayAppMapper extends BaseMapperX<PayAppDO> {

    default PageResult<PayAppDO> selectPage(PayAppPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayAppDO>()
                .likeIfPresent(PayAppDO::getName, reqVO.getName())
                .likeIfPresent(PayAppDO::getAppKey, reqVO.getAppKey())
                .eqIfPresent(PayAppDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PayAppDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayAppDO::getId));
    }

    default PayAppDO selectByAppKey(String appKey) {
        return selectOne(PayAppDO::getAppKey, appKey);
    }

}
