package com.zte.km.config;

import com.zte.km.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class WebMvcConfigure extends WebMvcConfigurerAdapter{

    /**
     * 必须提前将拦截器放入IOC容器，spring进行管理
     * @return
     */
    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/order/*")
                .addPathPatterns("/user/user_*");
        super.addInterceptors(registry);
    }

}
