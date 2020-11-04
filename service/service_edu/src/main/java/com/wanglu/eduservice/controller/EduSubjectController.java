package com.wanglu.eduservice.controller;


import com.wanglu.commonutils.R;
import com.wanglu.eduservice.entity.subject.OneSubject;
import com.wanglu.eduservice.service.EduSubjectService;
import com.wanglu.eduservice.service.impl.EduSubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件，把文件内容读出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    //课程分类列表（树形）
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list =  eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

