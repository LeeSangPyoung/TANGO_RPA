package com.tagui.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tagui.controller", "com.tagui.service"})
@MapperScan("com.tagui.mapper")  // ✅ MyBatis Mapper 스캔 추가
public class TaguiRpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaguiRpaApplication.class, args);
    }
}
