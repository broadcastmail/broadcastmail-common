package com.broadcastmail.common.connection;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "connections")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class Connection {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @ColumnDefault("'supabase'")
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "project_ref")
    @Getter
    private String projectRef;

    @NotNull
    @Column(name = "project_url", nullable = false)
    private String projectUrl;

    @NotNull
    @Column(name = "encrypted_creds", nullable = false)
    @Getter
    private String encryptedCreds;

    @ColumnDefault("'public'")
    @Getter
    @Column(name = "user_table_schema", nullable = true)
    private String userTableSchema;

    @Getter
    @Column(name = "user_table_name", nullable = true)
    private String userTableName;

    @Column(name = "email_column", nullable = true)
    @Getter
    private String emailColumn;

    @Column(name = "user_id_column", nullable = true)
    @Getter
    private String userIdColumn;

    @Column(name = "estimated_user_count")
    @Getter
    private Integer estimatedUserCount;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

}
