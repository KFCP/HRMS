# HRMS-SSM (Human Resource Management System - Spring/SpringMVC/MyBatis)

This project is a Human Resource Management System upgraded from an older JSP-based application to the SSM (Spring, SpringMVC, MyBatis) framework. It includes features for managing employees, positions, and basic user authentication.

## Project Structure

The project follows a standard Maven web application structure:

-   `HRMS_SSM/`
    -   `pom.xml`: Maven project configuration, dependencies (commented for clarity).
    -   `src/main/java/`: Java source code
        -   `com/example/hrms/config/`: Spring Security configuration (`SecurityConfig.java`, commented).
        -   `com/example/hrms/controller/`: Spring MVC controllers.
        -   `com/example/hrms/mapper/`: MyBatis mapper interfaces.
        -   `com/example/hrms/model/`: POJO data models (`User.java` implements `UserDetails`).
        -   `com.example.hrms/service/`: Service layer interfaces.
        -   `com.example/hrms/service/impl/`: Service layer implementations.
    -   `src/main/resources/`: Configuration files and MyBatis mappers
        -   `com/example/hrms/mapper/xml/`: MyBatis XML mapper files.
        -   `db.properties`: **CRITICAL: Database connection details (Must be configured by user).** Well-commented.
        -   `logback.xml`: Logging configuration.
        -   `mybatis-config.xml`: MyBatis global configuration.
    -   `src/main/webapp/`: Web application resources
        -   `css/`: CSS stylesheets (`style.css`).
        -   `WEB-INF/`:
            -   `jsp/`: JSP view files and common fragments (`header.jspf`, `footer.jspf`).
            -   `spring/`: Spring XML configuration files (`applicationContext.xml`, `spring-mvc-config.xml`, both well-commented).
            -   `web.xml`: Web application deployment descriptor (well-commented).
    -   `database_changes/`: SQL scripts for database setup.
        -   `alter_tables.sql`: Modifies `users.password` column and adds `employees.salary` column.
        -   `insert_test_user.sql`: Inserts a test 'admin' user with an encoded password.
    -   `MANUAL_TESTING_GUIDE.md`: Guide for manually testing the application.

## 1. Database Setup (Crucial First Step)

Getting the database right is key to running this application.

1.  **Create the Database:**
    *   Ensure you have MySQL server installed and running.
    *   Connect to your MySQL server using a client (e.g., MySQL Workbench, DBeaver, command line).
    *   Create the database. The default name used in `db.properties` is `hrms_db`.
        ```sql
        CREATE DATABASE hrms_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
        ```
        *Using `utf8mb4` is recommended for broader character support.*

2.  **Establish Initial Schema:**
    *   This project assumes you might have an initial schema for `users`, `positions`, and `employees` tables from a previous version or that you will create them.
    *   **If you DO NOT have these tables, you'll need to create them manually.** Basic structures are implied by the MyBatis mappers and POJOs (e.g., `users(id INT PK, username VARCHAR, password VARCHAR)`, `positions(id INT PK, position_name VARCHAR, level INT)`, `employees(id INT PK, name VARCHAR, ..., position_id INT FK, salary DECIMAL)`). *A future improvement would be to provide a full DDL script.*

3.  **Apply Schema Modifications (Mandatory):**
    *   Run the SQL script found in `HRMS_SSM/database_changes/alter_tables.sql`. This script:
        *   Modifies the `users.password` column to `VARCHAR(72)` to store BCrypt hashed passwords.
        *   Adds a `salary DECIMAL(10, 2)` column to the `employees` table.
    *   **Execute this script against your `hrms_db` database.**

4.  **Configure Database Connection Properties:**
    *   Open `HRMS_SSM/src/main/resources/db.properties`.
    *   Carefully update `db.url`, `db.username`, and `db.password` to match your local MySQL setup. Detailed comments in this file will guide you.

5.  **Insert Test User (Recommended for Initial Testing):**
    *   Run the SQL script `HRMS_SSM/database_changes/insert_test_user.sql`. This creates a user:
        *   Username: `admin`
        *   Password: `password` (the script contains a BCrypt hash for "password").
    *   **BCrypt Hash Note:** The provided hash `$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy` should work. If login fails with "Bad credentials", this hash might not be compatible with the BCrypt library version used by Spring Security in your environment (though unlikely with standard setups). You can generate a new BCrypt hash for "password" using:
        *   An online BCrypt generator.
        *   A simple Java program using `new BCryptPasswordEncoder().encode("password")`.
        Update the hash in `insert_test_user.sql` if needed.

