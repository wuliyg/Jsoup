package com.wulis.jsoup.common.webInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfigurer implements WebMvcConfigurer {

    /**
     * 自己定义的拦截器类
     *
     * @return
     */
    AssetInterceptor assetInterceptor() {
        return new AssetInterceptor();
    }

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(assetInterceptor()).addPathPatterns("/**");
    }

}