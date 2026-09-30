package com.broadcastmail.common.account.plan;

public class PlanLimitExceededException extends RuntimeException {
    public PlanLimitExceededException() {
        super("Recipient limit exceeded for current plan");
    }
}
