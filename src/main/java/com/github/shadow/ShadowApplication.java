package com.github.shadow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ShadowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShadowApplication.class, args);
    }

}
