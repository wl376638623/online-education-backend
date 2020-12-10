package com.wanglu.staservice.schedule;

import com.wanglu.staservice.service.StatisticsDailyService;
import com.wanglu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService staService;

    //    美格五秒钟执行一次这个方法
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void task1() {
//        System.out.println("*****task1执行了");
//    }
    //在每天凌晨一点执行task
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
