package com.bobochang.yygh.hosp.controller.api;

import com.bobochang.yygh.common.exception.YyghException;
import com.bobochang.yygh.common.helper.HttpRequestHelper;
import com.bobochang.yygh.common.result.Result;
import com.bobochang.yygh.common.result.ResultCodeEnum;
import com.bobochang.yygh.common.utils.MD5;
import com.bobochang.yygh.hosp.service.DepartmentService;
import com.bobochang.yygh.hosp.service.HospitalService;
import com.bobochang.yygh.hosp.service.HospitalSetService;
import com.bobochang.yygh.hosp.service.ScheduleService;
import com.bobochang.yygh.model.hosp.Department;
import com.bobochang.yygh.model.hosp.Hospital;
import com.bobochang.yygh.model.hosp.Schedule;
import com.bobochang.yygh.vo.hosp.DepartmentQueryVo;
import com.bobochang.yygh.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/2-14:18
 **/
@Api(tags = "Api接口")
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private HospitalSetService hospitalSetService;


    /**
     * 上传医院接口
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "上传医院接口")
    @PostMapping("/saveHospital")
    public Result saveHospital(HttpServletRequest request) {

        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(map);

        String hospSign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSignKey = MD5.encrypt(signKey);

        if (!hospSign.equals(encryptSignKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        String logoDataString = (String) paramMap.get("logoData");
        if (!StringUtils.isEmpty(logoDataString)) {
            String logoData = logoDataString.replaceAll(" ", "+");
            paramMap.put("logoData", logoData);
        }


        hospitalService.saveHospital(paramMap);
        return Result.ok();
    }

    /**
     * 查询医院接口
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询医院接口")
    @PostMapping("/hospital/show")
    public Result showHospital(HttpServletRequest request) {

        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        String hospSign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSignKey = MD5.encrypt(signKey);

        if (!hospSign.equals(encryptSignKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        Hospital hospital = hospitalService.showHospitalByHoscode(hoscode);
        return Result.ok(hospital);
    }

    /**
     * 上传科室接口
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "上传科室接口")
    @PostMapping("/saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {

        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        String hospSign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSignKey = MD5.encrypt(signKey);

        if (!hospSign.equals(encryptSignKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.saveDepartment(paramMap);
        return Result.ok();
    }

    /**
     * 查询科室接口
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询科室接口")
    @PostMapping("/department/list")
    public Result findDepartment(HttpServletRequest request) {

        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        String hospSign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSignKey = MD5.encrypt(signKey);

        if (!hospSign.equals(encryptSignKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String) paramMap.get("limit"));

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);

        Page<Department> pageInfo = departmentService.findDepartment(page, limit, departmentQueryVo);
        return Result.ok(pageInfo);
    }

    /**
     * 删除科室接口
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除科室接口")
    @PostMapping("/department/remove")
    public Result removeDepartment(HttpServletRequest request) {

        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        String hospSign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSignKey = MD5.encrypt(signKey);

        if (!hospSign.equals(encryptSignKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.remove(hoscode, depcode);
        return Result.ok();
    }

    /**
     * 上传排班接口
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "上传排班接口")
    @PostMapping("/saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {

        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        String hospSign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSignKey = MD5.encrypt(signKey);

        if (!hospSign.equals(encryptSignKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.save(paramMap);
        return Result.ok();
    }

    /**
     * 查询排班接口
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询排班接口")
    @PostMapping("/schedule/list")
    public Result findSchedule(HttpServletRequest request) {

        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        String hospSign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSignKey = MD5.encrypt(signKey);

        if (!hospSign.equals(encryptSignKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String) paramMap.get("limit"));

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        Page<Schedule> pageInfo = scheduleService.findSchedule(page, limit, scheduleQueryVo);
        return Result.ok(pageInfo);
    }

    /**
     * 删除排班接口
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除排班接口")
    @PostMapping("/schedule/remove")
    public Result removeSchedule(HttpServletRequest request) {

        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        String hospSign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");
        String hosScheduleId = (String) paramMap.get("hosScheduleId");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSignKey = MD5.encrypt(signKey);

        if (!hospSign.equals(encryptSignKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }
}
