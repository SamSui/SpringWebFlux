package com.example.dbdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages="com.example.dbdemo")
@ConfigurationPropertiesScan(basePackages = "com.example.dbdemo")
public class DbdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbdemoApplication.class, args);
    }

}
