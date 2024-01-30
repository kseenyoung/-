package com.ssafy.backend.common.config;

//import com.ssafy.backend.common.utils.CookieAttributerFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:5173")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true);
    }

//    @Bean
//    public FilterRegistrationBean filterBean() {
//
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new CookieAttributerFilter());
//        registrationBean.setOrder(Integer.MIN_VALUE); //필터 여러개 적용 시 순번
////        registrationBean.addUrlPatterns("/*"); //전체 URL 포함
////        registrationBean.addUrlPatterns("/test/*"); //특정 URL 포함
////        registrationBean.setUrlPatterns(Arrays.asList(INCLUDE_PATHS)); //여러 특정 URL 포함
//        registrationBean.setUrlPatterns(Arrays.asList("/dagak/*", "/*"));
//
//        return registrationBean;
//    }


}
