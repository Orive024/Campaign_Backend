package com.orive.security.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailOtpService {

  private final JavaMailSender javaMailSender;

  @Value("${otp.length}")
  private int otpLength; // You can configure the OTP length in your application.properties or application.yml

  public EmailOtpService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  public void sendOtpToEmail(String email, String otpCode) {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(email);
    msg.setSubject("Your OTP for Login");
    msg.setText("Your OTP is: " + otpCode);
    javaMailSender.send(msg);
  }

  // Method to generate random OTP of specified length
  public String generateRandomOtp() {
    String numbers = "0123456789";
    StringBuilder otp = new StringBuilder(otpLength);
    Random random = new Random();
    for (int i = 0; i < otpLength; i++) {
      otp.append(numbers.charAt(random.nextInt(numbers.length())));
    }
    return otp.toString();
  }

  // Method to generate and send OTP
  public String generateAndSendOtp(String email) {
    String otpCode = generateRandomOtp();
    sendOtpToEmail(email, otpCode);
    return otpCode;
  }
}