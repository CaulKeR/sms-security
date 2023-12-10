package com.telecom.antiphishing.service.impl;

import com.telecom.antiphishing.enums.SubscriptionCommand;
import com.telecom.antiphishing.model.Sms;
import com.telecom.antiphishing.service.MessageCheckerService;
import com.telecom.antiphishing.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SmsProcessorServiceImplTest {

    @Mock
    private SubscriptionService subscriptionService;
    @Mock
    private MessageCheckerService messageCheckerService;
    @InjectMocks
    private SmsProcessorServiceImpl smsProcessorService;

    private Sms sms;

    @Value("${app.system-phone}")
    private String systemIdentification;

    @BeforeEach
    public void init() {
        sms = new Sms("sender", "recipient", "message");
        ReflectionTestUtils.setField(smsProcessorService, "systemIdentification", "systemId");
    }

    @Test
    public void processSmsWhenMessageToSystemThenReturnSubscriptionResult() {
        sms.setMessage(SubscriptionCommand.START.name());
        sms.setRecipient("systemId");
        when(subscriptionService.manageSubscription(sms)).thenReturn("Subscription result");

        String result = smsProcessorService.processSms(sms);

        assertEquals("Subscription result", result);
        verify(subscriptionService, times(1)).manageSubscription(sms);
    }

    @Test
    public void processSmsWhenMessageToPersonAndSubscribedThenReturnValidMessage() {
        when(subscriptionService.isUserSubscribed(sms.getRecipient())).thenReturn(true);
        doNothing().when(messageCheckerService).validateMessageForPhishingLinks(sms.getSender(), sms.getMessage());

        String result = smsProcessorService.processSms(sms);

        assertEquals("Message is valid and can be send", result);
        verify(subscriptionService, times(1)).isUserSubscribed(sms.getRecipient());
        verify(messageCheckerService, times(1)).validateMessageForPhishingLinks(sms.getSender(), sms.getMessage());
    }

    @Test
    public void processSmsWhenMessageToPersonAndNotSubscribedThenReturnNoValidation() {
        when(subscriptionService.isUserSubscribed(sms.getRecipient())).thenReturn(false);

        String result = smsProcessorService.processSms(sms);

        assertEquals("Validation wasn't initiated because user doesn't have subscription", result);
        verify(subscriptionService, times(1)).isUserSubscribed(sms.getRecipient());
    }

}
