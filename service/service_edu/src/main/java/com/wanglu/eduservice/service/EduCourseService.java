package com.wanglu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanglu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanglu.eduservice.entity.frontVo.CourseFrontVo;
import com.wanglu.eduservice.entity.vo.CourseInfoVo;
import com.wanglu.eduservice.entity.vo.CoursePublishVo;
import com.wanglu.eduservice.entity.vo.CourseQuery;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-11-05
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);


    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);
}
