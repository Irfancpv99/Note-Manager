# Note Manager

A Java Swing desktop application for managing notes with category organization, built using Test-Driven Development (TDD) practices.

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
- Docker (for integration tests)
- MongoDB 6.0 (or use Docker)

## Build & Run

```bash
# Run unit tests
mvn test

# Run all tests (unit + integration + E2E)
mvn verify

# Run mutation testing
mvn org.pitest:pitest-maven:mutationCoverage

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
