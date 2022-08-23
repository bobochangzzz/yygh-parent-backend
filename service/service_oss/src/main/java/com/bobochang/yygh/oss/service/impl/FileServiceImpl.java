package com.bobochang.yygh.oss.service.impl;

import com.bobochang.yygh.oss.service.FileService;
import com.bobochang.yygh.oss.utils.ConstantOssPropertiesUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.AppendObjectRequest;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.region.Region;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author bobochang
 * @description
 * @created 2022/7/12-10:20 PM
 **/
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file, HttpServletRequest request) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = ConstantOssPropertiesUtils.SECRETID;
        String secretKey = ConstantOssPropertiesUtils.SECRECTKEY;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(ConstantOssPropertiesUtils.REGION);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定文件将要存放的存储桶
        String bucketName = ConstantOssPropertiesUtils.BUCKETNAME;
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = file.getOriginalFilename();
        //通过uuid为文件名赋予唯一值
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        objectName = uuid + objectName;
        //按照日期 将文件存放到对应日期的文件夹中
        String timeUrl = new DateTime().toString("yyyy/MM/dd");
        // 2022/07/13/xxx.jpg
        objectName = timeUrl + "/" + objectName;
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = objectName;
        // 处理文件路径
        String url = null;
        try {
            String filePath = request.getSession().getServletContext().getRealPath("/") + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            File localFile = new File(filePath);
            // 报错请求对象
            AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketName, key, localFile);
            // 设置节点
            appendObjectRequest.setPosition(0L);
            cosClient.appendObject(appendObjectRequest);
            // 获取返回对象
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
            COSObject cosObject = cosClient.getObject(getObjectRequest);
            url = cosObject.getObjectContent().getHttpRequest().getURI().toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
        return url;
    }
}
