package com.corelogic.hazardbot.notification.smsclient;

import com.corelogic.hazardbot.notification.SmsNotificationException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("!mock")
@Component
@Slf4j
public class SmsRestClientImpl implements SmsRestClient {
    private final TwilioRestClient twilioRestClient;
    private final String twilioNumber;

    public SmsRestClientImpl(SmsCredentials smsCredentials) {
        this.twilioRestClient =
                new TwilioRestClient.Builder(
                        smsCredentials.getAccountSid(),
                        smsCredentials.getAuthToken()
                ).build();
        this.twilioNumber = smsCredentials.getPhoneNumber();
    }

    @Override
    public Map<String, String> sendSms(List<String> numbers, String content)throws SmsNotificationException {
        Map<String, String> messages = new HashMap<>();
        for (String number : numbers) {
            MessageCreator messageCreator = new MessageCreator(
                    new PhoneNumber(number),
                    new PhoneNumber(this.twilioNumber),
                    content);
            // messageCreator.setMediaUrl(mediaUrl);
            try {
                messages.put(number,
                        messageCreator.create(this.twilioRestClient).getStatus().toString());


            }catch (Exception e){
                String errorMsg = String.format("An exception occurred trying to send a message to {}, exception: {}",
                        number.toString(), e.getMessage());
                log.error(errorMsg);
                throw new SmsNotificationException(errorMsg, e);
            }
        }
        return messages;
    }
}