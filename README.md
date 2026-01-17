# Note Manager

A Java Swing desktop application for managing notes with category organization, built using Test-Driven Development (TDD) practices.

## Metric 

| Metric | Status |
|------|------|
| Build Status | [![Build Status](https://github.com/Irfancpv99/Note-Manager/actions/workflows/maven.yml/badge.svg)](https://github.com/Irfancpv99/Note-Manager/actions) |
| Quality Gate | [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |
| Test Coverage | <a href="https://coveralls.io/github/Irfancpv99/Note-Manager?branch=main"><img src="https://coveralls.io/repos/github/Irfancpv99/Note-Manager/badge.svg?branch=main" alt="Coverage Status" /></a> |
| Security  | [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager)|
| Duplications | [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |
| Technical Debt|[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager)|
|Reliability |[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager)|
| Vulnerabilities | [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |
| Code Smells | [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |
| Bugs | [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Irfancpv99_Note-Manager&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Irfancpv99_Note-Manager) |


## Features

- Create, edit, and delete notes
- Organize notes by categories (Personal, Work, Study)
- MongoDB persistence
- Clean MVC architecture

## Tech Stack

- **Java 17**
- **MongoDB** - Data persistence
- **Swing** - GUI framework
- **Maven** - Build tool

### Testing & Quality

- JUnit 5 & JUnit 4 (for Swing tests)
- Mockito - Mocking framework
- AssertJ & AssertJ Swing - Assertions
- JaCoCo - Code coverage
- PIT - Mutation testing
- SonarCloud - Code quality analysis
- Coveralls - Code Coverage

## Requirements

- Java 17+
- Maven 3.6+
- Docker 
- MongoDB 6.0

## Build & Run

```bash
# Run unit tests
mvn clean test

# Run all tests (unit + integration + E2E)
mvn clean verify

# Run mutation testing
mvn pitest:mutationCoverage

# Build and run the application
mvn package
java -jar target/note-manager-1.0.0-jar-with-dependencies.jar
```

## Project Structure

```
src/
├── main/java/com/notemanager/
│   ├── app/           # Application entry point
│   ├── controller/    # MVC controllers
│   ├── model/         # Domain models (Note, Category)
│   ├── repository/    # Data access layer
│   ├── service/       # Business logic
│   └── view/          # Swing GUI
├── test/java/         # Unit tests
├── it/java/           # Integration tests
└── e2e/java/          # End-to-end tests
```

## CI/CD

GitHub Actions workflow runs:
- Build and test with GUI support (xvfb)
- Mutation testing
- Coveralls coverage report
- SonarCloud analysis

## License

MIT
