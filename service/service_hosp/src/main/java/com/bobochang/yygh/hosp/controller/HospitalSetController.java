package com.bobochang.yygh.hosp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.hosp.service.HospitalSetService;

import com.bobochang.yygh.common.utils.MD5;
import com.bobochang.yygh.model.hosp.HospitalSet;
import com.bobochang.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @author bobochang
 * @description
 * @created 2022/6/29-14:53
 **/

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    /**
     * 获取所有医院信息
     *
     * @return
     */
    @ApiOperation(value = "获取所有医院信息")
    @GetMapping("/findAll")
    public Result findAll() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    /**
     * 删除指定医院信息
     *
     * @param id 指定医院的id
     * @return
     */
    @ApiOperation(value = "删除指定医院信息")
    @DeleteMapping("/{id}")
    public Result deleteByID(@PathVariable long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 条件查询分页
     *
     * @param current            当前页
     * @param limit              每页条数
     * @param hospitalSetQueryVo 医院设置搜索实体类
     * @return
     */
    @ApiOperation(value = "条件查询医院信息")
    @PostMapping("/findPageHospitalSet/{current}/{limit}")
    public Result findPageHospitalSet(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSet> page = new Page<>(current, limit);
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if (!StringUtils.isEmpty(hosname)) {
            queryWrapper.like("hosname", hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hoscode)) {
            queryWrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, queryWrapper);
        return Result.ok(hospitalSetPage);
    }

    /**
     * 添加医院设置信息
     *
     * @param hospitalSet 医院信息
     * @return
     */
    @ApiOperation(value = "添加医院设置信息")
    @PostMapping("/saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        //调用service
        boolean save = hospitalSetService.save(hospitalSet);
        if (save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 根据id获取医院设置信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取医院信息")
    @GetMapping("/getHospitalSet/{id}")
    public Result getHospSetById(@PathVariable long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    /**
     * 修改医院设置信息
     *
     * @param hospitalSet
     * @return
     */
    @ApiOperation(value = "修改医院设置信息")
    @PostMapping("/updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 批量删除医院信息
     *
     * @param idList
     * @return
     */
    @ApiOperation(value = "批量删除医院信息")
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        boolean flag = hospitalSetService.removeByIds(idList);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 医院信息解锁和锁定
     *
     * @param id
     * @param status
     * @return
     */
    @ApiOperation(value = "医院解锁和锁定")
    @PutMapping("/lockHospital/{id}/{status}")
    public Result lockHospitalSet(@PathVariable long id,
                                  @PathVariable Integer status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    /**
     * 签名秘钥发送
     * @param id
     * @return
     */
    @ApiOperation(value = "签名秘钥发送")
    @PutMapping("/sendKey/{id}")
    public Result sendKey(@PathVariable long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }
}
