package com.f1v3.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class F1v3Application {

    public static void main(String[] args) {
        SpringApplication.run(F1v3Application.class, args);
    }

}
