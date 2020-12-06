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

    //查询订单支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        //根据订单号查询支付状态
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付出错了");
        }
        //如果返回map里面不为空 通过map获取订单状态
        if (map.get("trade_state").equals("SUCCESS")) {
            //向支付表中添加记录 更新订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().message("支付中");
    }

}

