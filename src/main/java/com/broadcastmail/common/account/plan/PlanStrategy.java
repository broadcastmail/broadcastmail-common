package com.broadcastmail.common.account.plan;

public interface PlanStrategy {
    int recipientLimit();
    int retentionDays();
    boolean has(PlanFeature feature);
    void checkRecipientLimit(int current,int newRecipients);
}
