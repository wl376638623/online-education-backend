package com.wanglu.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanglu.commonutils.R;
import com.wanglu.eduservice.entity.EduTeacher;
import com.wanglu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    //分页查询方法
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,@PathVariable long limit) {
        Page<EduTeacher> teacherPage = new Page<>();
        Map<String,Object> map = teacherService.getTeacherFrontList(teacherPage);
        //返回分页所有数据
        return R.ok().data(map);
    }
}
