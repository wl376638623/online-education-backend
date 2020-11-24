package com.wanglu.educenter.controller;


import com.wanglu.commonutils.R;
import com.wanglu.educenter.entity.UcenterMember;
import com.wanglu.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-24
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //登录
    @GetMapping("login")
    public R loginUser(@RequestBody UcenterMember member) {
        //调用service里面的方法实现登录
        return R.ok();

    }
    //注册
}

