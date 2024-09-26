package com.example.foodieweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.example"})
@SpringBootApplication
public class FoodieWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(com.example.foodieweb.FoodieWebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(com.example.foodieweb.FoodieWebApplication.class);
    }

}
