ALTER TABLE accounts
    ADD CONSTRAINT accounts_plan_check
    CHECK (plan IN ('FREE', 'PRO'));