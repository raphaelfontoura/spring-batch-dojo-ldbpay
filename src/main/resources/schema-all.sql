DROP TABLE payouts IF EXISTS;

CREATE TABLE payouts(id UUID PRIMARY KEY, amount_value INT, type VARCHAR(100), psp VARCHAR(100));