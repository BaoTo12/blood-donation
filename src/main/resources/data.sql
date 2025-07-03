-- ✅ We'll generate roughly:
-- - 10 accounts
-- - 10 blood requests
-- - 10 appointments
-- - 25 donations (some appointments get multiple donations if allowed)
--
-- ⚠️ Assumption: Since donation.appointment_id is UNIQUE, we must either:
--    - Remove UNIQUE constraint (to allow multiple donations per appointment)
--    - Or simulate 25 donations via 25 distinct appointments (we'll go with this)

-- 1. ACCOUNTS
INSERT INTO `account` (created_at, is_active, address, birthday, blood_group, email, gender, name, password, phone) VALUES
('2024-01-01', 1, '123 Street A', '1990-01-01', 'A+', 'a1@example.com', 'M', 'User One', 'password1', '0900000001'),
('2024-01-02', 1, '234 Street B', '1991-02-02', 'B+', 'a2@example.com', 'F', 'User Two', 'password2', '0900000002'),
('2024-01-03', 1, '345 Street C', '1992-03-03', 'O+', 'a3@example.com', 'M', 'User Three', 'password3', '0900000003'),
('2024-01-04', 1, '456 Street D', '1993-04-04', 'AB+', 'a4@example.com', 'F', 'User Four', 'password4', '0900000004'),
('2024-01-05', 1, '567 Street E', '1994-05-05', 'A-', 'a5@example.com', 'M', 'User Five', 'password5', '0900000005'),
('2024-01-06', 1, '678 Street F', '1995-06-06', 'B-', 'a6@example.com', 'F', 'User Six', 'password6', '0900000006'),
('2024-01-07', 1, '789 Street G', '1996-07-07', 'O-', 'a7@example.com', 'M', 'User Seven', 'password7', '0900000007'),
('2024-01-08', 1, '890 Street H', '1997-08-08', 'AB-', 'a8@example.com', 'F', 'User Eight', 'password8', '0900000008'),
('2024-01-09', 1, '901 Street I', '1998-09-09', 'A+', 'a9@example.com', 'M', 'User Nine', 'password9', '0900000009'),
('2024-01-10', 1, '012 Street J', '1999-10-10', 'B+', 'a10@example.com', 'F', 'User Ten', 'password10', '0900000010');

-- 2. BLOOD REQUESTS (linked to accounts 1–10)
INSERT INTO `blood_request` (created_at, blood_group, end_time, is_active, priority, start_time, title, account_id) VALUES
('2024-06-01', 'A+', '2024-06-10', 1, 'HIGH', '2024-06-02', 'Request 1', 1),
('2024-06-02', 'B+', '2024-06-11', 1, 'MEDIUM', '2024-06-03', 'Request 2', 2),
('2024-06-03', 'O+', '2024-06-12', 1, 'LOW', '2024-06-04', 'Request 3', 3),
('2024-06-04', 'AB+', '2024-06-13', 1, 'HIGH', '2024-06-05', 'Request 4', 4),
('2024-06-05', 'A-', '2024-06-14', 1, 'MEDIUM', '2024-06-06', 'Request 5', 5),
('2024-06-06', 'B-', '2024-06-15', 1, 'LOW', '2024-06-07', 'Request 6', 6),
('2024-06-07', 'O-', '2024-06-16', 1, 'HIGH', '2024-06-08', 'Request 7', 7),
('2024-06-08', 'AB-', '2024-06-17', 1, 'MEDIUM', '2024-06-09', 'Request 8', 8),
('2024-06-09', 'A+', '2024-06-18', 1, 'LOW', '2024-06-10', 'Request 9', 9),
('2024-06-10', 'B+', '2024-06-19', 1, 'HIGH', '2024-06-11', 'Request 10', 10);

-- 3. APPOINTMENTS (each request has one appointment, with member_id rotating from 1–10)
INSERT INTO `appointment` (created_at, status, request_id, member_id) VALUES
('2024-06-02', 'CONFIRMED', 1, 2),
('2024-06-03', 'CONFIRMED', 2, 3),
('2024-06-04', 'CONFIRMED', 3, 4),
('2024-06-05', 'CONFIRMED', 4, 5),
('2024-06-06', 'CONFIRMED', 5, 6),
('2024-06-07', 'CONFIRMED', 6, 7),
('2024-06-08', 'CONFIRMED', 7, 8),
('2024-06-09', 'CONFIRMED', 8, 9),
('2024-06-10', 'CONFIRMED', 9, 10),
('2024-06-11', 'CONFIRMED', 10, 1);

-- 4. DONATIONS (one per appointment, then reuse appointments to reach 25 donations)
-- 💡 We'll simulate by creating 25 appointments instead (needed due to UNIQUE constraint)
-- so we add 15 more dummy appointments
INSERT INTO `appointment` (created_at, status, request_id, member_id) VALUES
('2024-06-12', 'CONFIRMED', 1, 3),
('2024-06-13', 'CONFIRMED', 2, 4),
('2024-06-14', 'CONFIRMED', 3, 5),
('2024-06-15', 'CONFIRMED', 4, 6),
('2024-06-16', 'CONFIRMED', 5, 7),
('2024-06-17', 'CONFIRMED', 6, 8),
('2024-06-18', 'CONFIRMED', 7, 9),
('2024-06-19', 'CONFIRMED', 8, 10),
('2024-06-20', 'CONFIRMED', 9, 1),
('2024-06-21', 'CONFIRMED', 10, 2),
('2024-06-22', 'CONFIRMED', 1, 4),
('2024-06-23', 'CONFIRMED', 2, 5),
('2024-06-24', 'CONFIRMED', 3, 6),
('2024-06-25', 'CONFIRMED', 4, 7),
('2024-06-26', 'CONFIRMED', 5, 8);

-- DONATIONS (25 rows for 25 unique appointments)
INSERT INTO `donation` (created_at, amount, donation_type, appointment_id) VALUES
('2024-06-02', 450, 'WHOLE_BLOOD', 1),
('2024-06-03', 300, 'PLATELETS', 2),
('2024-06-04', 500, 'PLASMA', 3),
('2024-06-05', 450, 'WHOLE_BLOOD', 4),
('2024-06-06', 300, 'PLATELETS', 5),
('2024-06-07', 500, 'PLASMA', 6),
('2024-06-08', 450, 'WHOLE_BLOOD', 7),
('2024-06-09', 300, 'PLATELETS', 8),
('2024-06-10', 500, 'PLASMA', 9),
('2024-06-11', 450, 'WHOLE_BLOOD', 10),
('2024-06-12', 300, 'PLATELETS', 11),
('2024-06-13', 500, 'PLASMA', 12),
('2024-06-14', 450, 'WHOLE_BLOOD', 13),
('2024-06-15', 300, 'PLATELETS', 14),
('2024-06-16', 500, 'PLASMA', 15),
('2024-06-17', 450, 'WHOLE_BLOOD', 16),
('2024-06-18', 300, 'PLATELETS', 17),
('2024-06-19', 500, 'PLASMA', 18),
('2024-06-20', 450, 'WHOLE_BLOOD', 19),
('2024-06-21', 300, 'PLATELETS', 20),
('2024-06-22', 500, 'PLASMA', 21),
('2024-06-23', 450, 'WHOLE_BLOOD', 22),
('2024-06-24', 300, 'PLATELETS', 23),
('2024-06-25', 500, 'PLASMA', 24),
('2024-06-26', 450, 'WHOLE_BLOOD', 25);
