package com.wanglu.eduservice.service;

import com.wanglu.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanglu.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-11-05
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getCharpterVideoByCourseId(String courseId);
}
