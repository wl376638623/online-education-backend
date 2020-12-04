package com.wanglu.eduorder.service.impl;

import com.wanglu.commonutils.ordervo.CourseWebVoOrder;
import com.wanglu.commonutils.ordervo.UcenterMemberOrder;
import com.wanglu.eduorder.client.EduClient;
import com.wanglu.eduorder.client.UcenterClient;
import com.wanglu.eduorder.entity.Order;
import com.wanglu.eduorder.mapper.OrderMapper;
import com.wanglu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanglu.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;
    //生成订单的方法
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        //通过远程调用根据课程id获取用户信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        Order order = new Order();
        //生成订单号
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setId(courseId);//课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);//未支付
        order.setPayType(1);//支付类型
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }
}
