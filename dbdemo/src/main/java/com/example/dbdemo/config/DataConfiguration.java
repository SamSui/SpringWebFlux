package com.example.dbdemo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan( "com.example.dbdemo.mapper" )
@Import( DataSourceConfiguration.class )
public class DataConfiguration implements TransactionManagementConfigurer {

    @Autowired
    DataSource dataSource;
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(ApplicationContext context) throws Exception {
        SqlSessionFactoryBean sf = new SqlSessionFactoryBean();
        sf.setDataSource(dataSource);
        sf.setMapperLocations(context.getResources("classpath*:config/mappers/**/*.xml"));
        sf.setConfigLocation(context.getResource("classpath:config/mybatis.xml"));
        return sf.getObject();
    }

}