package com.wanglu.eduservice.controller;


import com.wanglu.commonutils.R;
import com.wanglu.eduservice.client.VodClient;
import com.wanglu.eduservice.entity.EduVideo;
import com.wanglu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-05
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video) {
        eduVideoService.save(video);
        System.out.println(video);
        return R.ok();
    }

    //删除小节 TODO 删除小节时候删除后面的视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        //根据小节id得到视频id
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            //根据视频id 远程调用实现视频删除
            vodClient.removeAlyVideo(videoSourceId);
        }
        //删除小节
        eduVideoService.removeById(id);
        return R.ok();
    }

    //按照Id查询小节
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId) {
        EduVideo video = eduVideoService.getById(videoId);
        return R.ok().data("video", video);
    }
    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo video) {
        eduVideoService.updateById(video);
        return R.ok();
    }
}

