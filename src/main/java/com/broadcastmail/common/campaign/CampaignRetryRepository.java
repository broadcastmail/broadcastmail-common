package com.broadcastmail.common.campaign;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CampaignRetryRepository extends Repository<Campaign, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select campaign
            from Campaign campaign
            where campaign.accountId = :accountId
              and campaign.id = :campaignId
            """)
    Optional<Campaign> findByAccountIdAndIdForUpdate(
            @Param("accountId") UUID accountId,
            @Param("campaignId") UUID campaignId);

    @Query(value = """
            SELECT COUNT(*)
            FROM campaign_recipients
            WHERE campaign_id = :campaignId
              AND status = 'failed'
            """, nativeQuery = true)
    long countFailedRecipients(@Param("campaignId") UUID campaignId);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM campaigns
                WHERE retry_of_campaign_id = :campaignId
            )
            """, nativeQuery = true)
    boolean hasRetryCampaign(@Param("campaignId") UUID campaignId);

    @Modifying
    @Query(value = """
            INSERT INTO campaign_recipients
                (id, campaign_id, external_user_id, email, status, idempotency_key, created_at)
            SELECT gen_random_uuid(),
                   :retryCampaignId,
                   external_user_id,
                   email,
                   'queued',
                   CAST(:retryCampaignId AS text) || ':' || external_user_id,
                   now()
            FROM campaign_recipients
            WHERE campaign_id = :originalCampaignId
              AND status = 'failed'
            """, nativeQuery = true)
    int copyFailedRecipients(
            @Param("originalCampaignId") UUID originalCampaignId,
            @Param("retryCampaignId") UUID retryCampaignId);

    @Modifying
    @Query(value = """
            INSERT INTO outbox (id, campaign_recipient_id, status, attempts, next_attempt_at)
            SELECT gen_random_uuid(), id, 'pending', 0, now()
            FROM campaign_recipients
            WHERE campaign_id = :retryCampaignId
            """, nativeQuery = true)
    int enqueueRecipients(@Param("retryCampaignId") UUID retryCampaignId);
}
