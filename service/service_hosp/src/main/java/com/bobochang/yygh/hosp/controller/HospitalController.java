package com.bobochang.yygh.hosp.controller;

import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.hosp.service.HospitalService;
import com.bobochang.yygh.model.hosp.Hospital;
import com.bobochang.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/3-14:47
 **/
@RestController
@RequestMapping("/admin/hosp/hospital")
@Api(tags = "医院管理")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    /**
     * 医院列表接口
     *
     * @param page
     * @param limit
     * @param hospitalQueryVo
     * @return
     */
    @ApiOperation(value = "医院列表接口")
    @GetMapping("/list/{page}/{limit}")
    public Result listHospital(@PathVariable Integer page,
                               @PathVariable Integer limit,
                               HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> pageInfo = hospitalService.list(page, limit, hospitalQueryVo);
        return Result.ok(pageInfo);
    }

    /**
     * 更新医院状态接口
     * @param id
     * @param status
     * @return
     */
    @ApiOperation(value = "更新医院状态接口")
    @PutMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id, @PathVariable Integer status) {
        hospitalService.updateStatus(id,status);
        return Result.ok();
    }

    /**
     * 医院详情信息
     * @param id
     * @return
     */
    @ApiOperation(value = "医院详情信息")
    @GetMapping("/showHospDetail/{id}")
    public Result showHospDetail(@PathVariable String id){
        Map<String,Object> hospDetailMap = hospitalService.getHospDetailById(id);
        return Result.ok(hospDetailMap);
    }
}
