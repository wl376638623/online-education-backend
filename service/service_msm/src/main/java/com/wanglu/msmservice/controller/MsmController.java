package com.wanglu.msmservice.controller;

import com.wanglu.commonutils.R;
import com.wanglu.msmservice.service.MsmService;
import com.wanglu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    //发送短息的方法
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        //生成随机的值传递给阿里云进行发送
        String code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用service中的发送短息方法
        boolean isSend = msmService.send(param, phone);
        if (isSend) {
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
