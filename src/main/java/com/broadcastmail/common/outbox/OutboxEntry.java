package com.broadcastmail.common.outbox;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  OutboxEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "campaign_recipient_id", nullable = false)
    @Getter
    private UUID campaignRecipientId;

    @NotNull
    @Setter
    @Column(name = "status", nullable = false, length = Integer.MAX_VALUE)
    @Convert(converter = OutboxStatus.PersistenceConverter.class)
    private OutboxStatus status;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "attempts", nullable = false)
    @Setter
    @Getter
    private Integer attempts;

    @NotNull
    @Timestamp
    @Column(name = "next_attempt_at", nullable = false)
    @Setter
    private OffsetDateTime nextAttemptAt;

    @Column(name = "last_attempted_at")
    @Timestamp
    @Setter
    private OffsetDateTime lastAttemptedAt;

    @CreationTimestamp
    private OffsetDateTime createdAt;

}
