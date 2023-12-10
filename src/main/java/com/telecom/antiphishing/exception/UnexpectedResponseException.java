package com.telecom.antiphishing.exception;

/***
 * @author - Kiryl Karpuk
 */
public class UnexpectedResponseException extends RuntimeException {

    public UnexpectedResponseException(String message) {
        super(message);
    }

    public UnexpectedResponseException(String message, Throwable cause) {
        super(message, cause);
    }

}
