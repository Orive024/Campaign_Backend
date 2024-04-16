package com.orive.security.emailsender;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public interface EmailService  {
    //String sendMail(MultipartFile[] file, String to,String[] cc, String subject, String body);
    String sendMail(MultipartFile[] file, List<String> to, List<String> cc, String subject, String body);
}
