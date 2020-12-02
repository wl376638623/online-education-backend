package com.wanglu.vod.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils implements InitializingBean {

    @Value("LTAI4FzmTRc2zNukBFTa6pT3")
    private String keyid;

    @Value("tqojkrUOXvQ3UtXwKsnIt0pptUZlO4")
    private String keysecret;

    public static String ACCESS_KEY_SECRET;
    public static String ACCESS_KEY_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
    }
}
