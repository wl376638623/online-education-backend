package com.wanglu.eduorder.service;

import com.wanglu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-02
 */
public interface PayLogService extends IService<PayLog> {

    Map createActive(String orderNo);

    //根据订单号查询支付状态
    Map<String, String> queryPayStatus(String orderNo);

    void updateOrdersStatus(Map<String, String> map);
    //向支付表中添加记录 更新订单状态
    
}
