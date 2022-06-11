package com.contentmunch.mail.annotation;

import com.contentmunch.mail.configuration.MailApiConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MailApiConfiguration.class)
public @interface EnableMailApi {
}
