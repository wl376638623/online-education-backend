package com.wanglu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {

    private String id;

    private String title;
    //表示章节中可以存放多个小节
    private List<VideoVo> children = new ArrayList<>();
}
