package com.wanglu.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanglu.commonutils.JwtUtils;
import com.wanglu.commonutils.R;
import com.wanglu.eduorder.entity.Order;
import com.wanglu.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-02
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    //1.生成订单方法
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        String orderNo = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderNo);
    }

    //根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);//支付状态 1代表已经支付
        int count = orderService.count(wrapper);
        if(count>0) { //已经支付
            return true;
        } else {
            return false;
        }
    }
}

