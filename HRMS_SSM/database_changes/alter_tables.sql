-- SQL ALTER TABLE Statements for HRMS Database (MySQL)

-- Assumptions:
-- 1. For the `users` table:
--    - The `users` table already exists.
--    - The `password` column already exists.
--    - The `password` column was previously something like VARCHAR(255) or another VARCHAR type.
--      If it was a different data type, the ALTER statement might need adjustment or could fail.
--    - We are making it NOT NULL. If there are existing rows with NULL passwords, this ALTER will fail
--      unless those are updated first or a DEFAULT is provided in the ALTER (not specified in requirements).

-- 2. For the `employees` table:
--    - The `employees` table already exists.
--    - There is no existing column named `salary`. If a `salary` column already exists, this
--      `ADD COLUMN` statement will fail.

-- --- `users` table modifications ---

-- Modify the `password` column to VARCHAR(72) and NOT NULL
ALTER TABLE users
MODIFY COLUMN password VARCHAR(72) NOT NULL;

-- --- `employees` table modifications ---

-- Add a new column `salary` with type DECIMAL(10, 2) and a default value
ALTER TABLE employees
ADD COLUMN salary DECIMAL(10, 2) DEFAULT 0.00;

-- Note: If the `salary` column should not allow NULLs and always have a value,
-- you might want to make it NOT NULL as well:
-- ALTER TABLE employees
-- ADD COLUMN salary DECIMAL(10, 2) NOT NULL DEFAULT 0.00;
-- However, the requirement was "allow it to have a default value",
-- which doesn't strictly mean it must be NOT NULL.
-- The chosen statement allows NULLs but will insert 0.00 if no value is provided on INSERT.
-- If a row is updated to set salary to NULL, it will be NULL.
-- If the intention is that it should always be a value and default to 0.00 if not specified,
-- then `NOT NULL DEFAULT 0.00` would be more appropriate.
-- For now, sticking to the interpretation that allows NULLs but defaults on insert.
