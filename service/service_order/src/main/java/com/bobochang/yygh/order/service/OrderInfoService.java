package com.bobochang.yygh.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bobochang.yygh.model.order.OrderInfo;
import com.bobochang.yygh.vo.order.OrderCountQueryVo;
import com.bobochang.yygh.vo.order.OrderQueryVo;

import java.util.Map;

/**
 * @author bobochang
 * @description
 * @created 2022/7/16-13:05
 **/
public interface OrderInfoService extends IService<OrderInfo> {
    Long saveOrder(String scheduleId, Long patientId);

    IPage<OrderInfo> selectPage(IPage<OrderInfo> pageParam, OrderQueryVo orderQueryVo);

    OrderInfo getOrder(String orderId);

    Map<String,Object> show(Long id);

    Boolean cancelOrder(Long orderId);

    Map<String, Object> getCountMap(OrderCountQueryVo orderCountQueryVo);
}
