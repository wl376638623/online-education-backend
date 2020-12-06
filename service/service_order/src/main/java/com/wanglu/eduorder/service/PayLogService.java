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
}
