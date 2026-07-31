package com.broadcastmail.common.campaign;

import com.broadcastmail.common.campaign.recipient.RecipientStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
    Page<Campaign> findByAccountId(UUID accountId, Pageable pageable);
    Optional<Campaign> findByAccountIdAndId(UUID accountId, UUID campaignId);
    List<Campaign> findByAccountId(UUID accountId);
    long countByIdAndStatus(UUID accountId, RecipientStatus status);
    @Modifying
    @Query(value = "DELETE FROM campaigns WHERE sent_at < :cutoff AND account_id IN (SELECT id FROM accounts WHERE plan = :plan)", nativeQuery = true)
    void deleteByPlanAndSentAtBefore(@Param("plan") String plan, @Param("cutoff") OffsetDateTime cutoff);

}