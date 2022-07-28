package com.bobochang.yygh.common.utils;

import com.bobochang.yygh.common.helper.JwtHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bobochang
 * @description 获取用户信息工具类
 * @created 2022/7/13-11:46
 **/
public class AuthContextHolder {

    //从请求头中获取用户id
    public static Long getUserId(HttpServletRequest request) {
        //从请求头中获取token
        String token = request.getHeader("token");
        //通过JwtHelper工具类获得用户id
        return JwtHelper.getUserId(token);
    }

    //从请求头中获取用户名称
    public static String getUserName(HttpServletRequest request) {
        //从请求头中获取token
        String token = request.getHeader("token");
        //通过JwtHelper工具类获得用户id
        return JwtHelper.getUserName(token);
    }
}
