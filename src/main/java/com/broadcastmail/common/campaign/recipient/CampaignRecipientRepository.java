package com.broadcastmail.common.campaign.recipient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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
}