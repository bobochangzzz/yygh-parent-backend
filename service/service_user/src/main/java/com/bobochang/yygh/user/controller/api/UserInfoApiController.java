package com.bobochang.yygh.user.controller.api;

import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.common.utils.AuthContextHolder;
import com.bobochang.yygh.model.user.UserInfo;
import com.bobochang.yygh.user.service.UserInfoService;
import com.bobochang.yygh.vo.user.LoginVo;
import com.bobochang.yygh.vo.user.UserAuthVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/5-22:38
 **/
@Api(tags = "手机登录接口管理")
@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "手机登录接口")
    @PostMapping("/login")
    public Result userLogin(@RequestBody LoginVo loginVo) {
        Map<String, Object> result = userInfoService.userLogin(loginVo);
        return Result.ok(result);
    }

    @ApiOperation(value = "用户认证接口")
    @PostMapping("/auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
        //用户认证实则向数据库中添加对应id中的userAuthVo对象数据
        userInfoService.userAuth(AuthContextHolder.getUserId(request), userAuthVo);
        return Result.ok();
    }

    @ApiOperation(value = "获取用户信息接口")
    @GetMapping("/auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        UserInfo userInfo = userInfoService.getById(AuthContextHolder.getUserId(request));
        return Result.ok(userInfo);
    }
}
