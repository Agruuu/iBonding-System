package com.ibonding.module.system.job;

import com.ibonding.framework.quartz.core.handler.JobHandler;
import com.ibonding.module.system.dal.dataobject.user.AdminUserDO;
import com.ibonding.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.List;

@Component
public class DemoJob implements JobHandler {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public String execute(String param) {
        List<AdminUserDO> users = adminUserMapper.selectList();
        return "用户数量：" + users.size();
    }

}
