package com.wanglu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanglu.eduservice.entity.EduCourse;
import com.wanglu.eduservice.entity.EduCourseDescription;
import com.wanglu.eduservice.entity.frontVo.CourseFrontVo;
import com.wanglu.eduservice.entity.frontVo.CourseWebVo;
import com.wanglu.eduservice.entity.vo.CourseInfoVo;
import com.wanglu.eduservice.entity.vo.CoursePublishVo;
import com.wanglu.eduservice.entity.vo.CourseQuery;
import com.wanglu.eduservice.mapper.EduCourseMapper;
import com.wanglu.eduservice.service.EduChapterService;
import com.wanglu.eduservice.service.EduCourseDescriptionService;
import com.wanglu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanglu.eduservice.service.EduVideoService;
import com.wanglu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //注入描述
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;


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
    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //根据课程ID珊瑚课程里面的小节
        eduVideoService.removeVideoByCourseId(courseId);
        //根据课程ID删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //根据课程ID删除描述
        eduCourseDescriptionService.removeById(courseId);
        //根据课程ID删除本身
        int result = baseMapper.deleteById(courseId);
        if (result == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }
    //1条件查询带分页查询课程
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空 不为空拼接
        //判断一级id是否存在
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        //判断二级id是否存在
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        //关注度排序
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        //最新排序
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        //根据价格排序
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageCourse, wrapper);
        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious = pageCourse.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
    //根据课程id 编写sql语句查询课程信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
            return baseMapper.getBaseCourseInfo(courseId);
    }
}
