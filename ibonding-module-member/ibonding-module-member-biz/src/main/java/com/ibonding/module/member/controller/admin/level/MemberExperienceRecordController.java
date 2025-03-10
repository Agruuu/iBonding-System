package com.ibonding.module.member.controller.admin.level;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.module.member.controller.admin.level.vo.experience.MemberExperienceRecordPageReqVO;
import com.ibonding.module.member.controller.admin.level.vo.experience.MemberExperienceRecordRespVO;
import com.ibonding.module.member.convert.level.MemberExperienceRecordConvert;
import com.ibonding.module.member.dal.dataobject.level.MemberExperienceRecordDO;
import com.ibonding.module.member.service.level.MemberExperienceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import static com.ibonding.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Membership Experience Records")
@RestController
@RequestMapping("/member/experience-record")
@Validated
public class MemberExperienceRecordController {

    @Resource
    private MemberExperienceRecordService experienceLogService;

    @GetMapping("/get")
    @Operation(summary = "Get Membership Experience Records")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:experience-record:query')")
    public CommonResult<MemberExperienceRecordRespVO> getExperienceRecord(@RequestParam("id") Long id) {
        MemberExperienceRecordDO experienceLog = experienceLogService.getExperienceRecord(id);
        return success(MemberExperienceRecordConvert.INSTANCE.convert(experienceLog));
    }

    @GetMapping("/page")
    @Operation(summary = "Get Membership Experience Records Pagination")
    @PreAuthorize("@ss.hasPermission('member:experience-record:query')")
    public CommonResult<PageResult<MemberExperienceRecordRespVO>> getExperienceRecordPage(
            @Valid MemberExperienceRecordPageReqVO pageVO) {
        PageResult<MemberExperienceRecordDO> pageResult = experienceLogService.getExperienceRecordPage(pageVO);
        return success(MemberExperienceRecordConvert.INSTANCE.convertPage(pageResult));
    }

}
