package com.ibonding.module.member.controller.app.signin;

import com.ibonding.framework.common.enums.CommonStatusEnum;
import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.module.member.controller.app.signin.vo.config.AppMemberSignInConfigRespVO;
import com.ibonding.module.member.convert.signin.MemberSignInConfigConvert;
import com.ibonding.module.member.dal.dataobject.signin.MemberSignInConfigDO;
import com.ibonding.module.member.service.signin.MemberSignInConfigService;
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

@Tag(name = "User App - Sign-in Config")
@RestController
@RequestMapping("/member/sign-in/config")
@Validated
public class AppMemberSignInConfigController {

    @Resource
    private MemberSignInConfigService signInConfigService;

    @GetMapping("/list")
    @Operation(summary = "Get Sign-in Config List")
    @PermitAll
    public CommonResult<List<AppMemberSignInConfigRespVO>> getSignInConfigList() {
        List<MemberSignInConfigDO> pageResult = signInConfigService.getSignInConfigList(CommonStatusEnum.ENABLE.getStatus());
        return success(MemberSignInConfigConvert.INSTANCE.convertList02(pageResult));
    }

}
