package com.broadcastmail.common.campaign.recipient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CampaignRecipientRepository extends JpaRepository<CampaignRecipient, UUID> {
    Page<CampaignRecipient> findByCampaignId(UUID campaignId, Pageable pageable);
    Optional<CampaignRecipient> findByResendMessageId(UUID resendMessageId);
    long countByCampaignIdAndStatus(UUID campaignId, RecipientStatus status);
    long countByCampaignId(UUID campaignId);
    Page<CampaignRecipient> findByCampaignIdAndStatus(UUID campaignId, RecipientStatus status, Pageable pageable);
    List<CampaignRecipient> findByCampaignIdAndStatus(UUID campaignId, RecipientStatus status);

    @Query(value = """
            SELECT
                COUNT(*) FILTER (WHERE status = 'queued') AS queued,
                COUNT(*) AS total,
                COUNT(*) FILTER (WHERE status = 'failed') AS failed
            FROM campaign_recipients
            WHERE campaign_id = :campaignId
            """, nativeQuery = true)
    RecipientStatusCounts countStatusesByCampaignId(@Param("campaignId") UUID campaignId);
}