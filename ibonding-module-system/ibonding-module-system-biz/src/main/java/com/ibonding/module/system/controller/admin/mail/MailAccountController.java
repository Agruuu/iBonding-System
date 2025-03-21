package com.ibonding.module.system.controller.admin.mail;


import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.system.controller.admin.mail.vo.account.MailAccountPageReqVO;
import com.ibonding.module.system.controller.admin.mail.vo.account.MailAccountRespVO;
import com.ibonding.module.system.controller.admin.mail.vo.account.MailAccountSaveReqVO;
import com.ibonding.module.system.controller.admin.mail.vo.account.MailAccountSimpleRespVO;
import com.ibonding.module.system.dal.dataobject.mail.MailAccountDO;
import com.ibonding.module.system.service.mail.MailAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

import static com.ibonding.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Email Account")
@RestController
@RequestMapping("/system/mail-account")
public class MailAccountController {

    @Resource
    private MailAccountService mailAccountService;

    @PostMapping("/create")
    @Operation(summary = "Create Email Account")
    @PreAuthorize("@ss.hasPermission('system:mail-account:create')")
    public CommonResult<Long> createMailAccount(@Valid @RequestBody MailAccountSaveReqVO createReqVO) {
        return success(mailAccountService.createMailAccount(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update Email Account")
    @PreAuthorize("@ss.hasPermission('system:mail-account:update')")
    public CommonResult<Boolean> updateMailAccount(@Valid @RequestBody MailAccountSaveReqVO updateReqVO) {
        mailAccountService.updateMailAccount(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete Email Account")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:mail-account:delete')")
    public CommonResult<Boolean> deleteMailAccount(@RequestParam Long id) {
        mailAccountService.deleteMailAccount(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get Email Account")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:mail-account:query')")
    public CommonResult<MailAccountRespVO> getMailAccount(@RequestParam("id") Long id) {
        MailAccountDO account = mailAccountService.getMailAccount(id);
        return success(BeanUtils.toBean(account, MailAccountRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "Get Email Account Pagination")
    @PreAuthorize("@ss.hasPermission('system:mail-account:query')")
    public CommonResult<PageResult<MailAccountRespVO>> getMailAccountPage(@Valid MailAccountPageReqVO pageReqVO) {
        PageResult<MailAccountDO> pageResult = mailAccountService.getMailAccountPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MailAccountRespVO.class));
    }

    @GetMapping({"/list-all-simple", "simple-list"})
    @Operation(summary = "Get Simple Email Account List")
    public CommonResult<List<MailAccountSimpleRespVO>> getSimpleMailAccountList() {
        List<MailAccountDO> list = mailAccountService.getMailAccountList();
        return success(BeanUtils.toBean(list, MailAccountSimpleRespVO.class));
    }

}
