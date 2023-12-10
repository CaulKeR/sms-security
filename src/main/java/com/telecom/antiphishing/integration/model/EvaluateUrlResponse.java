package com.telecom.antiphishing.integration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/***
 *
 * More details can be found here:
 * https://cloud.google.com/web-risk/docs/reference/rest/v1eap1/TopLevel/evaluateUri#response-body
 *
 * @author - Kiryl Karpuk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateUrlResponse implements Serializable {

    private List<Score> scores;

}
