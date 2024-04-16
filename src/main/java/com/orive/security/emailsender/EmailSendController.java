package com.orive.security.emailsender;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/mail")
@Tag(name = "Email")
@CrossOrigin(origins = "*")
public class EmailSendController {
    private EmailService emailService;

    public EmailSendController(EmailService emailService) {
        this.emailService = emailService;
    }

//    @PostMapping(value = "/send", consumes = "multipart/form-data")
//    public String sendMail(@RequestParam(value = "file", required = false) MultipartFile[] file, String to, String[] cc, String subject, String body) {
//        return emailService.sendMail(file, to, cc, subject, body);
//    }
    
    @PostMapping(value = "/send", consumes = "multipart/form-data")
    public String sendMail(@RequestParam(value = "file", required = false) MultipartFile[] file,
                           @RequestParam(value = "to") List<String> to,
                           @RequestParam(value = "cc", required = false) List<String> cc,
                           @RequestParam(value = "subject") String subject,
                           @RequestParam(value = "body") String body) {
        return emailService.sendMail(file, to, cc, subject, body);
    }

}
