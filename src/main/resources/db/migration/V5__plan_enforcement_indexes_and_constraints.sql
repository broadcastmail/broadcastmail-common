CREATE INDEX idx_campaign_recipients_campaign_email
    ON campaign_recipients(campaign_id, email);

ALTER TABLE campaign_recipients
    ADD CONSTRAINT uq_campaign_recipients_campaign_external_user
        UNIQUE (campaign_id, external_user_id);

-- drop unused counter columns (replaced by countUniqueRecipientsSince query)
ALTER TABLE accounts
    DROP COLUMN unique_recipients_this_period,
    DROP COLUMN period_reset_at;