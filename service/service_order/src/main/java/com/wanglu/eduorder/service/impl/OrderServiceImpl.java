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

}
