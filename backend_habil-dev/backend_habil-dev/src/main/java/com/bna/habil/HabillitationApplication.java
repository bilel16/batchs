package com.bna.habil;


import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.EnableScheduling;

@AutoConfiguration
@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.bna.habil")
public class HabillitationApplication {

    public static void main(String[] args) {
        SpringApplication.run(HabillitationApplication.class, args);
    }

    @PostConstruct
    public void init() {

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));

    }

}
