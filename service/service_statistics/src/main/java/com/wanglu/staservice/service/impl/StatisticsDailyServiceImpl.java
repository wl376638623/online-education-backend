package com.wanglu.staservice.service.impl;

import com.wanglu.commonutils.R;
import com.wanglu.staservice.client.UcenterClient;
import com.wanglu.staservice.entity.StatisticsDaily;
import com.wanglu.staservice.mapper.StatisticsDailyMapper;
import com.wanglu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-09
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;
    //统计某一天的注册人数
    @Override
    public void registerCount(String day) {
        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");
        //获取到数据添加到数据库

    }
}
