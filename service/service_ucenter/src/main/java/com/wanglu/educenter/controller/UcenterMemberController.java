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
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member) {
        //调用service里面的方法实现登录
        //返回token值 jwt
        String token = memberService.login(member);
        return R.ok().data("token",token);

    }
    //注册
}

