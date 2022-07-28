package com.bobochang.yygh.sms.service.impl;

import com.bobochang.yygh.sms.service.SmsService;
import com.bobochang.yygh.vo.msm.MsmVo;
import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

/**
 * @author bobochang
 * @description
 * @created 2022/7/6-11:30
 **/
@Service
public class SmsServiceImpl implements SmsService {

    private String acount = "8a216da881ad97540181d141b4850611";
    private String token = "1a2937e60ba34a639b1a901adaa10fde";
    private String appId = "8a216da881ad97540181d141b59f0618";

    @Override
    public boolean send(String phone, String code) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }

        //生产环境请求地址：app.cloopen.com
        String serverIp = "app.cloopen.com";
        //请求端口
        String serverPort = "8883";
        //请使用管理控制台中已创建应用的APPID
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(acount, token);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        HashMap<String, Object> result = sdk.sendTemplateSMS(phone, "1", new String[]{code, "3"});
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }
        return true;
    }

    @Override
    public boolean send(MsmVo msmVo) {
        if (msmVo.getPhone() != null) {
            String code = (String) msmVo.getParam().get("code");
            return this.send(msmVo.getPhone(), code);
        }
        return false;
    }
}
