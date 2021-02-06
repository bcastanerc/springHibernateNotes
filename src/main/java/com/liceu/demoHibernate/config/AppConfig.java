package com.liceu.demoHibernate.config;

import com.liceu.demoHibernate.interceptors.CheckCsrfToken;
import com.liceu.demoHibernate.interceptors.GenerateCsrfToken;
import com.liceu.demoHibernate.interceptors.LogOutInterceptor;
import com.liceu.demoHibernate.interceptors.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan("com.liceu.demoHibernate")
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    SessionInterceptor sessionInterceptor;

    @Autowired
    GenerateCsrfToken generateCsrfToken;

    @Autowired
    CheckCsrfToken checkCsrfToken;

    @Autowired
    LogOutInterceptor logOutInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns(getInterceptorMapping());
        registry.addInterceptor(generateCsrfToken).addPathPatterns("/*");
        registry.addInterceptor(checkCsrfToken).addPathPatterns("/*");
        registry.addInterceptor(logOutInterceptor).addPathPatterns("/*");
    }

    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Bean
    public List<String> getInterceptorMapping(){
        List<String> interceptorMapingList = new ArrayList<>();
        interceptorMapingList.add("/viewNote");
        interceptorMapingList.add("/updateNote");
        interceptorMapingList.add("/userNotes");
        interceptorMapingList.add("/userInfo");
        interceptorMapingList.add("/createNotes");
        return interceptorMapingList;
    }


}