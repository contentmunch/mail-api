package com.contentmunch.mail.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static java.util.Objects.requireNonNull;

@Slf4j
public final class FileUtils {
    public static File from(MultipartFile multipartFile) throws IOException {
        File filePath = new File(requireNonNull(multipartFile.getOriginalFilename()));
        try (OutputStream os = new FileOutputStream(filePath)) {
            os.write(multipartFile.getBytes());
        }
        return filePath;
    }

    public static void deleteTempFile(MultipartFile multipartFile) {
        File filePath = new File(requireNonNull(multipartFile.getOriginalFilename()));
        if (filePath.delete()) {
            log.debug(multipartFile.getOriginalFilename() + " deleted.");
        }
    }
}
