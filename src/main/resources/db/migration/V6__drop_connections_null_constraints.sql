ALTER TABLE connections
    ALTER COLUMN user_table_schema DROP NOT NULL,
ALTER COLUMN user_table_name DROP NOT NULL,
    ALTER COLUMN email_column DROP NOT NULL,
    ALTER COLUMN user_id_column DROP NOT NULL;