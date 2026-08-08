package com.broadcastmail.common.campaign.recipient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface CampaignRecipientRepository extends JpaRepository<CampaignRecipient, UUID> {
    Page<CampaignRecipient> findByCampaignId(UUID campaignId, Pageable pageable);
    long countByCampaignId(UUID campaignId);
    Page<CampaignRecipient> findByCampaignIdAndStatus(UUID campaignId, RecipientStatus status, Pageable pageable);
    @Query(value = """
            SELECT
                COUNT(*) FILTER (WHERE status = 'queued') AS queued,
                COUNT(*) AS total,
                COUNT(*) FILTER (WHERE status = 'failed') AS failed
            FROM campaign_recipients
            WHERE campaign_id = :campaignId
            """, nativeQuery = true)
    RecipientStatusCounts countStatusesByCampaignId(@Param("campaignId") UUID campaignId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE campaign_recipients SET status = 'delivered', delivered_at = now() WHERE resend_message_id = :emailId", nativeQuery = true)
    void markDelivered(@Param("emailId") String emailId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE campaign_recipients SET status = 'opened', opened_at = now() WHERE resend_message_id = :emailId", nativeQuery = true)
    void markOpened(@Param("emailId") String emailId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE campaign_recipients SET status = 'bounced', bounced_at = now() WHERE resend_message_id = :emailId", nativeQuery = true)
    void markBounced(@Param("emailId") String emailId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE campaign_recipients SET status = 'failed', failed_reason = :reason WHERE resend_message_id = :emailId", nativeQuery = true)
    void markFailed(@Param("emailId") String emailId, @Param("reason") String reason);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE campaign_recipients SET status = 'unsubscribed' WHERE id = :id", nativeQuery = true)
    void markUnsubscribed(@Param("id") UUID id);

    @Query(value = """
    SELECT COUNT(DISTINCT cr.email)
    FROM campaign_recipients cr
    JOIN campaigns c ON cr.campaign_id = c.id
    WHERE c.account_id = :accountId
    AND c.created_at >= :since
    """, nativeQuery = true)
    long countUniqueRecipientsSince(
            @Param("accountId") UUID accountId,
            @Param("since") OffsetDateTime since
    );
    Optional<CampaignRecipient> findByResendMessageId(String resendMessageId);

    @Modifying
    @Query(value = """
    DELETE FROM campaign_recipients
    WHERE id IN (
        SELECT id FROM campaign_recipients
        WHERE campaign_id = :campaignId
        AND status = 'failed'
        LIMIT :batchSize
    )
    """, nativeQuery = true)
    int deleteFailedBatch(@Param("campaignId") UUID campaignId, @Param("batchSize") int batchSize);
}