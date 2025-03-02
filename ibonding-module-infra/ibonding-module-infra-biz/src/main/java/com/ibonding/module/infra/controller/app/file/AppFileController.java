package com.ibonding.module.infra.controller.app.file;

import cn.hutool.core.io.IoUtil;
import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.module.infra.controller.admin.file.vo.file.FileCreateReqVO;
import com.ibonding.module.infra.controller.admin.file.vo.file.FilePresignedUrlRespVO;
import com.ibonding.module.infra.controller.app.file.vo.AppFileUploadReqVO;
import com.ibonding.module.infra.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.ibonding.framework.common.pojo.CommonResult.success;

@Tag(name = "User App - File Storage")
@RestController
@RequestMapping("/infra/file")
@Validated
@Slf4j
public class AppFileController {

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "Upload File")
    @PermitAll
    public CommonResult<String> uploadFile(AppFileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String path = uploadReqVO.getPath();
        return success(fileService.createFile(file.getOriginalFilename(), path, IoUtil.readBytes(file.getInputStream())));
    }

    @GetMapping("/presigned-url")
    @Operation(summary = "Get File Pre-signed URL", description = "Mode 2: Front-end File Upload: Used for the front-end to directly upload files to storage services such as Qiniu and Alibaba Cloud OSS.")
    @PermitAll
    public CommonResult<FilePresignedUrlRespVO> getFilePresignedUrl(@RequestParam("path") String path) throws Exception {
        return success(fileService.getFilePresignedUrl(path));
    }

    @PostMapping("/create")
    @Operation(summary = "Create File", description = "Mode 2: Front - end File Upload: In conjunction with the presigned-url interface, record the uploaded files")
    @PermitAll
    public CommonResult<Long> createFile(@Valid @RequestBody FileCreateReqVO createReqVO) {
        return success(fileService.createFile(createReqVO));
    }

}
