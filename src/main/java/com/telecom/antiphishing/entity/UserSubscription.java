package com.telecom.antiphishing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author - Kiryl Karpuk
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscription {

    @Id
    private String userId;
    private Boolean subscribed;

}
