package com.f1v3.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "f1v3")
public class AppConfig {

    private String jwtKey;
}
