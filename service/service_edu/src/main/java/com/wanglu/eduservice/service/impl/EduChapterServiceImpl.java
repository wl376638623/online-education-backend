package com.wanglu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanglu.eduservice.entity.EduChapter;
import com.wanglu.eduservice.entity.EduVideo;
import com.wanglu.eduservice.entity.chapter.ChapterVo;
import com.wanglu.eduservice.entity.chapter.VideoVo;
import com.wanglu.eduservice.mapper.EduChapterMapper;
import com.wanglu.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanglu.eduservice.service.EduVideoService;
import com.wanglu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-05
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;
    @Override
    public List<ChapterVo> getCharpterVideoByCourseId(String courseId) {
        //1根据课程id查询出所有课程中的章节
        //创建mp的条件构造器
        QueryWrapper<EduChapter> ChapterWrapper = new QueryWrapper<>();
        ChapterWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(ChapterWrapper);
        //2根据课程id查询小节
        QueryWrapper<EduVideo> VideoWrapper = new QueryWrapper<>();
        VideoWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(VideoWrapper);
        //创建一个list集合封装最终的数据
        List<ChapterVo> finalList = new ArrayList<>();
        //3遍历查询章节list集合进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //eduChapter对象值复制到ChapterVo中
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把ChapterVo放到finalList中
            finalList.add(chapterVo);
            //4遍历查询小节list集合,进行封装
            //创建集合封装章节中的小节
            List<VideoVo> videoList = new ArrayList<>();
            for (int m = 0; m < eduVideoList.size(); m++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoList.add(videoVo);
                }
            }
            //吧封装之后的小节list集合放到章节对象中
            chapterVo.setChildren(videoList);

        }
        return finalList;
    }
    //删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据章节id查询小节表 查询出数据 不删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);
        //判断
        if (count > 0) {
            //能查询出不进行删除
            throw new GuliException(20001, "有小节数据不能删除");
        } else {
            //没查出数据进行删除
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }

    }
}
