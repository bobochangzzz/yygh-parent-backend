package com.bobochang.yygh.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.model.user.UserInfo;
import com.bobochang.yygh.user.service.UserInfoService;
import com.bobochang.yygh.vo.user.UserInfoQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/13-16:34
 **/
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "用户列表展示带分页查询")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       UserInfoQueryVo userInfoQueryVo) {
        Page<UserInfo> userInfoPage = new Page<>(page, limit);
        IPage<UserInfo> pageModel = userInfoService.selectPage(userInfoPage, userInfoQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "用户状态锁定接口")
    @GetMapping("/lock/{userId}/{status}")
    public Result lock(@PathVariable Long userId, @PathVariable Integer status) {
        userInfoService.lock(userId, status);
        return Result.ok();
    }

    @ApiOperation(value = "用户详情信息接口")
    @GetMapping("/show/{userId}")
    public Result show(@PathVariable Long userId) {
        Map<String, Object> result = userInfoService.show(userId);
        return Result.ok(result);
    }

    @ApiOperation(value = "用户信息认证审批接口")
    @GetMapping("/approval/{userId}/{authStatus}")
    public Result approval(@PathVariable Long userId, @PathVariable Integer authStatus) {
        userInfoService.approval(userId, authStatus);
        return Result.ok();
    }
}
