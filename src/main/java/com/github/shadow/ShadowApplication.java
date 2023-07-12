package com.github.shadow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.shadow.mapper")
public class ShadowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShadowApplication.class, args);
    }

}
