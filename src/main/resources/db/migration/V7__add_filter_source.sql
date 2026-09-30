-- Lets a campaign filter (or a connection's filterable-column catalog) target
-- Supabase Auth metadata (auth.users, via the auth.user_emails view) in addition
-- to the linked profile table, including keys inside raw_user_meta_data /
-- raw_app_meta_data.

ALTER TABLE filterable_columns
    ADD COLUMN source TEXT NOT NULL DEFAULT 'PROFILE_TABLE'
        CHECK (source IN ('PROFILE_TABLE', 'AUTH_METADATA'));

-- column_name alone is no longer unique per connection once AUTH_METADATA columns
-- (e.g. created_at from auth.users) can coexist with a same-named PROFILE_TABLE column.
ALTER TABLE filterable_columns
    DROP CONSTRAINT IF EXISTS filterable_columns_connection_id_column_name_key;
ALTER TABLE filterable_columns
    ADD CONSTRAINT filterable_columns_connection_id_column_name_source_key
        UNIQUE (connection_id, column_name, source);

ALTER TABLE campaign_filters
    ADD COLUMN source TEXT NOT NULL DEFAULT 'PROFILE_TABLE'
        CHECK (source IN ('PROFILE_TABLE', 'AUTH_METADATA', 'AUTH_METADATA_JSON'));
ALTER TABLE campaign_filters
    ADD COLUMN json_key TEXT;
