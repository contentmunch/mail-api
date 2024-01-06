package com.contentmunch.mail.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "muncher.mail")
public class MailConfig {
    private String clientId;
    private String clientSecret;
    private String refreshToken;
    private String tokenServer;
    private String from;
    private String cc;
    private String backup;
    private String fromName;
}
