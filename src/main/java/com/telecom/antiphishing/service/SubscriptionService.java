package com.telecom.antiphishing.service;

import com.telecom.antiphishing.model.Sms;

/***
 * @author - Kiryl Karpuk
 */
public interface SubscriptionService {

    boolean isUserSubscribed(String userId);

    String manageSubscription(Sms sms);

}