## 2. Build Instructions

This project uses Apache Maven.

1.  **Prerequisites:**
    *   Java Development Kit (JDK) 1.8 or higher installed (check `JAVA_HOME` environment variable).
    *   Apache Maven installed and configured (check `M2_HOME` and `PATH`).
2.  **Build the WAR file:**
    *   Open a terminal or command prompt.
    *   Navigate to the root directory of the `HRMS_SSM` project (where `pom.xml` is located).
    *   Run:
        ```bash
        mvn clean package
        ```
    *   This command cleans previous builds, compiles the code, and packages the application into a `.war` file.
    *   The resulting WAR file will be in the `HRMS_SSM/target/` directory (e.g., `HRMS_SSM.war`).

## 3. Deployment Instructions

1.  **Servlet Container:**
    *   You need a Servlet 3.1+ compatible container like Apache Tomcat (8.5+ recommended) or Jetty. Ensure it's installed and running.
2.  **Deploy WAR:**
    *   Copy `HRMS_SSM.war` from `HRMS_SSM/target/` to your servlet container's deployment directory (e.g., `CATALINA_HOME/webapps/` for Tomcat).
    *   Tomcat usually auto-deploys new WAR files. If not, you might need to restart Tomcat or use its manager application to deploy.
3.  **Verify Deployment:**
    *   Check Tomcat's logs (e.g., `CATALINA_HOME/logs/catalina.out` and other localhost logs) for:
        *   Spring framework initialization messages (look for Spring's ASCII art logo).
        *   MyBatis initialization messages.
        *   Any errors during startup (e.g., "SEVERE: Context [/HRMS_SSM] startup failed"). These often point to configuration issues.
4.  **Access Application:**
    *   Open your web browser to: `http://localhost:8080/HRMS_SSM/`
        *(Adjust URL if your Tomcat runs on a different port or the application context path is different from `HRMS_SSM`)*.
    *   You should see the application's home page.

## 4. Manual Testing

*   Refer to `MANUAL_TESTING_GUIDE.md` for detailed steps on testing application features, including login, employee management, and position management.

## 5. Troubleshooting Common Local Deployment Issues

*   **404 Errors (Page Not Found):**
    *   Double-check the URL you are using. Is the context path (`/HRMS_SSM`) correct?
    *   Verify controller mappings in `com.example.hrms.controller` classes.
    *   Ensure JSP files are correctly named and located in `/WEB-INF/jsp/`.
    *   Check Tomcat logs for errors during deployment or request processing.
*   **Database Connection Errors:**
    *   Verify `db.properties` settings: URL, username, password, database name.
    *   Ensure your MySQL server is running and accessible.
    *   Check that the MySQL JDBC driver (`mysql-connector-java`) is correctly listed in `pom.xml` (it is).
    *   Look for "Cannot create PoolableConnectionFactory" or similar errors in server logs.
*   **Login Fails ("Bad credentials"):**
    *   Ensure you've run `alter_tables.sql` to modify the password column.
    *   Ensure you've run `insert_test_user.sql` or manually inserted a user with a BCrypt-encoded password.
    *   The test user is `admin` / `password`. If you changed the password or re-hashed it, use your new credentials.
    *   Verify the `PasswordEncoder` in `SecurityConfig.java` is `BCryptPasswordEncoder`.
*   **Character Encoding Issues (Garbled Text):**
    *   The `web.xml` includes a `CharacterEncodingFilter` set to UTF-8. This should handle most cases.
    *   Ensure your database and tables are also using UTF-8 (or `utf8mb4`). The `CREATE DATABASE` example above includes this.
    *   If using Tomcat, you can add `URIEncoding="UTF-8"` to the `<Connector>` element in Tomcat's `server.xml` for GET request parameters, though Spring's filter should manage request body encoding.
*   **Spring Bean Creation Errors (Visible in Server Logs):**
    *   Often caused by incorrect component scanning, missing dependencies, or issues in XML/Java configuration.
    *   Read the error messages carefully in the server logs; they usually point to the problematic bean or configuration file.
    *   Ensure all necessary annotations (`@Service`, `@Controller`, `@Repository` (if used), `@Autowired`) are in place.
*   **CSS or Static Resources Not Loading:**
    *   Verify the `<mvc:resources ... />` mappings in `spring-mvc-config.xml`.
    *   Check that your CSS/JS files are in the correct location (e.g., `/webapp/css/`).
    *   Use browser developer tools (Network tab) to see if these resources are giving 404 errors.

---
This updated README aims to be more comprehensive for new users.
```
