package com.ibonding.server.controller;

import com.ibonding.framework.common.pojo.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ibonding.framework.common.exception.enums.GlobalErrorCodeConstants.NOT_IMPLEMENTED;

/**
 * Default Controller to handle the 404 prompt when some modules are not enabled.
 * For Example, the Path `/pay/**`, payment
 *
 * @author Agaru
 */
@RestController
public class DefaultController {

    @RequestMapping(value = {"/admin-api/report/**"})
    public CommonResult<Boolean> report404() {
        return CommonResult.error(NOT_IMPLEMENTED.getCode(),
                "[Report Module ibonding-module-report - Disabled]");
    }

    @RequestMapping(value = {"/admin-api/pay/**"})
    public CommonResult<Boolean> pay404() {
        return CommonResult.error(NOT_IMPLEMENTED.getCode(),
                "[Payment Module ibonding-module-pay - Disabled]");
    }

    @RequestMapping(value = {"/admin-api/ai/**"})
    public CommonResult<Boolean> ai404() {
        return CommonResult.error(NOT_IMPLEMENTED.getCode(),
                "[AI Model ibonding-module-ai - Disabled]");
    }
}
