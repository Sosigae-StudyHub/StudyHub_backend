package com.studyhub.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "portone")
public class PortOneProperties {
    private String apiSecret; // ✅ V2에서는 accessToken이 아닌 apiSecret만 필요
}
