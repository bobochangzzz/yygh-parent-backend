package com.bobochang.yygh.user.controller.api;

import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.common.utils.AuthContextHolder;
import com.bobochang.yygh.model.user.Patient;
import com.bobochang.yygh.user.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author bobochang
 * @description
 * @created 2022/7/13-14:35
 **/

@Api(tags = "就诊人管理接口")
@RestController
@RequestMapping("/api/user/patient")
public class PatientApiController {

    @Autowired
    private PatientService patientService;

    @ApiOperation(value = "获取就诊人列表")
    @GetMapping("/auth/findAll")
    public Result findAll(HttpServletRequest request) {
        //通过工具类获得当前登陆用户的id
        Long userId = AuthContextHolder.getUserId(request);
        List<Patient> patientList = patientService.findAllByUserId(userId);
        return Result.ok(patientList);
    }

    @ApiOperation(value = "添加就诊人信息")
    @PostMapping("/auth/save")
    public Result save(@RequestBody Patient patient, HttpServletRequest request) {
        //获取请求头中的用户id
        Long userId = AuthContextHolder.getUserId(request);
        patient.setUserId(userId);
        patientService.save(patient);
        return Result.ok();
    }

    @ApiOperation(value = "根据就诊人id获取就诊人信息")
    @GetMapping("/auth/get/{id}")
    public Result getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return Result.ok(patient);
    }

    @ApiOperation(value = "修改就诊人信息")
    @PostMapping("/auth/update")
    public Result updatePatient(@RequestBody Patient patient) {
        patientService.updateById(patient);
        return Result.ok();
    }

    @ApiOperation(value = "删除就诊人信息")
    @DeleteMapping("/auth/remove/{id}")
    public Result removePatient(@PathVariable Long id) {
        patientService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据就诊人id获取就诊人信息")
    @GetMapping("/inner/get/{id}")
    public Patient getPatient(@PathVariable Long id) {
        Patient patient = patientService.getById(id);
        return patient;
    }
}
