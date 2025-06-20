-- Disable foreign key checks to allow dropping referenced tables
SET FOREIGN_KEY_CHECKS = 0;

-- Drop the old appointment table
DROP TABLE IF EXISTS appointment;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Recreate appointment table with account_id instead of member_id
CREATE TABLE appointment (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_at DATE NOT NULL,
  status VARCHAR(50) NOT NULL,
  account_id BIGINT NOT NULL,
  request_id BIGINT NOT NULL,

  PRIMARY KEY (id),

  -- Enforce that each (request, account) pair is unique
  UNIQUE KEY uk_appointments_request_account (request_id, account_id),

  -- Index for faster lookups
  INDEX idx_appointment_account (account_id),

  -- Foreign key constraints
  CONSTRAINT fk_appointment_member
    FOREIGN KEY (account_id)
    REFERENCES account (id)
    ON DELETE RESTRICT,

  CONSTRAINT fk_appointment_request
    FOREIGN KEY (request_id)
    REFERENCES blood_request (id)
    ON DELETE RESTRICT
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;