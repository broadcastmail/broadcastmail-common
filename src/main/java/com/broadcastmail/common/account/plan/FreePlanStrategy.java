package com.broadcastmail.common.account.plan;

public class FreePlanStrategy implements PlanStrategy {
    @Override
    public int recipientLimit() {
        return 500;
    }

    @Override
    public int retentionDays() {
        return 7;
    }

    @Override
    public boolean has(PlanFeature feature) {
        return false;
    }

    @Override
    public void checkRecipientLimit(int recipientCount, int newRecipients) {
        if (recipientCount + newRecipients > recipientLimit()) {
            throw new PlanLimitExceededException();
        }
    }
}
