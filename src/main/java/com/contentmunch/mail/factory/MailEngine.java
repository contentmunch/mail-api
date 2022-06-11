package com.contentmunch.mail.factory;

import com.contentmunch.mail.data.MailMessage;

public interface MailEngine {
    void sendMail(MailMessage mailMessage);
}
