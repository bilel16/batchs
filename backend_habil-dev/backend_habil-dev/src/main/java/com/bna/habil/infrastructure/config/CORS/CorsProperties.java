package com.bna.habil.infrastructure.config.CORS;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "cors")
@Getter
@Setter
public class CorsProperties {
    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

}
