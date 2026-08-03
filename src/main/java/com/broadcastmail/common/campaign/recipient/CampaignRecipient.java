package com.broadcastmail.common.campaign.recipient;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "campaign_recipients")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @Getter
    private UUID id;

    @NotNull
    @Getter
    @Column(name = "campaign_id", nullable = false)
    private UUID campaignId;

    @NotNull
    @Column(name = "external_user_id", nullable = false)
    private String externalUserId;

    @NotNull
    @Column(name = "email", nullable = false)
    @Getter
    private String email;

    @NotNull
    @Column(name = "status", nullable = false)
    @Setter
    @Getter
    @Convert(converter = RecipientStatus.PersistenceConverter.class)
    private RecipientStatus status;

    @NotNull
    @Column(name = "idempotency_key", nullable = false)
    @Getter
    private String idempotencyKey;

    @Column(name = "resend_message_id")
    @Setter
    @Getter
    private String resendMessageId;

    @Column(name = "failed_reason")
    @Setter
    @Getter
    private String failedReason;

    @Column(name = "sent_at")
    @Setter
    private OffsetDateTime sentAt;

    @Column(name = "delivered_at")
    private OffsetDateTime deliveredAt;

    @Column(name = "opened_at")
    private OffsetDateTime openedAt;

    @Column(name = "bounced_at")
    private OffsetDateTime bouncedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

}
