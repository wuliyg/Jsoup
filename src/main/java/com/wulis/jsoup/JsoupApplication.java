package com.wulis.jsoup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.wulis.jsoup.*.dao")//将项目中对应的mapper类的路径
public class JsoupApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsoupApplication.class, args);
    }

}

