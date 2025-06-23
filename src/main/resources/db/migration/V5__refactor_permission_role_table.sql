-- Disable foreign key checks to allow dropping tables with dependencies
SET FOREIGN_KEY_CHECKS = 0;

-- Drop all related tables (including JPA-generated ones)
DROP TABLE IF EXISTS account_role;
DROP TABLE IF EXISTS permission_role;
DROP TABLE IF EXISTS role_permissions;  -- JPA-generated table
DROP TABLE IF EXISTS permission;
DROP TABLE IF EXISTS role;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Create the role table with name as primary key
CREATE TABLE role(
    name VARCHAR(100) NOT NULL PRIMARY KEY,
    description VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create the permission table with name as primary key
CREATE TABLE permission(
    name VARCHAR(100) NOT NULL PRIMARY KEY,
    description VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create the account_role junction table
CREATE TABLE account_role(
    account_id BIGINT NOT NULL,
    role_name VARCHAR(100) NOT NULL,
    PRIMARY KEY(account_id, role_name),
    CONSTRAINT fk_ar_acc FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    CONSTRAINT fk_ar_role FOREIGN KEY (role_name) REFERENCES role(name) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create the permission_role junction table
CREATE TABLE permission_role(
    role_name VARCHAR(100) NOT NULL,
    permission_name VARCHAR(100) NOT NULL,
    PRIMARY KEY(role_name, permission_name),
    CONSTRAINT fk_role_name FOREIGN KEY (role_name) REFERENCES role(name) ON DELETE CASCADE,
    CONSTRAINT fk_permission_name FOREIGN KEY (permission_name) REFERENCES permission(name) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

