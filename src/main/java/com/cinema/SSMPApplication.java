package com.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cinema.*")
public class SSMPApplication {
    public static void main(String[] args) {
        SpringApplication.run(SSMPApplication.class, args);
    }
}