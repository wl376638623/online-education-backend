package com.wanglu.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanglu.commonutils.R;
import com.wanglu.eduservice.entity.EduCourse;
import com.wanglu.eduservice.entity.EduTeacher;
import com.wanglu.eduservice.entity.chapter.ChapterVo;
import com.wanglu.eduservice.entity.frontVo.CourseFrontVo;
import com.wanglu.eduservice.entity.frontVo.CourseWebVo;
import com.wanglu.eduservice.service.EduChapterService;
import com.wanglu.eduservice.service.EduCourseService;
import com.wanglu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CoruseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    //1条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);

        return R.ok().data(map);
    }

    //课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节小节
        List<ChapterVo> chapterVideoList = chapterService.getCharpterVideoByCourseId(courseId);
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }
}
