package com.zte.km.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2018/7/1.
 */
//数据源的配置
@Configuration
@PropertySource("classpath:datasource.properties")
@MapperScan("com.zte.km.dao")
public class DataSourceConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource(
            @Value(value = "${spring.datasource.url}") String url,
            @Value(value = "${spring.datasource.driver-class-name}") String driverClassName,
            @Value(value = "${spring.datasource.username}") String username,
            @Value(value = "${spring.datasource.data-password}") String password){
        StandardPBEStringEncryptor encryptor=new StandardPBEStringEncryptor();
        encryptor.setPassword(environment.getProperty("jasypt.encryptor.password"));
        Properties properties=new Properties();
        properties.setProperty("url",url);
        properties.setProperty("driverClassName",driverClassName);
        properties.setProperty("username",encryptor.decrypt(username));
        properties.setProperty("password",encryptor.decrypt(password));
        this.setCommonJDBCProperty(properties);
        try {
            return DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //加载所有的mapper.XML配置文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*.xml"));
        try {
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //配置事务管理器
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    //配置数据库连接池的通用属性
    public void setCommonJDBCProperty(Properties properties) {
        properties.setProperty("initialSize",environment.getProperty("jdbc.initialSize"));
        properties.setProperty("minIdle",environment.getProperty("jdbc.minIdle"));
        properties.setProperty("maxActive",environment.getProperty("jdbc.maxActive"));
        properties.setProperty("maxWait",environment.getProperty("jdbc.maxWait"));

        properties.setProperty("timeBetweenEvictionRunsMillis",environment.getProperty("jdbc.timeBetweenEvictionRunsMillis"));
        properties.setProperty("minEvictableIdleTimeMillis",environment.getProperty("jdbc.minEvictableIdleTimeMillis"));

        properties.setProperty("validationQuery",environment.getProperty("jdbc.validationQuery"));
        properties.setProperty("testWhileIdle",environment.getProperty("jdbc.testWhileIdle"));
        properties.setProperty("testOnBorrow",environment.getProperty("jdbc.testOnBorrow"));
        properties.setProperty("testOnReturn",environment.getProperty("jdbc.testOnReturn"));

        properties.setProperty("poolPreparedStatements",environment.getProperty("jdbc.poolPreparedStatements"));
        properties.setProperty("maxPoolPreparedStatementPerConnectionSize",environment.getProperty("jdbc.maxPoolPreparedStatementPerConnectionSize"));
        properties.setProperty("filters",environment.getProperty("jdbc.filters"));
    }
}
