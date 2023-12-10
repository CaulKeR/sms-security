package com.telecom.antiphishing.controller;

import com.telecom.antiphishing.exception.PhishingMessageException;
import com.telecom.antiphishing.exception.UnexpectedResponseException;
import com.telecom.antiphishing.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/***
 * @author - Kiryl Karpuk
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorMessage> catchWrongCommand(UnsupportedOperationException ex) {
        ErrorMessage message =
                new ErrorMessage(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedResponseException.class)
    public ResponseEntity<ErrorMessage> catchUnexpectedAnswer(UnexpectedResponseException ex) {
        ErrorMessage message =
                new ErrorMessage(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
        return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(PhishingMessageException.class)
    public ResponseEntity<ErrorMessage> catchPhishing(PhishingMessageException ex) {
        ErrorMessage message =
                new ErrorMessage(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.getReasonPhrase());
        return new ResponseEntity<>(message, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

}
