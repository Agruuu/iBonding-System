package com.ibonding.module.system.service.logger;

import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.module.system.api.logger.dto.OperateLogCreateReqDTO;
import com.ibonding.module.system.api.logger.dto.OperateLogPageReqDTO;
import com.ibonding.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import com.ibonding.module.system.dal.dataobject.logger.OperateLogDO;

/**
 * 操作日志 Service 接口
 *
 * @author Agaru
 */
public interface OperateLogService {

    /**
     * 记录操作日志
     *
     * @param createReqDTO 创建请求
     */
    void createOperateLog(OperateLogCreateReqDTO createReqDTO);

    /**
     * 获得操作日志分页列表
     *
     * @param pageReqVO 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqVO pageReqVO);

    /**
     * 获得操作日志分页列表
     *
     * @param pageReqVO 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqDTO pageReqVO);

}
