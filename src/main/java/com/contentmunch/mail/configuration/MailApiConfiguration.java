package com.contentmunch.mail.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackageClasses = {MailConfig.class})
@Configuration
public class MailApiConfiguration {
}
