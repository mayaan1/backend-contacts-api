package com.flock.spring_test.config;

import com.flock.spring_test.interseptor.RequestHandleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
@Configuration
public class AuthenticationConfig implements WebMvcConfigurer {

    @Autowired
    private RequestHandleInterceptor interceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).excludePathPatterns(
                "/auth/**"
        ).pathMatcher(new AntPathMatcher());
    }
}
