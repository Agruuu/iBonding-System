package com.ibonding.module.pay.controller.admin.transfer;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.module.pay.controller.admin.transfer.vo.*;
import com.ibonding.module.pay.convert.transfer.PayTransferConvert;
import com.ibonding.module.pay.dal.dataobject.transfer.PayTransferDO;
import com.ibonding.module.pay.service.transfer.PayTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import static com.ibonding.framework.common.pojo.CommonResult.success;
import static com.ibonding.framework.common.util.servlet.ServletUtils.getClientIP;

@Tag(name = "Management Backend - Transfer Order")
@RestController
@RequestMapping("/pay/transfer")
@Validated
public class PayTransferController {

    @Resource
    private PayTransferService payTransferService;

    @PostMapping("/create")
    @Operation(summary = "Create Transfer Order and Initiate Transfer")
    @PreAuthorize("@ss.hasPermission('pay:transfer:create')")
    public CommonResult<PayTransferCreateRespVO> createPayTransfer(@Valid @RequestBody PayTransferCreateReqVO reqVO) {
        PayTransferDO payTransfer = payTransferService.createTransfer(reqVO, getClientIP());
        return success(new PayTransferCreateRespVO().setId(payTransfer.getId()).setStatus(payTransfer.getStatus()));
    }

    @GetMapping("/get")
    @Operation(summary = "Get Transfer Order")
    @PreAuthorize("@ss.hasPermission('pay:transfer:query')")
    public CommonResult<PayTransferRespVO> getTransfer(@RequestParam("id") Long id) {
        return success(PayTransferConvert.INSTANCE.convert(payTransferService.getTransfer(id)));
    }

    @GetMapping("/page")
    @Operation(summary = "Get Transfer Order Pagination")
    @PreAuthorize("@ss.hasPermission('pay:transfer:query')")
    public CommonResult<PageResult<PayTransferPageItemRespVO>> getTransferPage(@Valid PayTransferPageReqVO pageVO) {
        PageResult<PayTransferDO> pageResult = payTransferService.getTransferPage(pageVO);
        return success(PayTransferConvert.INSTANCE.convertPage(pageResult));
    }
}
