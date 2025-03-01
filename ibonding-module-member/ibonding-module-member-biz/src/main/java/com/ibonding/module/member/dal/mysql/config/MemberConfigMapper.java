package com.ibonding.module.member.dal.mysql.config;

import com.ibonding.framework.mybatis.core.mapper.BaseMapperX;
import com.ibonding.module.member.dal.dataobject.config.MemberConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分设置 Mapper
 *
 * @author QingX
 */
@Mapper
public interface MemberConfigMapper extends BaseMapperX<MemberConfigDO> {
}
