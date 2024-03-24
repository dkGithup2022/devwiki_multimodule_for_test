package com.devwiki.project.app.web.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-mysql, application-webapi");
        SpringApplication.run(ApiApplication.class, args);
    }
}