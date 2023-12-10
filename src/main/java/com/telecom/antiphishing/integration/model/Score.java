package com.telecom.antiphishing.integration.model;

import com.telecom.antiphishing.integration.enums.ConfidenceLevel;
import com.telecom.antiphishing.integration.enums.ThreatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/***
 *
 * More details can be found here:
 * https://cloud.google.com/web-risk/docs/reference/rest/v1eap1/TopLevel/evaluateUri#score
 *
 * @author - Kiryl Karpuk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score implements Serializable {

    private ThreatType threatType;
    private ConfidenceLevel confidenceLevel;

}
