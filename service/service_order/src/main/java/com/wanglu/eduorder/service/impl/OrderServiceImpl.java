package com.wanglu.eduorder.service.impl;

import com.wanglu.eduorder.entity.Order;
import com.wanglu.eduorder.mapper.OrderMapper;
import com.wanglu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-02
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    //生成订单的方法
    @Override
    public String createOrders(String courseId, String memberIdByJwtToken) {
        //通过远程调用根据用户id获取用户信息

        //通过远程调用根据课程id获取用户信息

        return null;
    }
}
