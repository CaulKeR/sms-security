package com.telecom.antiphishing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/***
 * @author - Kiryl Karpuk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sms implements Serializable {

    private String sender;
    private String recipient;
    private String message;

}
