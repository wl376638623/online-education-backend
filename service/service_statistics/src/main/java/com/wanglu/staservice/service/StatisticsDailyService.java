package com.wanglu.staservice.service;

import com.wanglu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-09
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void registerCount(String day);
    //图标显示 返回两部分书库 日期json数组  数量json数组
    Map<String, Object> getShowData(String type, String begin, String end);
}
