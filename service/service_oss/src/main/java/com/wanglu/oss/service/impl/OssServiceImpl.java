package com.wanglu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wanglu.oss.service.OssService;
import com.wanglu.oss.utils.ConstandPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    //上传文件到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //地域节点 keyId 秘钥
        String endPoint = ConstandPropertiesUtils.END_POINT;
        String accessKeyId = ConstandPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstandPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketname = ConstandPropertiesUtils.BUCKETNAME;
        //创建OSSclient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        //上传文件流
        try {
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String filename = file.getOriginalFilename();
            //在文件名称里面加入随机唯一值 让其不重复
            String uuid = UUID.randomUUID().toString();
            //去除-
            uuid.replaceAll("-", "");
            filename = uuid + filename;
            //把文件按照日期进行分类
            //获取当前日期用joda-time
            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename = datePath + "/" + filename;
            //https://on-edu.oss-ap-northeast-1.aliyuncs.com/1.jpg
            ossClient.putObject(bucketname, filename,inputStream);
            ossClient.shutdown();
            //上传之后文件的路径返回
            String url = "https://"+bucketname+"."+endPoint+"/"+filename;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
