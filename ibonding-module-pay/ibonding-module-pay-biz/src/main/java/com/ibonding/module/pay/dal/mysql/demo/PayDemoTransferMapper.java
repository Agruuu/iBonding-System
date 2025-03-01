package com.ibonding.module.pay.dal.mysql.demo;

import com.ibonding.framework.common.pojo.PageParam;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.framework.mybatis.core.mapper.BaseMapperX;
import com.ibonding.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ibonding.module.pay.dal.dataobject.demo.PayDemoTransferDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayDemoTransferMapper extends BaseMapperX<PayDemoTransferDO> {

    default  PageResult<PayDemoTransferDO> selectPage(PageParam pageParam){
        return selectPage(pageParam, new LambdaQueryWrapperX<PayDemoTransferDO>()
                .orderByDesc(PayDemoTransferDO::getId));
    }
}