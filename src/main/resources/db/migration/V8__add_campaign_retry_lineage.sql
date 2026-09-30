ALTER TABLE campaigns
    ADD COLUMN retry_of_campaign_id UUID REFERENCES campaigns(id) ON DELETE SET NULL;

CREATE UNIQUE INDEX idx_campaigns_retry_of_campaign_id
    ON campaigns (retry_of_campaign_id)
    WHERE retry_of_campaign_id IS NOT NULL;
