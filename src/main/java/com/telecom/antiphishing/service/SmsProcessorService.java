package com.telecom.antiphishing.service;

import com.telecom.antiphishing.model.Sms;

/***
 * @author - Kiryl Karpuk
 */
public interface SmsProcessorService {

    String processSms(Sms sms);

}
