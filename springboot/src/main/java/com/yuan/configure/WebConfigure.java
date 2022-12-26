package com.yuan.configure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.interceptor.Interceptor;
import com.yuan.utils.GlobalFormDateConvert;
import com.yuan.utils.GlobalJsonDateConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  配置类
 *
 */
@Configuration
public class WebConfigure implements WebMvcConfigurer {

    /*
    拦截器
    * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor()).addPathPatterns("/**")
                .excludePathPatterns("/", "/user/login", "/user/forgetPassword");
    }

    @Bean
    public Interceptor interceptor() {
        return new Interceptor();
    }

   /* //JSON格式 全局日期转换器配置
    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //设置日期格式
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(GlobalJsonDateConvert.instance);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        //设置中文编码格式
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        return mappingJackson2HttpMessageConverter;
    }

    //表单格式 全局日期转换器
    @Bean
    @Autowired
    public ConversionService getConversionService(GlobalFormDateConvert globalDateConvert) {
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(globalDateConvert);
        factoryBean.setConverters(converters);
        return factoryBean.getObject();
    }*/

    @Value("${Location.realPath}")
    private String realPath;
    @Value("${Location.mapperPath}")
    private String mapperPath;

    // 图片映射类
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(mapperPath + "**").addResourceLocations("file:" + realPath);
    }

}