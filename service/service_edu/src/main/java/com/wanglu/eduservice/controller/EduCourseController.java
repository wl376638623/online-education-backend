package com.wanglu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanglu.commonutils.R;
import com.wanglu.eduservice.entity.EduCourse;
import com.wanglu.eduservice.entity.EduTeacher;
import com.wanglu.eduservice.entity.vo.CourseInfoVo;
import com.wanglu.eduservice.entity.vo.CoursePublishVo;
import com.wanglu.eduservice.entity.vo.CourseQuery;
import com.wanglu.eduservice.entity.vo.TeacherQuery;
import com.wanglu.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-05
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //课程列表 基本实现
    //todo 完成条件查询带分页
    @GetMapping
    public R getCourseList() {
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list", list);
    }
    //4 条件查询带分页的方法
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current,@PathVariable long limit,
                                  @RequestBody(required = false) CourseQuery courseQuery){

        //创建一个page对象
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        //构造条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //wrapper多条件组合查询
        //判断条件值是否为空，如果不为空拼接
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        if (!StringUtils.isEmpty(title)) {
            //构建条件
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }

        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询
        courseService.page(pageCourse, wrapper);
        List<EduCourse> records = pageCourse.getRecords();//数据list
        long total = pageCourse.getTotal();//总记录数
        return R.ok().data("total", total).data("rows", records);

    }


    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程ID查询课程的基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo courseInfoVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", courseInfoVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

