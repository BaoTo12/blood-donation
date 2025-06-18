-- Flyway Migration: V1__initial_schema.sql
-- Generated on 2025-06-18

-- Table: account
CREATE TABLE IF NOT EXISTS account (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_at DATE NOT NULL,
  is_active BIT(1) NOT NULL,
  address TEXT,
  birthday DATE DEFAULT NULL,
  blood_group VARCHAR(10) DEFAULT NULL,
  email VARCHAR(255) DEFAULT NULL,
  gender VARCHAR(255) DEFAULT NULL,
  name VARCHAR(255) DEFAULT NULL,
  password VARCHAR(255) DEFAULT NULL,
  phone VARCHAR(255) DEFAULT NULL,
  role VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_account_email (email),
  UNIQUE KEY UK_account_phone (phone),
  CONSTRAINT account_chk_1 CHECK (CHAR_LENGTH(password) >= 8)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: blood_request
CREATE TABLE IF NOT EXISTS blood_request (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_at DATE NOT NULL,
  blood_group ENUM('AB_MINUS','AB_PLUS','A_MINUS','A_PLUS','B_MINUS','B_PLUS','O_MINUS','O_PLUS') DEFAULT NULL,
  end_time DATE NOT NULL,
  is_active BIT(1) NOT NULL,
  priority ENUM('HIGH','LOW','MEDIUM') DEFAULT NULL,
  start_time DATE NOT NULL,
  title VARCHAR(255) NOT NULL,
  account_id BIGINT DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_blood_request_account (account_id),
  CONSTRAINT fk_blood_request_account FOREIGN KEY (account_id) REFERENCES account(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: appointment
CREATE TABLE IF NOT EXISTS appointment (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_at DATE NOT NULL,
  status TINYINT DEFAULT NULL,
  member_id BIGINT NOT NULL,
  request_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_appointments_request_member (request_id, member_id),
  KEY idx_appointment_member (member_id),
  CONSTRAINT fk_appointment_member FOREIGN KEY (member_id) REFERENCES account(id),
  CONSTRAINT fk_appointment_request FOREIGN KEY (request_id) REFERENCES blood_request(id),
  CONSTRAINT appointment_chk_1 CHECK (status BETWEEN 0 AND 3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: blog
CREATE TABLE IF NOT EXISTS blog (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_at DATE NOT NULL,
  content TEXT,
  title VARCHAR(255) DEFAULT NULL,
  account_id BIGINT DEFAULT NULL,
  PRIMARY KEY (id),
  KEY idx_blog_account (account_id),
  CONSTRAINT fk_blog_account FOREIGN KEY (account_id) REFERENCES account(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: comment
CREATE TABLE IF NOT EXISTS comment (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_at DATE NOT NULL,
  content VARCHAR(255) DEFAULT NULL,
  account_id BIGINT DEFAULT NULL,
  blog_id BIGINT DEFAULT NULL,
  PRIMARY KEY (id),
  KEY idx_comment_account (account_id),
  KEY idx_comment_blog (blog_id),
  CONSTRAINT fk_comment_blog FOREIGN KEY (blog_id) REFERENCES blog(id),
  CONSTRAINT fk_comment_account FOREIGN KEY (account_id) REFERENCES account(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: donation
CREATE TABLE IF NOT EXISTS donation (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_at DATE NOT NULL,
  amount INT DEFAULT NULL,
  donation_type ENUM('PLASMA','PLATELET','POWER_RED','WHOLE_BLOOD') DEFAULT NULL,
  appointment_id BIGINT DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_donation_appointment (appointment_id),
  CONSTRAINT fk_donation_appointment FOREIGN KEY (appointment_id) REFERENCES appointment(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: flyway_schema_history (managed by Flyway)
CREATE TABLE IF NOT EXISTS flyway_schema_history (
  installed_rank INT NOT NULL,
  version VARCHAR(50) DEFAULT NULL,
  description VARCHAR(200) NOT NULL,
  type VARCHAR(20) NOT NULL,
  script VARCHAR(1000) NOT NULL,
  checksum INT DEFAULT NULL,
  installed_by VARCHAR(100) NOT NULL,
  installed_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  execution_time INT NOT NULL,
  success TINYINT(1) NOT NULL,
  PRIMARY KEY (installed_rank),
  KEY flyway_schema_history_s_idx (success)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table: health
CREATE TABLE IF NOT EXISTS health (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_at DATE NOT NULL,
  heart_pulse INT DEFAULT NULL,
  is_good_health BIT(1) DEFAULT NULL,
  note TEXT,
  temperature FLOAT DEFAULT NULL,
  weight FLOAT DEFAULT NULL,
  appointment_id BIGINT DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_health_appointment (appointment_id),
  CONSTRAINT fk_health_appointment FOREIGN KEY (appointment_id) REFERENCES appointment(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

