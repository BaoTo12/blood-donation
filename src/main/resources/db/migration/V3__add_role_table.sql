CREATE TABLE IF NOT EXISTS role(
     id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL UNIQUE,
     description VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS account_role(
    account_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY(account_id, role_id),
    CONSTRAINT fk_ar_acc FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    CONSTRAINT fk_ar_role  FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Drop role column in account
ALTER TABLE account DROP COLUMN role;

INSERT INTO role(name) VALUES ('ADMIN'), ('STAFF'), ('MEMBER');