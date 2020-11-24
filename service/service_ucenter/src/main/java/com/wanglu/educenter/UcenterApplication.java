package com.wanglu.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.wanglu"})
@SpringBootApplication
@MapperScan("com.wanglu.educenter.mapper")
public class UcenterApplication {
}
