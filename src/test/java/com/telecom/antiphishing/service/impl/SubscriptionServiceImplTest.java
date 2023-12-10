package com.telecom.antiphishing.service.impl;

import com.telecom.antiphishing.enums.SubscriptionCommand;
import com.telecom.antiphishing.model.Sms;
import com.telecom.antiphishing.repository.UserSubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {

    @Mock
    private UserSubscriptionRepository userSubscriptionRepository;
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    private Sms sms;

    @BeforeEach
    public void init() {
        sms = new Sms("sender", "recipient", "message");
    }

    @Test
    public void isUserSubscribedWhenSubscribedThenReturnTrue() {
        when(userSubscriptionRepository.existsByUserIdAndSubscribedTrue(anyString())).thenReturn(true);

        assertTrue(subscriptionService.isUserSubscribed(anyString()));
    }

    @Test
    public void isUserSubscribedWhenUnsubscribedThenReturnFalse() {
        when(userSubscriptionRepository.existsByUserIdAndSubscribedTrue(anyString())).thenReturn(false);

        assertFalse(subscriptionService.isUserSubscribed(anyString()));
    }

    @Test
    public void manageSubscriptionWhenCommandStartThenSubscribe() {
        sms.setMessage(SubscriptionCommand.START.name());

        String result = subscriptionService.manageSubscription(sms);

        assertEquals("You subscribed to anti-phishing service", result);
    }

    @Test
    public void manageSubscriptionWhenCommandStopThenUnsubscribe() {
        sms.setMessage(SubscriptionCommand.STOP.name());

        String result = subscriptionService.manageSubscription(sms);

        assertEquals("You unsubscribed from anti-phishing service", result);
    }

    @Test
    public void manageSubscriptionWhenCommandUnknownThenThrowException() {
        sms.setMessage("DESTROY THE SYSTEM");

        assertThrows(UnsupportedOperationException.class,
                () -> subscriptionService.manageSubscription(sms));
    }

}
