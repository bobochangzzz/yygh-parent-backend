package com.bobochang.yygh.hosp.service;

import com.bobochang.yygh.model.hosp.Schedule;
import com.bobochang.yygh.vo.hosp.ScheduleOrderVo;
import com.bobochang.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/3-13:05
 **/
public interface ScheduleService {
    void save(Map<String, Object> paramMap);

    Page<Schedule> findSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    void remove(String hoscode, String hosScheduleId);

    Map<String, Object> findScheduleRule(long page, long limit, String hoscode, String depcode);

    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);

    Map<String,Object> getBookingScheduleRule(Integer page, Integer limit, String hoscode, String depcode);

    Schedule getScheduleById(String scheduleId);

    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    void update(Schedule schedule);
}
