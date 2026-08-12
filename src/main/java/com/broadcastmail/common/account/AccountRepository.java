package com.broadcastmail.common.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByApiKeyHash(String apiKeyHash);
    boolean existsByEmail(String email);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByStripeCustomerId(String stripeCustomerId);
    @Modifying
    @Query(value = "UPDATE accounts SET unique_recipients_this_period = 0, period_reset_at = now() + interval '30 days' WHERE period_reset_at <= now()", nativeQuery = true)
    void resetExpiredPeriods();
}
