package com.yuan.configure;

import com.yuan.interceptor.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  注册拦截器
 */
@Configuration
public class WebConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor()).addPathPatterns("/**")
                .excludePathPatterns("/","/user/login","/user/update");
    }

    @Bean
    public Interceptor interceptor(){
            return new Interceptor();
    }
}
