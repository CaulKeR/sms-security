package com.telecom.antiphishing.integration.service.impl;


import com.telecom.antiphishing.exception.UnexpectedResponseException;
import com.telecom.antiphishing.integration.config.GoogleApiConfig;
import com.telecom.antiphishing.integration.enums.ConfidenceLevel;
import com.telecom.antiphishing.integration.enums.ThreatType;
import com.telecom.antiphishing.integration.model.EvaluateUriRequest;
import com.telecom.antiphishing.integration.model.EvaluateUrlResponse;
import com.telecom.antiphishing.integration.model.Score;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GoogleValidationUriServiceTest {

    private final HttpHeaders headers = new HttpHeaders() {{
        set("Content-Type", "application/json");
        set("Authorization", "Bearer someToken");
    }};
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private GoogleApiConfig apiConfig;
    @InjectMocks
    private GoogleValidationUriService googleValidationUriService;

    @Test
    void isPhishingUriWhenLinkIsSafeThenReturnFalse() {
        String uri = "https://trustedLink.com";

        EvaluateUrlResponse mockResponse = new EvaluateUrlResponse();
        mockResponse.setScores(List.of(new Score(ThreatType.THREAT_TYPE_UNSPECIFIED, ConfidenceLevel.SAFE)));
        ResponseEntity<EvaluateUrlResponse> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(GoogleApiConfig.API_URL),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(EvaluateUrlResponse.class)))
                .thenReturn(mockResponseEntity);
        when(apiConfig.getHeaders()).thenReturn(headers);

        boolean result = googleValidationUriService.isPhishingUri(uri);
        verify(restTemplate).exchange(
                eq(GoogleApiConfig.API_URL),
                eq(HttpMethod.POST),
                argThat(requestEntity -> {
                    EvaluateUriRequest evaluateUriRequest = (EvaluateUriRequest) requestEntity.getBody();
                    assertEquals(uri, evaluateUriRequest.getUri());
                    return true;
                }),
                eq(EvaluateUrlResponse.class));
        assertFalse(result);
    }

    @Test
    void isPhishingUriWhenServiceNotWorkingThenThrowException() {
        String uri = "https://trustedLink.com";

        ResponseEntity<EvaluateUrlResponse> errorResponseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        when(restTemplate.exchange(
                eq(GoogleApiConfig.API_URL),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(EvaluateUrlResponse.class)))
                .thenReturn(errorResponseEntity);

        when(apiConfig.getHeaders()).thenReturn(headers);

        assertThrows(UnexpectedResponseException.class,
                () -> googleValidationUriService.isPhishingUri(uri));
    }

    @Test
    void isPhishingUriWhenLinkIsUnsafeThenReturnTrue() {
        String uri = "https://scam.com";

        EvaluateUrlResponse mockResponse = new EvaluateUrlResponse();
        mockResponse.setScores(List.of(new Score(ThreatType.UNWANTED_SOFTWARE, ConfidenceLevel.LOW)));
        ResponseEntity<EvaluateUrlResponse> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(GoogleApiConfig.API_URL),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(EvaluateUrlResponse.class)))
                .thenReturn(mockResponseEntity);
        when(apiConfig.getHeaders()).thenReturn(headers);

        boolean result = googleValidationUriService.isPhishingUri(uri);
        verify(restTemplate)
                .exchange(
                        eq(GoogleApiConfig.API_URL),
                        eq(HttpMethod.POST),
                        argThat(requestEntity -> {
                            EvaluateUriRequest evaluateUriRequest = (EvaluateUriRequest) requestEntity.getBody();
                            assertEquals(uri, evaluateUriRequest.getUri());
                            return true;
                        }),
                        eq(EvaluateUrlResponse.class));
        assertTrue(result);
    }


}
