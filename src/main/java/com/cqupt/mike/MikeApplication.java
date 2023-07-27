package com.cqupt.mike;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cqupt.mike.dao")
public class MikeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MikeApplication.class, args);
    }

}
