package com.telecom.antiphishing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * @author - Kiryl Karpuk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage implements Serializable {

    private LocalDateTime timestamp;
    private String message;
    private String description;

}
