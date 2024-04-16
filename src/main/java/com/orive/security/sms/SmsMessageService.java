package com.orive.security.sms;

import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsMessageService {

	@Value("${twilio.account.sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;

    @Value("${twilio.from.number}")
    private String TWILIO_PHONE_NUMBER;

    public void sendSms(SmsMessage smsMessage) throws TwilioException {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(smsMessage.getToPhoneNumber()), // to
                new PhoneNumber(TWILIO_PHONE_NUMBER), // from
                smsMessage.getMessageBody())
                .create();
        smsMessage.setStatus(message.getStatus().toString());
    }
}

