package com.wanglu.eduservice.entity.vo;

import lombok.Data;

@Data
public class CoursePublishVo {
    private String id;

    private String title;

    private String cover;

    private Integer lessonNum;

    private String SubjectLevelOne;

    private String SubjectLevelTwo;

    private String teacherName;

    private String price;
}
