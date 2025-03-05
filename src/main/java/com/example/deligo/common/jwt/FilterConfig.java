package com.example.deligo.common.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns("/orders/*");
        registrationBean.addUrlPatterns("/reviews/*");
        registrationBean.addUrlPatterns("/stores/*");
        registrationBean.addUrlPatterns("/menus/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}