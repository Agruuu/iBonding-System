package com.ibonding.module.member.controller.app.level;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.module.member.controller.app.level.vo.level.AppMemberLevelRespVO;
import com.ibonding.module.member.convert.level.MemberLevelConvert;
import com.ibonding.module.member.dal.dataobject.level.MemberLevelDO;
import com.ibonding.module.member.service.level.MemberLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

import static com.ibonding.framework.common.pojo.CommonResult.success;

@Tag(name = "User App - Membership Level")
@RestController
@RequestMapping("/member/level")
@Validated
public class AppMemberLevelController {

    @Resource
    private MemberLevelService levelService;

    @GetMapping("/list")
    @Operation(summary = "Get Membership Level List")
    @PermitAll
    public CommonResult<List<AppMemberLevelRespVO>> getLevelList() {
        List<MemberLevelDO> result = levelService.getEnableLevelList();
        return success(MemberLevelConvert.INSTANCE.convertList02(result));
    }

}
