package com.bobochang.yygh.sms.service;

import com.bobochang.yygh.vo.msm.MsmVo;

/**
 * @author bobochang
 * @description
 * @created 2022/7/6-11:30
 **/
public interface SmsService {
    boolean send(String phone, String code);
    //mq发送短信封装
    boolean send(MsmVo msmVo);
}
