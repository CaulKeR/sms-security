package com.telecom.antiphishing.service;

/***
 * @author - Kiryl Karpuk
 */
public interface MessageCheckerService {

    void validateMessageForPhishingLinks(String senderId, String message);

}
