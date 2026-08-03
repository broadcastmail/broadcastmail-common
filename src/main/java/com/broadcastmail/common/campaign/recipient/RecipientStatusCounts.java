package com.broadcastmail.common.campaign.recipient;

public interface RecipientStatusCounts {
    long getQueued();
    long getTotal();
    long getFailed();
}
