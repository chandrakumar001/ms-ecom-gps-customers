package com.chandrakumar.ms.api.interceptor.config;

import com.chandrakumar.ms.api.interceptor.HttpHeaderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebAppConfigAdapter implements WebMvcConfigurer {

    @Autowired
    HttpHeaderInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        //adding custom interceptor
        interceptorRegistry.addInterceptor(interceptor);
    }
}