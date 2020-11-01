package com.wanglu.eduservice.controller;

//import org.springframework.stereotype.Controller;
import com.wanglu.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin //解决跨域
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }
    //info
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://www.google.com/imgres?imgurl=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fthumb%2Fe%2Fe8%2FCandymyloveYasu.png%2F220px-CandymyloveYasu.png&imgrefurl=https%3A%2F%2Fzh.wikipedia.org%2Fwiki%2F%25E5%25A4%25B4%25E5%2583%258F&tbnid=lLmpObEzLEyh_M&vet=12ahUKEwjI0dio4dvsAhW4zIsBHUM2Bl4QMygAegUIARCeAQ..i&docid=ryEBP9pMcfMEoM&w=220&h=220&itg=1&q=%E5%A4%B4%E5%83%8F&ved=2ahUKEwjI0dio4dvsAhW4zIsBHUM2Bl4QMygAegUIARCeAQ");
    }
}
