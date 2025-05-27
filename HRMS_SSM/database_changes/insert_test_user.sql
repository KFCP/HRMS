-- SQL to insert a test user with a BCrypt encoded password.
-- The password is 'password'.
-- IMPORTANT: Ensure the 'users' table 'password' column is VARCHAR(72) or similar to hold BCrypt hashes.
-- DELETE FROM users WHERE username = 'admin'; -- Optional: delete if admin user already exists
INSERT INTO users (username, password) 
VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'); 
-- This is a sample BCrypt hash for 'password'. 
-- For different passwords or if this hash doesn't work (e.g. due to salt/version issues from different bcrypt libs),
-- a new hash should be generated using a BCrypt generator.
