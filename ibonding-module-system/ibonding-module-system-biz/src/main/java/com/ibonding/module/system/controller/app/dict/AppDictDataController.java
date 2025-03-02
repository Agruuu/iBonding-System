package com.ibonding.module.system.controller.app.dict;

import com.ibonding.framework.common.enums.CommonStatusEnum;
import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.system.controller.app.dict.vo.AppDictDataRespVO;
import com.ibonding.module.system.dal.dataobject.dict.DictDataDO;
import com.ibonding.module.system.service.dict.DictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

import static com.ibonding.framework.common.pojo.CommonResult.success;

@Tag(name = "User App - Dictionary Data")
@RestController
@RequestMapping("/system/dict-data")
@Validated
public class AppDictDataController {

    @Resource
    private DictDataService dictDataService;

    @GetMapping("/type")
    @Operation(summary = "Get Dict Data List by Type")
    @Parameter(name = "type", description = "字典类型", required = true, example = "common_status")
    @PermitAll
    public CommonResult<List<AppDictDataRespVO>> getDictDataListByType(@RequestParam("type") String type) {
        List<DictDataDO> list = dictDataService.getDictDataList(
                CommonStatusEnum.ENABLE.getStatus(), type);
        return success(BeanUtils.toBean(list, AppDictDataRespVO.class));
    }

}
