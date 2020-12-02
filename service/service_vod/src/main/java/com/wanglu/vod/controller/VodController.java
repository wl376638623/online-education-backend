package com.wanglu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.wanglu.commonutils.R;
import com.wanglu.servicebase.exceptionhandler.GuliException;
import com.wanglu.vod.Utils.ConstantVodUtils;
import com.wanglu.vod.Utils.InitVodClient;
import com.wanglu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.wanglu.vod.Utils.InitVodClient.initVodClient;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;


    //上传视频到阿里云
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId", videoId);
    }

    //根据视频id删除视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        try {
            //初始化对象
            DefaultAcsClient client = initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建一个删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //想request里面设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    //删除多个阿里云视频的方法
    //参数时多个视频id
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);

        return R.ok();
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            DefaultAcsClient client =
                    initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request里面设置视频id
            request.setVideoId(id);
            //调用方法获得凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new GuliException(20001, "获取凭证失败");
        }
    }

    //获取视频播放地址
    @GetMapping("getPlayInfo/{id}")
    public R getPlayInfo(@PathVariable String id) {
        String playInfoUrl = "";
        DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request对象里面设置视频id
        request.setVideoId(id);

        //调用初始化对象里面的方法，传递request，获取数据
        try {
            response = client.getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                playInfoUrl = playInfo.getPlayURL();
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return R.ok().data("playInfo", playInfoUrl);

    }


}
