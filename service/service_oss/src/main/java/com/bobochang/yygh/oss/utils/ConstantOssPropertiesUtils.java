package com.bobochang.yygh.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author bobochang
 * @description
 * @created 2022/7/12-10:25 PM
 **/
@Component
public class ConstantOssPropertiesUtils implements InitializingBean {

    @Value("${tencent.cos.region}")
    private String region;

    @Value("${tencent.cos.secretId}")
    private String secretId;

    @Value("${tencent.cos.secretKey}")
    private String secretKey;

    @Value("${tencent.cos.bucketName}")
    private String bucketName;

    public static String REGION;
    public static String SECRETID;
    public static String SECRECTKEY;
    public static String BUCKETNAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        REGION = region;
        SECRETID = secretId;
        SECRECTKEY = secretKey;
        BUCKETNAME = bucketName;
    }
}
