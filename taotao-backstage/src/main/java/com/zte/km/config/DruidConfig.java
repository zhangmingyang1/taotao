package com.zte.km.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by Administrator on 2018/7/2.
 */
@Configuration
public class DruidConfig {

    //配置检测数据源和SQL语句的servlet
    @Bean
    public ServletRegistrationBean servletRegistrationBean(Environment environment){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        // http://localhost:8080/druid/login.html
        servletRegistrationBean.addUrlMappings("/druid/*");
        // 设置白名单--允许访问的IP地址
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // 设置黑名单--不允许访问的IP地址，一个IP同时在白黑名单时，不允许访问
        servletRegistrationBean.addInitParameter("deny", "192.168.0.1");
        // 设置登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        return servletRegistrationBean;
    }

    //配置拦截器，拦截记录资源
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions",
                "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;

    }
}
