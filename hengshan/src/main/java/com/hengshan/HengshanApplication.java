package com.hengshan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hengshan.mapper")
public class HengshanApplication {

    public static void main(String[] args) {
        SpringApplication.run(HengshanApplication.class, args);
    }

}
