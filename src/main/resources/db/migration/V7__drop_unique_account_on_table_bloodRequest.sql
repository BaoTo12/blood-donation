-- 1) Drop the FK that depends on the unique index
ALTER TABLE blood_request
  DROP FOREIGN KEY fk_blood_request_account;

-- 2) Drop the unique index
ALTER TABLE blood_request
  DROP INDEX UK_blood_request_account;

-- 3) Add a non-unique index on account_id for FK support
ALTER TABLE blood_request
  ADD INDEX idx_blood_request_account (account_id);

-- 4) Re-create the foreign-key constraint
ALTER TABLE blood_request
  ADD CONSTRAINT fk_blood_request_account
    FOREIGN KEY (account_id)
    REFERENCES account (id);
