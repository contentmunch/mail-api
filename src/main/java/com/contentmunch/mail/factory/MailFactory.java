package com.contentmunch.mail.factory;

import com.contentmunch.mail.configuration.MailConfig;

public class MailFactory {
    public MailEngine getDefaultMailFactory(MailConfig mailConfig) {
        return new GmailEngine(mailConfig);
    }
}
