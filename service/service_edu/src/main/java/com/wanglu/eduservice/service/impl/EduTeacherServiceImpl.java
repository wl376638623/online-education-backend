package com.wanglu.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanglu.eduservice.entity.EduTeacher;
import com.wanglu.eduservice.mapper.EduTeacherMapper;
import com.wanglu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-02-24
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    //分页查询讲述方法
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage) {
        return null;
    }
}
