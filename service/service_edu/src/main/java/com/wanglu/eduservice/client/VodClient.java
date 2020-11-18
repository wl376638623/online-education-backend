package com.wanglu.eduservice.client;

import com.wanglu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("service-vod") //调用的服务器名字
public interface VodClient {
    //定义调用方法的路径
    //根据视频id删除阿里云视频
    @DeleteMapping("eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    //删除多个阿里云视频的方法
    //参数时多个视频id
    @DeleteMapping("eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List videoIdList);
}
