package com.contentmunch.mail.engine;

import com.contentmunch.mail.data.MailMessage;

public interface MailEngine {
    void sendMail(MailMessage mailMessage);
}
