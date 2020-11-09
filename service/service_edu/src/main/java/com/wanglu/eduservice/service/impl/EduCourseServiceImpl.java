package com.wanglu.eduservice.service.impl;

import com.wanglu.eduservice.entity.EduCourse;
import com.wanglu.eduservice.entity.EduCourseDescription;
import com.wanglu.eduservice.entity.vo.CourseInfoVo;
import com.wanglu.eduservice.entity.vo.CoursePublishVo;
import com.wanglu.eduservice.mapper.EduCourseMapper;
import com.wanglu.eduservice.service.EduCourseDescriptionService;
import com.wanglu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanglu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-05
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表里面添加课程基本信息
        //CourseInfoVo对象转换eduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            //添加失败
            throw new GuliException(20001, "添加课程失败");
        }
        //获取添加之后的课程ID
        String id = eduCourse.getId();
        //向课程简介表里面添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(id);
        eduCourseDescriptionService.save(eduCourseDescription);
        return id;
    }
    //根据课程id查询课程信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //查询描述表
        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(description.getDescription());
        return courseInfoVo;
    }
    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1先去修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }
        //2修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);
        if (!b) {
            throw new GuliException(20001,"修改课程描述失败");
        }
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        return baseMapper.getPublishCourseInfo(id);
    }
}
