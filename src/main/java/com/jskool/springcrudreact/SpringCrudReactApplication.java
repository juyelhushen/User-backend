package com.jskool.springcrudreact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringCrudReactApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCrudReactApplication.class, args);

    }

}
