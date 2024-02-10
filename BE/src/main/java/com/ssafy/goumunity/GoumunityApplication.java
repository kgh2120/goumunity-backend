package com.ssafy.goumunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class GoumunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoumunityApplication.class, args);
    }
}
