# API Automation Framework

API test automation framework using Cucumber BDD, Rest Assured, and Java. Tests the [GoRest API](https://gorest.co.in).

## Tech Stack

- Java 11+
- Maven
- Rest Assured
- Cucumber
- JUnit 5

## Project Structure

```
src/
├── main/java/com/apiautomation/
│   ├── client/          # API clients
│   ├── config/          # Configuration
│   ├── models/          # Data models
│   └── utils/           # Utilities
└── test/
    ├── java/com/apiautomation/
    │   ├── stepdefinitions/ # Step definitions
    │   ├── runners/         # Test runners
    │   └── context/         # Test context
    └── resources/
        ├── features/        # Gherkin scenarios
        └── schemas/         # JSON schemas
```

## Setup

1. Clone the repository
2. Get your GoRest API token from [gorest.co.in](https://gorest.co.in)
3. Update `src/main/resources/config.properties`:
   ```properties
   access.token=your_actual_token_here
   ```
4. Install dependencies:
   ```bash
   mvn clean install
   ```

## Running Tests

### Using scripts
```bash
# Linux/Mac
./scripts/run-tests.sh smoke
./scripts/run-tests.sh regression
./scripts/run-tests.sh get

# Windows
scripts\run-tests.bat smoke
scripts\run-tests.bat regression
```

### Using Maven
```bash
# All tests
mvn clean test

# Specific tags
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@get"
mvn test -Dcucumber.filter.tags="@negative"
```

## Test Tags

- `@smoke` - Critical tests
- `@regression` - Full test suite
- `@get` / `@post` / `@delete` - Operation types
- `@negative` - Error scenarios
- `@validation` - Data validation

## Reports

After running tests, check:
- HTML Report: `target/cucumber-reports/index.html`
- JSON Report: `target/cucumber-reports/Cucumber.json`

Open reports:
```bash
./scripts/run-tests.sh report
```

## Configuration

### Environment variables
```bash
export ACCESS_TOKEN=your_token
mvn test
```

### Different environments
```bash
mvn test -Dconfig.file=config-test.properties
mvn test -Dbase.url=https://custom-api.com
```

## Test Coverage

### GET Operations
- Get all users
- Get user by ID
- Search users
- Pagination
- Error handling

### POST Operations
- Create users
- Data validation
- Authentication
- Error scenarios

### DELETE Operations
- Delete users
- Non-existent users
- Authentication
- Bulk operations

## CI/CD

GitHub Actions workflow runs tests automatically on push/PR. Add this secret to your repository:
- `GOREST_ACCESS_TOKEN` - Your API token

## Adding New Tests

1. Create feature file in `src/test/resources/features/`
2. Add step definitions in `src/test/java/com/apiautomation/stepdefinitions/`
3. Update API client if needed in `src/main/java/com/apiautomation/client/`

## Common Issues

**Tests failing with 401?** Check your API token in config.properties

**No reports generated?** Make sure target directories exist:
```bash
mkdir -p target/cucumber-reports
```

**Rate limiting?** Add delays in test execution or use fewer parallel tests

## Contributing

1. Fork the repo
2. Create feature branch
3. Add tests for new features
4. Ensure all tests pass
5. Submit pull request