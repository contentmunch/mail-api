package com.contentmunch.mail.engine;

import com.contentmunch.mail.configuration.MailConfig;
import com.contentmunch.mail.data.MailMessage;
import com.contentmunch.mail.exception.MailException;
import com.contentmunch.mail.utils.FileUtils;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;
import java.util.Properties;

import static com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport;
import static com.google.common.io.BaseEncoding.base64;
import static javax.mail.Session.getDefaultInstance;


public class GmailEngine implements MailEngine {
    private final MailConfig mailConfig;
    private final Gmail gmail;

    public GmailEngine(MailConfig mailConfig) {
        this.mailConfig = mailConfig;
        this.gmail = buildGmail(mailConfig.getClientId(), mailConfig.getClientSecret(), mailConfig.getRefreshToken());
    }

    private Gmail buildGmail(String clientId, String clientSecret, String refreshToken) {
        TokenResponse response = new TokenResponse();
        response.setRefreshToken(refreshToken);
        try {
            return new Gmail.Builder(newTrustedTransport(), GsonFactory.getDefaultInstance(),
                    new Credential.Builder(BearerToken.authorizationHeaderAccessMethod()).setTransport(
                                    newTrustedTransport())
                            .setJsonFactory(GsonFactory.getDefaultInstance())
                            .setTokenServerUrl(
                                    new GenericUrl(mailConfig.getTokenServer()))
                            .setClientAuthentication(new BasicAuthentication(
                                    clientId, clientSecret))
                            .build()
                            .setFromTokenResponse(response))
                    .setApplicationName("muncher-mail-api")
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            throw new MailException("Error creating Gmail Object", e);
        }
    }

    private Message createMessageWithEmail(final MimeMessage emailContent) throws MessagingException, IOException {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        final byte[] bytes = buffer.toByteArray();
        final String encodedEmail = base64().encode(bytes);
        final Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    private MimeMessage from(MailMessage message) throws MessagingException, IOException {
        final MimeMessage email = new MimeMessage(getDefaultInstance(new Properties(), null));
        MimeMessageHelper helper = new MimeMessageHelper(email, message.getFile1() != null);
        helper.setTo(message.getTo());

        if (message.getCc() != null || mailConfig.getCc() != null)
            helper.setCc(message.getCc() == null ? mailConfig.getCc() : message.getCc());

        helper.setFrom(mailConfig.getFrom());
        helper.setSubject(message.getSubject());
        helper.setText(message.getMessage());

        if (message.getReplyTo() != null)
            helper.setReplyTo(message.getReplyTo());

        if (message.getFile1() != null) {
            helper.addAttachment(Objects.requireNonNull(message.getFile1().getOriginalFilename()), FileUtils.from(message.getFile1()));
        }
        if (message.getFile2() != null) {
            helper.addAttachment(Objects.requireNonNull(message.getFile2().getOriginalFilename()), FileUtils.from(message.getFile2()));
        }
        if (message.getFile3() != null) {
            helper.addAttachment(Objects.requireNonNull(message.getFile3().getOriginalFilename()), FileUtils.from(message.getFile3()));
        }
        return helper.getMimeMessage();
    }

    @Override
    public void sendMail(MailMessage mailMessage) {
        try {
            gmail.users().messages().send("me", createMessageWithEmail(from(mailMessage))
            ).execute();
        } catch (IOException | MessagingException ex) {
            throw new MailException("Error Sending Email.", ex);
        }
    }
}
