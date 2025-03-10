package com.ibonding.module.pay.controller.admin.wallet;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageCreateReqVO;
import com.ibonding.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackagePageReqVO;
import com.ibonding.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageRespVO;
import com.ibonding.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageUpdateReqVO;
import com.ibonding.module.pay.convert.wallet.PayWalletRechargePackageConvert;
import com.ibonding.module.pay.dal.dataobject.wallet.PayWalletRechargePackageDO;
import com.ibonding.module.pay.service.wallet.PayWalletRechargePackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import static com.ibonding.framework.common.pojo.CommonResult.success;


@Tag(name = "Management Backend - Wallet Recharge Package")
@RestController
@RequestMapping("/pay/wallet-recharge-package")
@Validated
public class PayWalletRechargePackageController {

    @Resource
    private PayWalletRechargePackageService walletRechargePackageService;

    @PostMapping("/create")
    @Operation(summary = "Create Wallet Recharge Package")
    @PreAuthorize("@ss.hasPermission('pay:wallet-recharge-package:create')")
    public CommonResult<Long> createWalletRechargePackage(@Valid @RequestBody WalletRechargePackageCreateReqVO createReqVO) {
        return success(walletRechargePackageService.createWalletRechargePackage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "UPdate Wallet Recharge Package")
    @PreAuthorize("@ss.hasPermission('pay:wallet-recharge-package:update')")
    public CommonResult<Boolean> updateWalletRechargePackage(@Valid @RequestBody WalletRechargePackageUpdateReqVO updateReqVO) {
        walletRechargePackageService.updateWalletRechargePackage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete Wallet Recharge Package")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pay:wallet-recharge-package:delete')")
    public CommonResult<Boolean> deleteWalletRechargePackage(@RequestParam("id") Long id) {
        walletRechargePackageService.deleteWalletRechargePackage(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get Wallet Recharge Package")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pay:wallet-recharge-package:query')")
    public CommonResult<WalletRechargePackageRespVO> getWalletRechargePackage(@RequestParam("id") Long id) {
        PayWalletRechargePackageDO walletRechargePackage = walletRechargePackageService.getWalletRechargePackage(id);
        return success(PayWalletRechargePackageConvert.INSTANCE.convert(walletRechargePackage));
    }

    @GetMapping("/page")
    @Operation(summary = "Get Wallet Recharge Package Pagination")
    @PreAuthorize("@ss.hasPermission('pay:wallet-recharge-package:query')")
    public CommonResult<PageResult<WalletRechargePackageRespVO>> getWalletRechargePackagePage(@Valid WalletRechargePackagePageReqVO pageVO) {
        PageResult<PayWalletRechargePackageDO> pageResult = walletRechargePackageService.getWalletRechargePackagePage(pageVO);
        return success(PayWalletRechargePackageConvert.INSTANCE.convertPage(pageResult));
    }

}
