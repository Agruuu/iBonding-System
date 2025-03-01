package com.ibonding.module.infra.dal.mysql.db;

import com.ibonding.framework.mybatis.core.mapper.BaseMapperX;
import com.ibonding.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author Agaru
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
