package com.company.proxyproject.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "api.info")
public class OpenApiProperties {
    private String title;
    private String version;
    private String description;
    private String contactName;
    private String contactEmail;
    private String contactUrl;
}
