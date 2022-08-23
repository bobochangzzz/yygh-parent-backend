package com.bobochang.yygh.oss.controller;

import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * @author bobochang
 * @description
 * @created 2022/7/12-10:18 PM
 **/

@Api(tags = "阿里云文件接口管理")
@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传文件接口")
    @PostMapping("/fileUpload")
    public Result fileUpload(MultipartFile file,HttpServletRequest request){
        String url = fileService.upload(file, request);
        return Result.ok(url);
    }
}
