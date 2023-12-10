package com.telecom.antiphishing.service.impl;

import com.telecom.antiphishing.model.Sms;
import com.telecom.antiphishing.service.MessageCheckerService;
import com.telecom.antiphishing.service.SmsProcessorService;
import com.telecom.antiphishing.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

/***
 * @author - Kiryl Karpuk
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsProcessorServiceImpl implements SmsProcessorService {

    private static final String VALID_MESSAGE_RESPONSE =
            "Message is valid and can be send";
    private static final String VALIDATION_SKIPPED_RESPONSE =
            "Validation wasn't initiated because user doesn't have subscription";
    private final MessageCheckerService messageCheckerService;
    private final SubscriptionService subscriptionService;
    @Value("${app.system-phone}")
    private String systemPhoneId;

    @Override
    public String processSms(Sms sms) {
        // message to system scenario
        if (isMessageToSystem(sms)) {
            return subscriptionService.manageSubscription(sms);
        }
        // message to person scenario
        if (subscriptionService.isUserSubscribed(sms.getRecipient())) {
            messageCheckerService
                    .validateMessageForPhishingLinks(
                            sms.getSender(),
                            sms.getMessage());
            return VALID_MESSAGE_RESPONSE;
        }
        return VALIDATION_SKIPPED_RESPONSE;
    }

    private boolean isMessageToSystem(Sms sms) {
        return Objects.equals(systemPhoneId, sms.getRecipient());
    }

}
