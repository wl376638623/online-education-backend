package com.wanglu.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.java.accessibility.util.GUIInitializedListener;
import com.wanglu.commonutils.JwtUtils;
import com.wanglu.commonutils.MD5;
import com.wanglu.educenter.entity.UcenterMember;
import com.wanglu.educenter.entity.vo.RegisterVo;
import com.wanglu.educenter.mapper.UcenterMemberMapper;
import com.wanglu.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanglu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-24
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //登录方法
    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败");
        }
        //判断手机号是否争取
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember == null) {
            throw new GuliException(20001, "登录失败");
        }
        //判断密码是否正确
        //把输入的密码进行加密 然后和数据库的密码进行比较(md5)
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001, "密码不正确");
        }
        //判断用户是否被禁用
        if (mobileMember.getIsDisabled()) {
            throw new GuliException(20001, "登录失败");
        }
        //登录成功
        //生成token字符串
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }
    //注册方法
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        //获取手机验证码
        String code = registerVo.getCode();
        //获取手机号
        String mobile = registerVo.getMobile();
        //获取用户昵称
        String nickname = registerVo.getNickname();
        //获取用户密码
        String password = registerVo.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001, "用户注册信息不全，请重新注册");
        }
        //半段手机验证码是否正确
        //获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "验证输入有误，请重新输入！");
        }
        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuliException(20001, "手机号已经注册请重新输入!");
        }
        //数据库中添加数据
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);//用户不被禁用
        ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(ucenterMember);
    }
    //根据openid进行查询
    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }
    //查询一天注册的人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
