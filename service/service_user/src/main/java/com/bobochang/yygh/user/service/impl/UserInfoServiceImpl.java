package com.bobochang.yygh.user.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bobochang.yygh.common.exception.YyghException;
import com.bobochang.yygh.common.helper.JwtHelper;
import com.bobochang.yygh.common.result.ResultCodeEnum;
import com.bobochang.yygh.enums.AuthStatusEnum;
import com.bobochang.yygh.model.user.Patient;
import com.bobochang.yygh.model.user.UserInfo;
import com.bobochang.yygh.user.mapper.UserInfoMapper;
import com.bobochang.yygh.user.service.PatientService;
import com.bobochang.yygh.user.service.UserInfoService;
import com.bobochang.yygh.vo.user.LoginVo;
import com.bobochang.yygh.vo.user.UserAuthVo;
import com.bobochang.yygh.vo.user.UserInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/5-22:37
 **/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PatientService patientService;

    @Override
    public Map<String, Object> userLogin(LoginVo loginVo) {

        //获取用户手机号和验证码
        String phone = loginVo.getPhone();
        String code = loginVo.getCode();

        //手机号和验证码校验空值
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }

        //TODO 验证码校验
        String redisCode = redisTemplate.opsForValue().get(phone);
        if (!code.equals(redisCode)) {
            throw new YyghException(ResultCodeEnum.CODE_ERROR);
        }

        //绑定手机号码
        UserInfo userInfo = null;
        if (!StringUtils.isEmpty(loginVo.getOpenid())) {
            userInfo = this.getByOpenid(loginVo.getOpenid());
            if (null != userInfo) {
                userInfo.setPhone(loginVo.getPhone());
                this.updateById(userInfo);
            } else {
                throw new YyghException(ResultCodeEnum.DATA_ERROR);
            }
        }
        if (userInfo == null) {
            //判断登录状态 根据手机号查询数据表 若第一次登录则进行创建 否则直接登录
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", phone);
            userInfo = baseMapper.selectOne(queryWrapper);
            if (userInfo == null) {
                userInfo = new UserInfo();
                userInfo.setName("");
                userInfo.setPhone(phone);
                userInfo.setStatus(1);
                baseMapper.insert(userInfo);
            }
        }

        //校验是否被禁用
        if (userInfo.getStatus() == 0) {
            throw new YyghException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        //返回页面显示名称
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }

        //记录登录 生成token
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("name", name);
        map.put("token", token);

        return map;
    }

    @Override
    public UserInfo getByOpenid(String openId) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void userAuth(Long userId, UserAuthVo userAuthVo) {
        //根据用户id查询用户信息
        UserInfo userInfo = baseMapper.selectById(userId);
        //设置认证信息
        //认证人姓名
        userInfo.setName(userAuthVo.getName());
        userInfo.setUpdateTime(new Date());
        //其他认证信息
        userInfo.setCertificatesType(userAuthVo.getCertificatesType());
        userInfo.setCertificatesNo(userAuthVo.getCertificatesNo());
        userInfo.setCertificatesUrl(userAuthVo.getCertificatesUrl());
        userInfo.setAuthStatus(AuthStatusEnum.AUTH_RUN.getStatus());
        //进行信息更新
        baseMapper.updateById(userInfo);
    }

    @Override
    public IPage<UserInfo> selectPage(Page<UserInfo> userInfoPage, UserInfoQueryVo userInfoQueryVo) {
        //用户名称
        String name = userInfoQueryVo.getKeyword();
        //用户状态
        Integer status = userInfoQueryVo.getStatus();
        //用户认证状态
        Integer authStatus = userInfoQueryVo.getAuthStatus();
        //用户创建时间
        String createTimeBegin = userInfoQueryVo.getCreateTimeBegin();
        //用户更新时间
        String createTimeEnd = userInfoQueryVo.getCreateTimeEnd();

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status", status);
        }
        if (!StringUtils.isEmpty(authStatus)) {
            queryWrapper.eq("auth_status", authStatus);
        }
        if (!StringUtils.isEmpty(createTimeBegin)) {
            queryWrapper.ge("create_time", createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)) {
            queryWrapper.le("update_time", createTimeEnd);
        }

        Page<UserInfo> page = baseMapper.selectPage(userInfoPage, queryWrapper);
        //通过流式遍历 修改userinfo中对应编码值
        page.getRecords().forEach(this::packageUserInfo);
        return page;
    }

    @Override
    public void lock(Long userId, Integer status) {
        if (status == 1 || status == 0) {
            UserInfo userInfo = baseMapper.selectById(userId);
            userInfo.setStatus(status);
            baseMapper.updateById(userInfo);
        }
    }

    @Override
    public Map<String, Object> show(Long userId) {
        HashMap<String, Object> map = new HashMap<>();
        //根据userId查询用户所有信息
        UserInfo userInfo = this.packageUserInfo(baseMapper.selectById(userId));
        map.put("userInfo", userInfo);
        //根据userId查询该用户下所有的就诊人信息
        List<Patient> list = patientService.findAllByUserId(userId);
        map.put("patientList", list);
        return map;
    }

    @Override
    public void approval(Long userId, Integer authStatus) {
        if (authStatus == 2 || authStatus == -1) {
            UserInfo userInfo = baseMapper.selectById(userId);
            userInfo.setAuthStatus(authStatus);
            baseMapper.updateById(userInfo);
        }
    }

    private UserInfo packageUserInfo(UserInfo userInfo) {
        //处理实名认证状态
        userInfo.getParam().put("authStatusString", AuthStatusEnum.getStatusNameByStatus(userInfo.getAuthStatus()));
        //处理用户状态
        String status = userInfo.getStatus() == 0 ? "锁定" : "未锁定";
        userInfo.getParam().put("statusString", status);
        return userInfo;
    }
}
