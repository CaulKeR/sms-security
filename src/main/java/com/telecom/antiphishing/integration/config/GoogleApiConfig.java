package com.telecom.antiphishing.integration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;


/***
 * @author - Kiryl Karpuk
 */
@Configuration
public class GoogleApiConfig {

    public static final String API_URL = "https://webrisk.googleapis.com/v1eap1:evaluateUri";

    @Value("${app.uri-validation.google.api-key}")
    private String apiKey;

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);
        return headers;
    }

}
