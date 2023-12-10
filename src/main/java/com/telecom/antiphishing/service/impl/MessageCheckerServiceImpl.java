package com.telecom.antiphishing.service.impl;

import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;
import com.telecom.antiphishing.exception.PhishingMessageException;
import com.telecom.antiphishing.integration.service.UriValidationService;
import com.telecom.antiphishing.service.MessageCheckerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/***
 * @author - Kiryl Karpuk
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageCheckerServiceImpl implements MessageCheckerService {

    private final UriValidationService uriValidationService;

    @Override
    public void validateMessageForPhishingLinks(String senderId, String message) {
        UrlDetector parser = new UrlDetector(message, UrlDetectorOptions.Default);
        parser.detect()
                .stream()
                .map(Url::getFullUrl)
                .collect(Collectors.toSet())
                .forEach(uri -> {
                    if (uriValidationService.isPhishingUri(uri)) {
                        log.warn("Phishing detected, sender: {}, message: {},", senderId, message);
                        throw new PhishingMessageException();
                    }
                });
    }

}
