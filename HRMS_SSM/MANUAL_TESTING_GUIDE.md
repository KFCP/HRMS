# HRMS-SSM Manual Testing Guide

This guide outlines the steps to manually test the HRMS-SSM application after deployment.

## Prerequisites
1.  The application is deployed to a Servlet container (e.g., Tomcat).
2.  The database schema has been updated:
    *   `users.password` column is `VARCHAR(72)`.
    *   `employees.salary` column (`DECIMAL(10,2)`) exists.
3.  The test user has been inserted (see `database_changes/insert_test_user.sql`). You might need to generate a new BCrypt hash for 'password' if the provided one doesn't work, using a local script or online tool.

## I. Authentication & Basic Navigation
1.  **Access Home Page (Not Logged In):**
    *   Open the application (e.g., `http://localhost:8080/HRMS_SSM/`).
    *   **Expected:** Home page (`index.jsp`) loads. Links for "Login" should be visible. Links for "员工管理", "职位管理", "用户管理", "退出" should NOT be visible or should be disabled.
2.  **Attempt to Access Protected Page (Not Logged In):**
    *   Try to navigate directly to `/employees`.
    *   **Expected:** Redirected to the login page (`/login`).
3.  **Login with Invalid Credentials:**
    *   Go to the login page.
    *   Enter incorrect username/password.
    *   Click "登录".
    *   **Expected:** Remain on login page. Error message "用户名或密码错误，请重试。" should appear.
4.  **Login with Valid Credentials:**
    *   Go to the login page.
    *   Enter username: `admin`, password: `password`.
    *   Click "登录".
    *   **Expected:** Redirected to the home page (`/index`). Welcome message "欢迎，admin!" should appear. Links for "员工管理", "职位管理", "用户管理", "退出 (admin)" should be visible. "Login" link should not be visible.
5.  **Access Protected Page (Logged In):**
    *   Click on "员工管理".
    *   **Expected:** Employee list page (`/employees`) loads successfully.
6.  **Logout:**
    *   On the home page or any page with the main menu, click "退出 (admin)". Confirm if prompted.
    *   **Expected:** Redirected to the login page (`/login?logout=true`). Message "您已成功退出。" should appear. Main menu should reflect logged-out state.
7.  **Verify CSS and Layout:**
    *   On all visited pages, check that the styling is applied correctly and the layout appears as intended (header, footer, main content area).

## II. Position Management (Logged In as Admin)
1.  **View Position List:**
    *   Navigate to "职位管理" (`/positions`).
    *   **Expected:** List of positions (if any) is displayed. Search bar is present.
2.  **Add New Position:**
    *   Click "添加新职位".
    *   Enter valid Position Name and Level. Click "添加职位".
    *   **Expected:** Redirected to position list. Success message "Position added successfully!" appears. New position is in the list.
    *   Try adding with empty name/level.
    *   **Expected:** Error message appears on the form page (or on list page if redirect occurs). Position is not added.
3.  **Search Positions:**
    *   Enter a known part of a position name in the search bar. Click "查询".
    *   **Expected:** Only matching positions are displayed.
    *   Clear search bar, click "查询".
    *   **Expected:** All positions are displayed.
4.  **Edit Position:**
    *   Click "编辑" for an existing position.
    *   Modify name and/or level. Click "更新职位".
    *   **Expected:** Redirected to position list. Success message "Position updated successfully!" appears. Changes are reflected.
    *   Try editing with empty name/level.
    *   **Expected:** Error message, changes not saved.
5.  **Delete Position:**
    *   Click "删除" for an existing position. Confirm the action.
    *   **Expected:** Redirected to position list. Success message "Position deleted successfully!" appears. Position is removed.
    *   (Consideration: Test deleting a position currently assigned to an employee - current setup might allow it or throw a database error if FK constraints are strict and not handled by service layer. Service layer should ideally prevent this.)

## III. Employee Management (Logged In as Admin)
1.  **View Employee List:**
    *   Navigate to "员工管理" (`/employees`).
    *   **Expected:** List of employees (if any) is displayed. Position name and formatted salary should be visible. Search bar is present.
2.  **Add New Employee:**
    *   Click "添加新员工".
    *   Fill in all required fields (Name, Gender, Age, Position, Salary). Click "添加员工".
    *   **Expected:** Redirected to employee list. Success message "Employee added successfully!" appears. New employee is in the list.
    *   Try adding with missing required fields or an invalid Position ID (if possible to select an invalid one - dropdown should prevent this).
    *   **Expected:** Error message. Employee not added.
3.  **Search Employees:**
    *   Enter a known part of an employee name in the search bar. Click "查询".
    *   **Expected:** Only matching employees are displayed.
4.  **Edit Employee:**
    *   Click "编辑" for an existing employee.
    *   Modify details (e.g., name, phone, email, position, salary). Click "更新员工".
    *   **Expected:** Redirected to employee list. Success message "Employee updated successfully!" appears. Changes are reflected.
5.  **Delete Employee:**
    *   Click "删除" for an existing employee. Confirm.
    *   **Expected:** Redirected to employee list. Success message "Employee deleted successfully!" appears. Employee is removed.

## IV. User Management (Placeholder - Logged In as Admin)
1.  **Access User Management Page:**
    *   Navigate to "用户管理" (`/users`).
    *   **Expected:** Page loads with placeholder text "User listing page - functionality to be fully implemented." or similar. Link to "添加新用户 (Placeholder)" is visible.
2.  **Access Add User Form (Placeholder):**
    *   Click "添加新用户 (Placeholder)".
    *   **Expected:** Form loads with placeholder text and security warnings about password handling.

## V. General Checks
1.  **Broken Links:** Click through various navigation links to ensure they lead to the correct pages.
2.  **Error Handling:** Try to cause errors (e.g., invalid IDs in URL if possible) to see if generic error pages or graceful error messages appear (full error page handling might not be implemented yet).
3.  **Session Persistence:** Log in, close browser tab (not entire browser), reopen tab and navigate to app.
    *   **Expected:** Still logged in (if session cookie persists).
4.  **Concurrent Logins:** (Optional) Log in with the same user in two different browsers.
    *   **Expected:** Both sessions work independently.
```
