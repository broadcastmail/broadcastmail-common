package com.broadcastmail.common.account.plan;

public enum Plan {
    FREE(new FreePlanStrategy()),
    PRO(new ProPlanStrategy());

    private final PlanStrategy strategy;

    Plan(PlanStrategy strategy) {
        this.strategy = strategy;
    }

    public PlanStrategy strategy() {
        return strategy;
    }
}
