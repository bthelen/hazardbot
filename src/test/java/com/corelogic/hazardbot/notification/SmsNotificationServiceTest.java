package com.corelogic.hazardbot.notification;

import com.corelogic.hazardbot.Event;
import com.corelogic.hazardbot.notification.smsclient.SmsRestClientImpl;
import com.corelogic.hazardbot.persistence.SubscriberLookupService;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class SmsNotificationServiceTest {
    @Test
    public void notifySubscribers() throws Exception {
        SmsRestClientImpl mocksmsRestClientImpl = mock(SmsRestClientImpl.class);
        SubscriberLookupService mockSubscriberLookupService;
        mockSubscriberLookupService = mock(SubscriberLookupService.class);
        final List<String> numbers = Arrays.asList(
                "123456789",
                "234567894",
                "789456123"
        );
        SmsNotificationService subject = new SmsNotificationService(
                mocksmsRestClientImpl,
                mockSubscriberLookupService
        );
        when(mockSubscriberLookupService.getSubscribers()).thenReturn(numbers);

        Event event = new Event("Something happened!");
        subject.notifySubscribers(event);
        verify(mockSubscriberLookupService).getSubscribers();
        verify(mocksmsRestClientImpl).sendSms(numbers, event.getContent());
    }
}