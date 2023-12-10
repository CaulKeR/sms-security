package com.telecom.antiphishing.repository;

import com.telecom.antiphishing.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/***
 * @author - Kiryl Karpuk
 */
@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, String> {

    boolean existsByUserIdAndSubscribedTrue(String userId);

}
