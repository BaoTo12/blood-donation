# Blood Donation Management System

## Overview
A comprehensive Spring Boot application for managing blood donation activities, including donor registration, appointment scheduling, blood requests, health tracking, and more. The system is designed for hospitals, blood banks, and organizations to streamline the blood donation process.

## Features
- User registration, authentication, and role-based access control
- Donor and recipient management
- Appointment scheduling for blood donation
- Blood request and donation tracking
- Health information management for donors
- Blog and comment system for community engagement
- Email notifications (SendGrid integration)
- Report generation (Excel via Apache POI)
- Secure authentication with Spring Security and OAuth2
- Database migrations with Flyway

## Technology Stack
- **Java 21**
- **Spring Boot 3.5.0**
- **Spring Data JPA**
- **Spring Security & OAuth2**
- **MySQL** (database)
- **Flyway** (database migrations)
- **Lombok** (boilerplate code reduction)
- **MapStruct** (object mapping)
- **SendGrid** (email service)
- **Apache POI** (Excel report generation)
- **Maven** (build tool)

## Prerequisites
- Java 21 or higher
- Maven
- MySQL Server
- SendGrid account (for email notifications)

## Getting Started

### 1. Clone the repository
```bash
git clone <repository-url>
cd blood-donation
```

### 2. Configure the Database
- Create a MySQL database for the application.
- Edit `src/main/resources/application.properties` with your database credentials:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  ```

### 3. Configure Email (SendGrid)
- Add your SendGrid API key to `application.properties`:
  ```properties
  spring.sendgrid.api-key=YOUR_SENDGRID_API_KEY
  ```

### 4. Build and Run
```bash
mvn clean install
mvn spring-boot:run
```
The application will be available at [http://localhost:8080](http://localhost:8080).

## Project Structure
- `src/main/java/com/example/blood_donation/` - Main source code
  - `controller/` - REST controllers
  - `service/` - Business logic
  - `repository/` - Data access layer
  - `entity/` - JPA entities
  - `dto/` - Data transfer objects
  - `mapper/` - MapStruct mappers
  - `config/` - Configuration classes
  - `validation/` - Custom validation logic
- `src/main/resources/` - Configuration and static resources

## Database Migrations
- Flyway automatically applies migrations from `src/main/resources/db/` on startup.

## Testing
- Run tests with:
  ```bash
  mvn test
  ```

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request.

## License
This project is licensed under the MIT License. See the LICENSE file for details.
