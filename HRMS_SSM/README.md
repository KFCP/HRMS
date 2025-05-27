# HRMS-SSM (Human Resource Management System - Spring/SpringMVC/MyBatis)

This project is a Human Resource Management System upgraded from an older JSP-based application to the SSM (Spring, SpringMVC, MyBatis) framework.

## Project Structure

The project follows a standard Maven web application structure:

-   `HRMS_SSM/`
    -   `pom.xml`: Maven project configuration, dependencies.
    -   `src/main/java/`: Java source code
        -   `com/example/hrms/config/`: Spring Security configuration.
        -   `com/example/hrms/controller/`: Spring MVC controllers.
        -   `com/example/hrms/mapper/`: MyBatis mapper interfaces.
        -   `com/example/hrms/model/`: POJO data models (implementing UserDetails for User).
        -   `com/example/hrms/service/`: Service layer interfaces.
        -   `com/example/hrms/service/impl/`: Service layer implementations.
    -   `src/main/resources/`: Configuration files and MyBatis mappers
        -   `com/example/hrms/mapper/xml/`: MyBatis XML mapper files.
        -   `db.properties`: **IMPORTANT: Database connection details.**
        -   `logback.xml`: Logging configuration.
        -   `mybatis-config.xml`: MyBatis global configuration.
    -   `src/main/webapp/`: Web application resources
        -   `css/`: CSS stylesheets (`style.css`).
        -   `WEB-INF/`:
            -   `jsp/`: JSP view files and common fragments (`header.jspf`, `footer.jspf`).
            -   `spring/`: Spring XML configuration files (`applicationContext.xml`, `spring-mvc-config.xml`).
            -   `web.xml`: Web application deployment descriptor.
    -   `database_changes/`: SQL scripts for database setup.
        -   `alter_tables.sql`: Modifies `users.password` column and adds `employees.salary` column.
        -   `insert_test_user.sql`: Inserts a test 'admin' user with an encoded password.
    -   `MANUAL_TESTING_GUIDE.md`: Guide for manually testing the application.


## Database Setup and Changes

1.  **Initial Schema:** It's assumed you have an existing database schema for `users`, `positions`, and `employees` from the original AJSP project.
2.  **Apply Schema Modifications:**
    *   Before deploying the application, you **MUST** apply the SQL changes found in `database_changes/alter_tables.sql`. This script:
        *   Modifies the `users.password` column to `VARCHAR(72)` to store BCrypt hashed passwords.
        *   Adds a `salary DECIMAL(10, 2)` column to the `employees` table.
3.  **Configure Database Connection:**
    *   Edit `HRMS_SSM/src/main/resources/db.properties` with your actual database URL, username, and password.
4.  **Insert Test User (Optional but Recommended for Testing):**
    *   To test login, you can run the script `database_changes/insert_test_user.sql`. This will create a user `admin` with the password `password` (BCrypt encoded). If this specific hash does not work with your environment, you may need to generate a new BCrypt hash for "password" and update the script.

## Build Instructions

This project uses Maven.

1.  **Prerequisites:**
    *   Java Development Kit (JDK) 1.8 or higher installed.
    *   Apache Maven installed.
2.  **Build the WAR (Web Application Archive) file:**
    *   Open a terminal or command prompt.
    *   Navigate to the root directory of the `HRMS_SSM` project (where `pom.xml` is located).
    *   Run the following Maven command:
        ```bash
        mvn clean package
        ```
    *   This will compile the code, run any tests (none currently configured to run automatically), and package the application into a `.war` file.
    *   The WAR file will be located in the `HRMS_SSM/target/` directory (e.g., `HRMS_SSM.war`).

## Deployment Instructions

1.  **Deploy the WAR file** to a Servlet 3.1+ compatible container (e.g., Apache Tomcat 8.5+, Jetty).
    *   Copy the `HRMS_SSM.war` file from the `target/` directory to your servlet container's deployment directory (e.g., `webapps/` for Tomcat).
    *   Start your servlet container.
2.  **Access the Application:**
    *   Open your web browser and navigate to the application URL (e.g., `http://localhost:8080/HRMS_SSM/` if Tomcat is running on port 8080 and the WAR is named `HRMS_SSM.war`).

## Manual Testing

Refer to `MANUAL_TESTING_GUIDE.md` for detailed steps on how to test the application's features.
