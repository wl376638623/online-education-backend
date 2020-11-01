package com.wanglu.eduservice.controller;


import com.wanglu.commonutils.R;
import com.wanglu.eduservice.entity.EduTeacher;
import com.wanglu.eduservice.entity.vo.TeacherQuery;
import com.wanglu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanglu.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author wanglu
 * @since 2020-02-24
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    //访问地址： http://localhost:8001/eduservice/teacher/findAll
    //把service注入
    @Autowired
    private EduTeacherService teacherService;

    //1 查询讲师表所有数据
    //rest风格
    @ApiOperation(value = "查询所有讲师")
    @GetMapping("findAll")
    public R findAllTeacher(){
        //调用service方法
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    //2 逻辑删除讲师的方
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true)@PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //3 分页查询讲师的方法
    //current 当前页
    //limit 每页记录数
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable Long current,
                             @PathVariable Long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        try {
            int i = 10 / 0;
        } catch (Exception e) {
            throw new GuliException(20001, "执行了自定义异常处理");
        }
        //调用方法实现分页
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合
//        Map map = new HashMap<>();
//        map.put("total", total);
//        map.put("records", records);
        return R.ok().data("total",total).data("rows",records);
    }

    //4 条件查询带分页的方法
   @PostMapping("pageTeacherCondition/{current}/{limit}")
   public R pageTeacherCondition(@PathVariable long current,@PathVariable long limit,
                                 @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建一个page对象
       Page<EduTeacher> pageTeacher = new Page<>(current, limit);
       //构造条件
       QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
       //wrapper多条件组合查询
       //判断条件值是否为空，如果不为空拼接
       String name = teacherQuery.getName();
       Integer level = teacherQuery.getLevel();
       String begin = teacherQuery.getBegin();
       String end = teacherQuery.getEnd();
       if (!StringUtils.isEmpty(name)) {
           //构建条件
           wrapper.like("name", name);
       }
       if (!StringUtils.isEmpty(level)) {
           wrapper.eq("level", level);
       }
       if (!StringUtils.isEmpty(begin)) {
           wrapper.ge("gmt_create", begin);
       }
       if (!StringUtils.isEmpty(end)) {
           wrapper.le("gmt_modified", end);
       }
       //排序
       wrapper.orderByDesc("gmt_create");
       //调用方法实现条件查询
       teacherService.page(pageTeacher, wrapper);
       List<EduTeacher> records = pageTeacher.getRecords();//数据list
       long total = pageTeacher.getTotal();//总记录数
       return R.ok().data("total", total).data("rows", records);

   }


    //添加讲师接口的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
//        if (save) {
////            return R.ok();
////        } else {
////            return R.error();
////        }
        return save ? R.ok() : R.error();
    }

    //根据讲师id进行查询
   @GetMapping("getTeacher/{id}")
   public R getTeacher(@PathVariable String id){
       EduTeacher eduTeacher = teacherService.getById(id);
       return R.ok().data("teacher", eduTeacher);
   }

    //讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

