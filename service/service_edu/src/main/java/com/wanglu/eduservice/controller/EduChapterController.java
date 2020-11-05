package com.wanglu.eduservice.controller;


import com.wanglu.commonutils.R;
import com.wanglu.eduservice.entity.chapter.ChapterVo;
import com.wanglu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    //返回课程大纲的方法,根据课程ID进行查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list= chapterService.getCharpterVideoByCourseId(courseId);
        return R.ok().data("allChapterVide",list);
    }
}

