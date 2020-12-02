package com.wanglu.educenter.controller;


import com.wanglu.commonutils.JwtUtils;
import com.wanglu.commonutils.R;
import com.wanglu.educenter.entity.UcenterMember;
import com.wanglu.educenter.entity.vo.RegisterVo;
import com.wanglu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;

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
        return R.ok().data("token", token);

    }

    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类里面的对象，根据request获取头信息,返回用户的信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据根据用户id查询用户信息
        UcenterMember member = memberService.getById(memberId);
        //将返回的对象密码设置为空
        member.setPassword(null);
        return R.ok().data("userInfo", member);
    }
    //根据token字符串获取用户信息
    @PostMapping("getInfoUc/{id}")
    public R getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        UcenterMember member = new UcenterMember();
        BeanUtils.copyProperties(ucenterMember,member);
        return R.ok().data("member",member);
    }
}

