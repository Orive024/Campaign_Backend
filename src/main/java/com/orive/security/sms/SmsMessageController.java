package com.orive.security.sms;

import com.twilio.exception.TwilioException;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
@Tag(name = "sms")
@CrossOrigin(origins = "*")
public class SmsMessageController {

    @Autowired
    private SmsMessageService smsMessageService;

    @PostMapping("/send-sms")
    public ResponseEntity<String> sendSmsMessage(@RequestBody SmsMessage smsMessage) {
        try {
            smsMessageService.sendSms(smsMessage);
            return ResponseEntity.ok("SMS sent successfully");
        } catch (TwilioException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send SMS: " + e.getMessage());
        }
    }
}

