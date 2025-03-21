package com.ibonding.module.member.controller.admin.signin;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.module.member.controller.admin.signin.vo.config.MemberSignInConfigCreateReqVO;
import com.ibonding.module.member.controller.admin.signin.vo.config.MemberSignInConfigRespVO;
import com.ibonding.module.member.controller.admin.signin.vo.config.MemberSignInConfigUpdateReqVO;
import com.ibonding.module.member.convert.signin.MemberSignInConfigConvert;
import com.ibonding.module.member.dal.dataobject.signin.MemberSignInConfigDO;
import com.ibonding.module.member.service.signin.MemberSignInConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

import static com.ibonding.framework.common.pojo.CommonResult.success;

// TODO Agaru：url
@Tag(name = "Management Backend - Sign-in Config")
@RestController
@RequestMapping("/member/sign-in/config")
@Validated
public class MemberSignInConfigController {

    @Resource
    private MemberSignInConfigService signInConfigService;

    @PostMapping("/create")
    @Operation(summary = "Create Sign-in Config")
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:create')")
    public CommonResult<Long> createSignInConfig(@Valid @RequestBody MemberSignInConfigCreateReqVO createReqVO) {
        return success(signInConfigService.createSignInConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update Sign-in Config")
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:update')")
    public CommonResult<Boolean> updateSignInConfig(@Valid @RequestBody MemberSignInConfigUpdateReqVO updateReqVO) {
        signInConfigService.updateSignInConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete Sign-in Config")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:delete')")
    public CommonResult<Boolean> deleteSignInConfig(@RequestParam("id") Long id) {
        signInConfigService.deleteSignInConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get Sign-in Config")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:query')")
    public CommonResult<MemberSignInConfigRespVO> getSignInConfig(@RequestParam("id") Long id) {
        MemberSignInConfigDO signInConfig = signInConfigService.getSignInConfig(id);
        return success(MemberSignInConfigConvert.INSTANCE.convert(signInConfig));
    }

    @GetMapping("/list")
    @Operation(summary = "Get Sign-in Config List")
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:query')")
    public CommonResult<List<MemberSignInConfigRespVO>> getSignInConfigList() {
        List<MemberSignInConfigDO> list = signInConfigService.getSignInConfigList();
        return success(MemberSignInConfigConvert.INSTANCE.convertList(list));
    }

}
