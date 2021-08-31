package com.chandrakumar.ms.api.tracking;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;


@Configuration
public class MsTrackingAutoConfig {

    private static final int ORDER = 1;


    @Bean
    @RequestScope
    public MsRequestContext msRequestContext() {
        return new MsRequestContext();
    }

    @Bean
    public FilterRegistrationBean<MsTrackingFilter> msTrackingFilterRegistrar(
            MsRequestContext msRequestContext
    ) {

        final FilterRegistrationBean<MsTrackingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new MsTrackingFilter(msRequestContext));
        registration.setOrder(ORDER);
        return registration;
    }
}