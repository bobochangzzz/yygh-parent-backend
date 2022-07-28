package com.bobochang.yygh.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author bobochang
 * @description
 * @created 2022/7/12-10:19 PM
 **/
public interface FileService {
    String upload(MultipartFile file);
}
