package com.jeeplus.common.config;

//import com.jeeplus.flowable.interceptor.FlowableHandlerInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Created by jeeplus on 2021/9/28.
 */
@Configuration
public class FlowableInterceptor implements WebMvcConfigurer {

    @Autowired
    private DispatcherServlet dispatcherServlet;


//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new FlowableHandlerInterceptor ())
//                    .addPathPatterns("/app/**");
//    }


    @Bean
    public ServletRegistrationBean apiServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean ( dispatcherServlet );
        //注入上传配置到自己注册的ServletRegistrationBean
        bean.addUrlMappings ( "/service/*" );
        bean.setName ( "ModelRestServlet" );
        return bean;
    }

    @Bean
    public ServletRegistrationBean restServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean ( dispatcherServlet );
        //注入上传配置到自己注册的ServletRegistrationBean
        bean.addUrlMappings ( "/rest/*" );
        bean.setName ( "RestServlet" );
        return bean;
    }


}
