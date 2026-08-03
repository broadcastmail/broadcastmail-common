package com.broadcastmail.common.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface OutboxEntryRepository extends JpaRepository<OutboxEntry, UUID> {

    List<OutboxEntry> findByStatusAndLastAttemptedAtBefore(OutboxStatus status, OffsetDateTime cutoff);

    @Query(value = """
        SELECT * FROM outbox
        WHERE status = 'pending'
        AND next_attempt_at <= now()
        ORDER BY next_attempt_at
        LIMIT 50
        FOR UPDATE SKIP LOCKED  
        """, nativeQuery = true)
    List<OutboxEntry> pollPending();

    @Modifying
    @Query(value = "UPDATE outbox SET status = 'pending', next_attempt_at = now() WHERE status = 'processing' AND last_attempted_at < :cutoff", nativeQuery = true)
    void resetStuckRows(@Param("cutoff") OffsetDateTime cutoff);
}