package com.broadcastmail.common.emailprovider;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_providers")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EmailProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "encrypted_api_key", nullable = false)
    @Getter
    private String encryptedApiKey;

    @NotNull
    @Column(name = "from_address", nullable = false)
    @Getter
    private String fromAddress;

    @Column(name = "from_name")
    private String fromName;

    @Column(name = "encrypted_webhook_secret")
    @Getter
    private String encryptedWebhookSecret;

    @Column(name = "resend_webhook_id")
    @Getter
    private String resendWebhookId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;


}
