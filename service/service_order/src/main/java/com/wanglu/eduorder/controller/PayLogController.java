package com.wanglu.eduorder.controller;


import com.wanglu.commonutils.R;
import com.wanglu.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-02
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    //生成微信支付
    @GetMapping("createActive/{orderNo}")
    public R createActive(@PathVariable String orderNo) {
        //返回相关信息 包含二维码地址 还有其他信息
        Map map = payLogService.createActive(orderNo);
        return R.ok().data(map);
    }

}

