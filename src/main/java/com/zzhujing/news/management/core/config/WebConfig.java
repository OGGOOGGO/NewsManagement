package com.zzhujing.news.management.core.config;

import com.zzhujing.news.management.core.interceptor.AuthInterceptor;
import com.zzhujing.news.management.core.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @auther hujing
 * @date Create in 2020/6/2
 * Copyright 本内容仅限于杭州大希地有限公司内部传阅，禁止外泄以及用于其他的商业目的
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/login", "/news/list/**");
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**").excludePathPatterns("/login", "/news/list/**");
    }
}
