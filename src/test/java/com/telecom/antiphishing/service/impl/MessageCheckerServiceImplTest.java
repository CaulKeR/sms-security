package com.telecom.antiphishing.service.impl;

import com.telecom.antiphishing.exception.PhishingMessageException;
import com.telecom.antiphishing.integration.service.UriValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageCheckerServiceImplTest {

    private static final String senderId = "sender";
    @Mock
    private UriValidationService uriValidationService;
    @InjectMocks
    private MessageCheckerServiceImpl messageCheckerService;

    @Test
    void validateMessageForPhishingLinksWhenNoLinksThenExceptionDoesNotThrow() {
        String message = "SMS without any links";

        assertDoesNotThrow(() -> messageCheckerService.validateMessageForPhishingLinks(senderId, message));
        verify(uriValidationService, times(0)).isPhishingUri(anyString());
    }

    @Test
    void validateMessageForPhishingLinksWhenPhishingLinkThenThrowException() {
        String message = "SMS with phishing link: https://scam.pl";

        when(uriValidationService.isPhishingUri(anyString())).thenReturn(true);

        assertThrows(PhishingMessageException.class,
                () -> messageCheckerService.validateMessageForPhishingLinks(senderId, message));
    }

    @Test
    void validateMessageForPhishingLinksWhenHasTrustedLinkThenDoesNotThrowException() {
        String message = "SMS contains trusted links: https://trusted.pl or https://pko.pl/super-trusted-api/data";

        when(uriValidationService.isPhishingUri(anyString())).thenReturn(false);

        messageCheckerService.validateMessageForPhishingLinks(senderId, message);

        assertDoesNotThrow(() -> messageCheckerService.validateMessageForPhishingLinks(senderId, message));
    }

}
