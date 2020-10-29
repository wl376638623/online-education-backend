package com.wanglu.eduservice.controller;

//import org.springframework.stereotype.Controller;
import com.wanglu.commonutils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }
    //info
    @PostMapping("infi")
    public R info(){
        return R.ok().data("roles","admin").data("name","admin").data("avatar","https://lh3.googleusercontent.com/proxy/kX5wO4XG5YyAtmDEZvTaqIYf1XEGhJ7edTV7UW7hxi97RRnxwLuq5gTT6GvsgjqXb_uE4uLFU40-aINH48kQsppLdFBAEeXq1X3H-JzNKyc");
    }
}
