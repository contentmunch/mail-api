package com.contentmunch.mail.factory;

import com.contentmunch.mail.configuration.MailConfig;
import com.contentmunch.mail.engine.GmailEngine;
import com.contentmunch.mail.engine.MailEngine;

public class MailFactory {
    public static MailEngine getDefaultMailEngine(MailConfig mailConfig) {
        return new GmailEngine(mailConfig);
    }
}
