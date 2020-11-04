package com.wanglu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanglu.eduservice.entity.EduSubject;
import com.wanglu.eduservice.entity.excel.SubjectData;
import com.wanglu.eduservice.service.EduSubjectService;
import com.wanglu.servicebase.exceptionhandler.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    //不能实现数据库操作
    public EduSubjectService subjectService;
    public SubjectExcelListener(){}
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if (data == null) {
            throw new GuliException(20001, "文件数据为空");
        }
        //一行一行读取,每次读取有两个值,第一个值是一级分类,第二值是二级分类
        EduSubject existOneSubject = this.existOneSubject(subjectService, data.getOneSubjectName());
        //添加一级分类是否重复
        if (existOneSubject == null) {//没有相同的一级分类进行添加
            EduSubject eduOneSubject = new EduSubject();
            eduOneSubject.setParentId("0");
            eduOneSubject.setTitle(data.getOneSubjectName());
            subjectService.save(eduOneSubject);
        }
        String pid = existOneSubject.getId();
        //添加二级分类是否重复
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, data.getTwoSubjectName(), pid);
        if (existTwoSubject == null) {//没有相同的二级分类进行添加
            EduSubject eduTwoSubject = new EduSubject();
            eduTwoSubject.setParentId(pid);
            eduTwoSubject.setTitle(data.getTwoSubjectName());//二级分类名称
            subjectService.save(eduTwoSubject);
        }
    }
    //判断一级分类不能重复添加再判断二级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper  = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }
    //判断二级分类
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper  = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
