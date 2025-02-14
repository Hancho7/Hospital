

```markdown
# Hospital Management System

A comprehensive API-driven hospital management system that leverages AI for processing medical notes and automating patient reminders. The system streamlines doctor-patient relationships while ensuring secure data handling and efficient communication.

## Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

## Features
- Secure user authentication and authorization
- Doctor-patient relationship management
- AI-powered medical notes processing
- Automated patient reminders via email
- Real-time action item generation from doctor's notes
- Encrypted data storage for sensitive medical information
- Automated email verification system
- Scheduled reminder system for patient follow-ups

## Technology Stack
- **Backend Framework**: Spring Boot
- **Database**: PostgreSQL
- **Email Service**: Azure Communication Email
- **AI Processing**: Spring AI with OpenAI/Azure AI
- **Security**: JWT Authentication, AES-256 Encryption
- **Documentation**: Swagger/OpenAPI
- **Testing**: JUnit, Mockito

## Getting Started

### Prerequisites
- Java 17 or higher
- PostgreSQL 13+
- Maven 3.8+
- Azure account with active subscription
- IDE (recommended: IntelliJ IDEA or Eclipse)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Hancho7/hospital.git
   cd hospital
   ```

2. Create a PostgreSQL database:
   ```sql
   CREATE DATABASE hospital_management;
   ```

3. Configure environment variables:
   ```bash
   cp .env.example .env
   # Edit .env with your credentials
   ```

4. Build the project:
   ```bash
   mvn clean install
   ```

5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Documentation

### Authentication Endpoints

#### Register User
```
POST /api/v1/auth/signup
```
Register a new patient or doctor account.

Request Body:
```json
{
  "name": "John Doe",
  "email": "johndoe@example.com",
  "password": "securePassword",
  "userType": "PATIENT"  // or "DOCTOR"
}
```

Response:
```json
{
  "message": "User registered successfully. Check your email for verification."
}
```

#### Login
```
POST /api/v1/auth/login
```
Authenticate a user and receive a JWT token.

Request Body:
```json
{
  "email": "johndoe@example.com",
  "password": "securePassword"
}
```

Response:
```json
{
  "token": "jwt_token_here",
  "expiresIn": 3600
}
```

### Doctor Endpoints

#### Get All Doctors
```
GET /api/v1/doctors
```
Retrieve a list of all registered doctors.

Response:
```json
[
  {
    "id": 1,
    "name": "Dr. Smith",
    "email": "drsmith@example.com",
    "specialization": "Cardiology"
  }
]
```

### Patient Endpoints

#### Select Doctor
```
POST /api/v1/patient/{patientId}/select-doctor/{doctorId}
```
Associate a patient with a doctor.

Response:
```json
{
  "message": "Doctor selected successfully"
}
```

### Notes Management

#### Create Note
```
POST /api/v1/notes
```
Create a new medical note.

Request Body:
```json
{
  "patientId": 1,
  "doctorId": 2,
  "content": "Patient should monitor blood pressure daily."
}
```

Response:
```json
{
  "id": 10,
  "created": "2024-02-14T10:30:00Z",
  "status": "PROCESSED"
}
```

## Configuration

### Environment Variables
```properties
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/hospital_management
DB_USERNAME=your_username
DB_PASSWORD=your_password

# Azure Services
AZURE_OPENAI_KEY=your_azure_key
AZURE_OPENAI_ENDPOINT=your_azure_endpoint
AZURE_COMMUNICATION_STRING=your_communication_string
AZURE_COMMUNICATION_SENDER=sender@yourdomain.com

# Application Settings
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=3600
ENCRYPTION_KEY=your_encryption_key
```

### Application Properties
Create `application.yml` in `src/main/resources`:

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 8080
  servlet:
    context-path: /api/v1
```

## Development

### Building
```bash
# Run tests
mvn test

# Build package
mvn package

# Build and skip tests
mvn package -DskipTests
```

### Testing
```bash
# Run unit tests
mvn test

# Run integration tests
mvn verify
```

### Code Style
This project follows the Google Java Style Guide. A formatter configuration file is provided in the repository.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Coding Standards
- Write unit tests for new features
- Update documentation for API changes
- Follow existing code style and conventions
- Keep commits atomic and write meaningful commit messages

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Support

For support and queries, please open an issue in the GitHub repository or contact the maintenance team at support@yourdomain.com.

---

Built by Jose Jefferson
```
