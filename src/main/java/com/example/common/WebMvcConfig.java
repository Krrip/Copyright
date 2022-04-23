package com.example.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(new AuthInterceptor())
                //拦截
                .addPathPatterns("/api/**")
                .addPathPatterns("/page/end/**")
                //放行

                .excludePathPatterns("/page/end/login.html", "/page/end/register.html")
                .excludePathPatterns("/api/user/login", "/api/user/register");

        registry.addInterceptor(new TwoInterceptor())
                .addPathPatterns("/page/front/work.html")

        ;
    }
}
