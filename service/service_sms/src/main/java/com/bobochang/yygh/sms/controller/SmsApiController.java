package com.bobochang.yygh.sms.controller;

import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.sms.service.SmsService;
import com.bobochang.yygh.sms.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author bobochang
 * @description
 * @created 2022/7/6-11:29
 **/
@Api(tags = "验证码接口管理")
@RestController
@RequestMapping("/api/sms")
public class SmsApiController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation(value = "发送验证码")
    @GetMapping("/send/{phone}")
    public Result sendCode(@PathVariable String phone) {

        //从redis中获取手机号对应的验证码 若存在则直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }

        //若不存在验证码则通过整合短信服务进行发送 并将验证码存放到Redis并设置有效时间
        code = RandomUtil.getSixBitRandom();
        boolean isSend = smsService.send(phone, code);
        if (isSend) {
            redisTemplate.opsForValue().set(phone, code, 3, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.fail().message("发送失败");
        }
    }
}
