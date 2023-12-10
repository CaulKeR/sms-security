package com.telecom.antiphishing.exception;

/***
 * @author - Kiryl Karpuk
 */
public class PhishingMessageException extends RuntimeException {

    public PhishingMessageException() {
        super("Phishing detected");
    }

}
