package com.onwelo.practice.bts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BtsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BtsApplication.class, args);
    }
}