package com.ibonding.module.member.controller.admin.config;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.module.member.controller.admin.config.vo.MemberConfigRespVO;
import com.ibonding.module.member.controller.admin.config.vo.MemberConfigSaveReqVO;
import com.ibonding.module.member.convert.config.MemberConfigConvert;
import com.ibonding.module.member.dal.dataobject.config.MemberConfigDO;
import com.ibonding.module.member.service.config.MemberConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import static com.ibonding.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Membership Settings")
@RestController
@RequestMapping("/member/config")
@Validated
public class MemberConfigController {

    @Resource
    private MemberConfigService memberConfigService;

    @PutMapping("/save")
    @Operation(summary = "Save Membership Config")
    @PreAuthorize("@ss.hasPermission('member:config:save')")
    public CommonResult<Boolean> saveConfig(@Valid @RequestBody MemberConfigSaveReqVO saveReqVO) {
        memberConfigService.saveConfig(saveReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get Membership Config")
    @PreAuthorize("@ss.hasPermission('member:config:query')")
    public CommonResult<MemberConfigRespVO> getConfig() {
        MemberConfigDO config = memberConfigService.getConfig();
        return success(MemberConfigConvert.INSTANCE.convert(config));
    }

}
