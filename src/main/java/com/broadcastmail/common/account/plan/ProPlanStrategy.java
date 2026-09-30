package com.broadcastmail.common.account.plan;

public class ProPlanStrategy implements PlanStrategy {

    @Override
    public boolean has(PlanFeature feature) {
        return true;
    }

    @Override
    public int recipientLimit() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int retentionDays() {
        return 90;
    }

    @Override
    public void checkRecipientLimit(int current, int newRecipients) {
        // no limit — no-op
    }
}
