package com.telecom.antiphishing.service.impl;

import com.telecom.antiphishing.entity.UserSubscription;
import com.telecom.antiphishing.enums.SubscriptionCommand;
import com.telecom.antiphishing.model.Sms;
import com.telecom.antiphishing.repository.UserSubscriptionRepository;
import com.telecom.antiphishing.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/***
 * @author - Kiryl Karpuk
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final String SUBSCRIPTION_RESPONSE = "You subscribed to anti-phishing service";
    private static final String UNSUBSCRIPTION_RESPONSE = "You unsubscribed from anti-phishing service";

    private final UserSubscriptionRepository userSubscriptionRepository;

    @Override
    public boolean isUserSubscribed(String userId) {
        return userSubscriptionRepository.existsByUserIdAndSubscribedTrue(userId);
    }

    @Override
    public String manageSubscription(Sms sms) {
        String senderId = sms.getSender();
        if (SubscriptionCommand.START.name().equalsIgnoreCase(sms.getMessage())) {
            updateSubStatus(senderId, true);
            log.info("User {} subscribed to anti-phishing service", senderId);
            return SUBSCRIPTION_RESPONSE;
        } else if (SubscriptionCommand.STOP.name().equalsIgnoreCase(sms.getMessage())) {
            updateSubStatus(senderId, false);
            log.info("User {} unsubscribed from anti-phishing service", senderId);
            return UNSUBSCRIPTION_RESPONSE;
        }
        throw new UnsupportedOperationException("Unknown operation: " + sms.getMessage());
    }

    private void updateSubStatus(String userId, boolean subscribed) {
        userSubscriptionRepository.save(new UserSubscription(userId, subscribed));
    }

}
