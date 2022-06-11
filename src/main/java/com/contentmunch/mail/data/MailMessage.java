package com.contentmunch.mail.data;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;


@Data
@SuperBuilder
public class MailMessage {
    public static final String NEWLINE_WITH_TAB = "\n\r";
    private String name;
    private String emailAddress;
    private String to;
    private String cc;
    private String as;
    private String subject;

    private String message;

    private MultipartFile file1;
    private MultipartFile file2;
    private MultipartFile file3;

    @Override
    public String toString() {

        return subject + " by " + name + NEWLINE_WITH_TAB + " email: " + emailAddress + NEWLINE_WITH_TAB + "message";
    }
}
