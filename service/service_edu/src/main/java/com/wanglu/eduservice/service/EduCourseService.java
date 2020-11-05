package com.wanglu.eduservice.service;

import com.wanglu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanglu.eduservice.entity.vo.CourseInfoVo;

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
}
