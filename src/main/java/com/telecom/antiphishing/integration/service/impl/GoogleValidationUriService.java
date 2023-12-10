package com.telecom.antiphishing.integration.service.impl;

import com.telecom.antiphishing.exception.UnexpectedResponseException;
import com.telecom.antiphishing.integration.config.GoogleApiConfig;
import com.telecom.antiphishing.integration.enums.ConfidenceLevel;
import com.telecom.antiphishing.integration.model.EvaluateUriRequest;
import com.telecom.antiphishing.integration.model.EvaluateUrlResponse;
import com.telecom.antiphishing.integration.service.UriValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

/***
 * @author - Kiryl Karpuk
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleValidationUriService implements UriValidationService {

    private static final Set<ConfidenceLevel> RISKY_SCORES = Set.of(
            ConfidenceLevel.CONFIDENCE_LEVEL_UNSPECIFIED,
            ConfidenceLevel.LOW,
            ConfidenceLevel.MEDIUM,
            ConfidenceLevel.HIGH,
            ConfidenceLevel.HIGHER);

    private final RestTemplate restTemplate;
    private final GoogleApiConfig apiConfig;

    @Override
    public boolean isPhishingUri(String uri) {
        try {
            ResponseEntity<EvaluateUrlResponse> response =
                    restTemplate
                            .exchange(
                                    GoogleApiConfig.API_URL,
                                    HttpMethod.POST,
                                    new HttpEntity<>(
                                            new EvaluateUriRequest(uri),
                                            apiConfig.getHeaders()),
                                    EvaluateUrlResponse.class);
            if (response.getStatusCode().is2xxSuccessful() &&
                response.getBody() != null &&
                !CollectionUtils.isEmpty(response.getBody().getScores())) {
                return containsPhishingLinks(response.getBody());
            }
            log.error("Unexpected answer: {}", response);
            throw new UnexpectedResponseException("Unexpected answer from verification service");
        } catch (Exception e) {
            log.error("Unexpected answer: ", e);
            throw new UnexpectedResponseException("Unexpected answer from verification service", e);
        }
    }

    private boolean containsPhishingLinks(EvaluateUrlResponse response) {
        return response.getScores()
                .stream()
                .anyMatch(score ->
                        RISKY_SCORES.contains(
                                score.getConfidenceLevel()));
    }

}
