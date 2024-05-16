package com.f1v3.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "f1v3")
public class AppConfig {

    private Hello hello;

    @Data
    public static class Hello {
        private String name;
        private String home;
        private String hobby;
        private Long age;
    }
}
