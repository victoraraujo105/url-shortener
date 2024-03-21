-- Create database
SELECT 'CREATE DATABASE urlshortener'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'urlshortener')\gexec

-- Use database
\c urlshortener;

-- Create extension
CREATE EXTENSION pgcrypto;

-- Function to generate alias
-- Keeps generating alias (8 digit base64) until it finds a unique one
CREATE OR REPLACE FUNCTION generate_alias() RETURNS VARCHAR(8) AS $$
DECLARE
    vl_alias_pretrim VARCHAR;
    vl_alias VARCHAR(8);
BEGIN
    LOOP
        vl_alias_pretrim := encode(gen_random_bytes(6), 'base64');
        vl_alias_pretrim := REPLACE(vl_alias_pretrim, '/', '_');
        vl_alias_pretrim := REPLACE(vl_alias_pretrim, '+', '-');
        vl_alias := SUBSTR(vl_alias_pretrim, 1, 8);
        EXIT WHEN NOT EXISTS (SELECT 1 FROM aliases WHERE alias = vl_alias);
    END LOOP;
    RETURN vl_alias;
END;
$$ LANGUAGE plpgsql;

-- Create table
CREATE TABLE IF NOT EXISTS aliases (
    id SERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    alias VARCHAR(8) NOT NULL UNIQUE DEFAULT generate_alias(),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_alias ON aliases (alias);
