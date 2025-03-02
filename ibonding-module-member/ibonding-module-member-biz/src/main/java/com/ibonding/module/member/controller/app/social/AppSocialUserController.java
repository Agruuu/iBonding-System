package com.ibonding.module.member.controller.app.social;

import cn.hutool.core.codec.Base64;
import com.ibonding.framework.common.enums.UserTypeEnum;
import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.member.controller.app.social.vo.*;
import com.ibonding.module.system.api.social.SocialClientApi;
import com.ibonding.module.system.api.social.SocialUserApi;
import com.ibonding.module.system.api.social.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ibonding.framework.common.pojo.CommonResult.success;
import static com.ibonding.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "User App - Social Users")
@RestController
@RequestMapping("/member/social-user")
@Validated
public class AppSocialUserController {

    @Resource
    private SocialUserApi socialUserApi;
    @Resource
    private SocialClientApi socialClientApi;

    @PostMapping("/bind")
    @Operation(summary = "Social Binding, Using Code Authorization Code")
    @PermitAll
    public CommonResult<String> socialBind(@RequestBody @Valid AppSocialUserBindReqVO reqVO) {
        SocialUserBindReqDTO reqDTO = new SocialUserBindReqDTO(getLoginUserId(), UserTypeEnum.MEMBER.getValue(),
                reqVO.getType(), reqVO.getCode(), reqVO.getState());
        String openid = socialUserApi.bindSocialUser(reqDTO);
        return success(openid);
    }

    @DeleteMapping("/unbind")
    @Operation(summary = "Social Unbinding")
    public CommonResult<Boolean> socialUnbind(@RequestBody AppSocialUserUnbindReqVO reqVO) {
        SocialUserUnbindReqDTO reqDTO = new SocialUserUnbindReqDTO(getLoginUserId(), UserTypeEnum.MEMBER.getValue(),
                reqVO.getType(), reqVO.getOpenid());
        socialUserApi.unbindSocialUser(reqDTO);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get Social Users")
    @Parameter(name = "type", description = "社交平台的类型，参见 SocialTypeEnum 枚举值", required = true, example = "10")
    public CommonResult<AppSocialUserRespVO> getSocialUser(@RequestParam("type") Integer type) {
        SocialUserRespDTO socialUser = socialUserApi.getSocialUserByUserId(UserTypeEnum.MEMBER.getValue(), getLoginUserId(), type);
        return success(BeanUtils.toBean(socialUser, AppSocialUserRespVO.class));
    }

    @PostMapping("/wxa-qrcode")
    @Operation(summary = "Get WeChat Mini-program Code(base64 image)")
    @PermitAll
    public CommonResult<String> getWxaQrcode(@RequestBody @Valid AppSocialWxaQrcodeReqVO reqVO) {
        byte[] wxQrcode = socialClientApi.getWxaQrcode(BeanUtils.toBean(reqVO, SocialWxQrcodeReqDTO.class));
        return success(Base64.encode(wxQrcode));
    }

    @GetMapping("/get-subscribe-template-list")
    @Operation(summary = "Get WeChat Mini-program Subscription Template List")
    @PermitAll
    public CommonResult<List<AppSocialWxaSubscribeTemplateRespVO>> getSubscribeTemplateList() {
        List<SocialWxaSubscribeTemplateRespDTO> template = socialClientApi.getWxaSubscribeTemplateList(UserTypeEnum.MEMBER.getValue());
        return success(BeanUtils.toBean(template, AppSocialWxaSubscribeTemplateRespVO.class));
    }

}
