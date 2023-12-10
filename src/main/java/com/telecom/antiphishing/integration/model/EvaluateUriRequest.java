package com.telecom.antiphishing.integration.model;

import com.telecom.antiphishing.integration.enums.ThreatType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/***
 *
 * More details can be found here:
 * https://cloud.google.com/web-risk/docs/reference/rest/v1eap1/TopLevel/evaluateUri#request-body
 *
 * @author - Kiryl Karpuk
 */
@Data
public class EvaluateUriRequest implements Serializable {

    private String uri;
    private List<ThreatType> threatTypes;
    private boolean allowScan;

    public EvaluateUriRequest(String uri) {
        this.uri = uri;
        this.threatTypes = List.of(ThreatType.values());
        this.allowScan = true;
    }

}
