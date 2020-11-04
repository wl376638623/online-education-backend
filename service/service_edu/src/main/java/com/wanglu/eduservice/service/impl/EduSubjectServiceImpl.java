package com.wanglu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanglu.eduservice.entity.EduSubject;
import com.wanglu.eduservice.entity.excel.SubjectData;
import com.wanglu.eduservice.entity.subject.OneSubject;
import com.wanglu.eduservice.listener.SubjectExcelListener;
import com.wanglu.eduservice.mapper.EduSubjectMapper;
import com.wanglu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author wanglu
 * @since 2020-11-03
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
    }
    //课程分类列表(树形)
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1.查询所有一级分类
        QueryWrapper<EduSubject> WrapperOne = new QueryWrapper<>();
        WrapperOne.eq("parent_id", "0");
        //service中调用mapper
        List<EduSubject> oneSubjectList = baseMapper.selectList(WrapperOne);
        //2.查询所有二级分类
        QueryWrapper<EduSubject> WrapperTwo = new QueryWrapper<>();
        WrapperTwo.ne("parent_id", "0");
        //service中调用mapper
        List<EduSubject> twoSubjectList = baseMapper.selectList(WrapperTwo);
        //创建list集合，用于存储最终封装的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //3.封装一级分类
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //把eduSubject里面的值获取出来,放到OneSubject里面
            //多个OneSubject放到finalSubjectList里面
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle());
            finalSubjectList.add(oneSubject);
        }

        //4.封装二级分类
        return null;
    }
}
