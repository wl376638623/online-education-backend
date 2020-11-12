package com.wanglu.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws Exception{

    }

    public static void getPalyAuth() throws Exception{
        //根绝视频id获取视频播放凭证
        //撞见一个初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4FzmTRc2zNukBFTa6pT3", "tqojkrUOXvQ3UtXwKsnIt0pptUZlO4");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("9c8fd6205c054529b5eb71311c9a5c1b");

        response= client.getAcsResponse(request);
        System.out.println("playauth："+response.getPlayAuth());
    }

    public static void getPlayUrl() throws Exception{
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4FzmTRc2zNukBFTa6pT3", "tqojkrUOXvQ3UtXwKsnIt0pptUZlO4");
        //创建获取说地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request中设置id
        request.setVideoId("9c8fd6205c054529b5eb71311c9a5c1b");
        //调用初始化对象里面的方法 床底request 获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println("PlayInfo.PlayURL"+playInfo.getPlayURL()+"\n");
        }
        //base信息
        System.out.println("VideoBase.Title"+response.getVideoBase().getTitle()+"\n");

    }
}
