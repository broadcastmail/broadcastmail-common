package com.broadcastmail.common.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface OutboxEntryRepository extends JpaRepository<OutboxEntry, UUID> {

    List<OutboxEntry> findByStatusAndLastAttemptedAtBefore(String status, OffsetDateTime cutoff);

    @Query(value = """
        SELECT * FROM outbox
        WHERE status = 'pending'
        AND next_attempt_at <= now()
        ORDER BY next_attempt_at
        LIMIT 50
        FOR UPDATE SKIP LOCKED  
        """, nativeQuery = true)
    List<OutboxEntry> pollPending();
}