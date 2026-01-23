# Note Manager

A simple desktop app for managing your notes with categories. Built with Java and Swing, following TDD practices.

## What It Does

- Create, edit, and delete notes
- Organize notes into categories (Personal, Work, Study)
- Stores everything in MongoDB
- Clean MVC architecture

## Tech Stack

- **Java 17** - Core language
- **MongoDB** - Database
- **Swing** - GUI
- **Maven** - Build tool

### Testing & Quality Tools

- JUnit 5 & JUnit 4 - Testing
- Mockito - Mocking
- AssertJ & AssertJ Swing - Assertions
- JaCoCo - Code coverage
- PIT - Mutation testing
- SonarCloud - Code quality
- Coveralls - Coverage tracking

## Project Status

| Metric | Badge |
|--------|-------|
| Build | [![Build Status](https://github.com/Irfancpv99/Note-Manager/actions/workflows/maven.yml/badge.svg)](https://github.com/Irfancpv99/Note-Manager/actions) |
| Quality | [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |
| Coverage | [![Coverage Status](https://coveralls.io/repos/github/Irfancpv99/Note-Manager/badge.svg?branch=main)](https://coveralls.io/github/Irfancpv99/Note-Manager?branch=main) |
| Security | [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |
| Reliability | [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |
| Duplications | [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |
| Tech Debt | [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |
| Vulnerabilities | [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |
| Code Smells | [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |
| Bugs | [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |

## What You Need

- Java 17 or higher
- Maven 3.6+
- Docker
- MongoDB 6.0

## Getting Started

### Running Tests
```bash
# Just unit tests
mvn clean test

# Everything (unit + integration + E2E)
mvn clean verify

# Mutation testing
mvn pitest:mutationCoverage
```

### Running the App
```bash
# Build it
mvn package

# Run it
java -jar target/note-manager-1.0.0-jar-with-dependencies.jar
```

## Project Structure
```
src/
├── main/java/com/notemanager/
│   ├── app/           # Main entry point
│   ├── controller/    # Controllers
│   ├── model/         # Note & Category models
│   ├── repository/    # Database access
│   ├── service/       # Business logic
│   └── view/          # Swing UI
├── test/java/         # Unit tests
├── it/java/           # Integration tests
└── e2e/java/          # End-to-end tests
```

## CI/CD

GitHub Actions runs on every push:
- Builds and tests (with headless GUI support)
- Mutation testing with PIT
- Coverage reports to Coveralls
- Code quality analysis via SonarCloud

## License

MIT

---
