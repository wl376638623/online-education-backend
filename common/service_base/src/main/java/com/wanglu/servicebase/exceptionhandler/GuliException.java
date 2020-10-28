package com.wanglu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //生产有参数的构造方法
@NoArgsConstructor //生产无参数的构造方法
public class GuliException extends RuntimeException {

    private Integer code;//状态码

    private String msg;//异常信息
}
