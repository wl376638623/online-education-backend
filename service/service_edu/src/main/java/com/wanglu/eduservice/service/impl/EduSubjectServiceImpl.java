package com.wanglu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.wanglu.eduservice.entity.EduSubject;
import com.wanglu.eduservice.entity.excel.SubjectData;
import com.wanglu.eduservice.listener.SubjectExcelListener;
import com.wanglu.eduservice.mapper.EduSubjectMapper;
import com.wanglu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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
}
