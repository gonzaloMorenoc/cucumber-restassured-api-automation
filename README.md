# Cucumber RestAssured API Automation Framework

A comprehensive and scalable API test automation framework built with **Cucumber BDD**, **Rest Assured**, and **Java**. This framework demonstrates best practices for API testing using the [GoRest API](https://gorest.co.in) as a sample application.

## 🚀 Features

- **BDD Testing** - Gherkin scenarios for readable and maintainable test cases
- **REST API Testing** - Complete CRUD operations testing with Rest Assured
- **Clean Architecture** - Scalable and maintainable code structure following SOLID principles
- **CI/CD Integration** - Complete GitHub Actions workflow
- **Detailed Reporting** - HTML, JSON, and XML test reports
- **Dynamic Data Management** - Smart test data generation and cleanup
- **Error Handling** - Comprehensive validation and error scenario coverage
- **Configuration Management** - Environment-specific configurations

## 🛠️ Tech Stack

- **Java 11+** - Programming language
- **Maven** - Build and dependency management
- **Rest Assured** - API testing library
- **Cucumber** - BDD framework
- **JUnit 5** - Test execution platform
- **Jackson** - JSON processing
- **Owner** - Configuration management

## 📁 Project Structure

```
cucumber-restassured-api-automation/
├── src/
│   ├── main/
│   │   ├── java/com/apiautomation/
│   │   │   ├── client/          # API clients (UserClient, BaseClient)
│   │   │   ├── config/          # Configuration management
│   │   │   ├── models/          # POJO models (User, ErrorResponse)
│   │   │   └── utils/           # Utility classes (TestDataGenerator)
│   │   └── resources/
│   │       └── config.properties # Configuration file
│   └── test/
│       ├── java/com/apiautomation/
│       │   ├── context/         # Test context management
│       │   ├── hooks/           # Cucumber hooks
│       │   ├── runners/         # Test runners
│       │   └── stepdefinitions/ # Step definitions
│       └── resources/
│           ├── features/        # Gherkin feature files
│           └── schemas/         # JSON schemas for validation
├── .github/workflows/           # GitHub Actions CI/CD
├── target/                      # Build outputs and reports
├── pom.xml                      # Maven configuration
└── README.md                    # Project documentation
```

## 🏃‍♂️ Quick Start

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- GoRest API access token

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/cucumber-restassured-api-automation.git
   cd cucumber-restassured-api-automation
   ```

2. **Get API Access Token**
   - Visit [GoRest.co.in](https://gorest.co.in)
   - Sign up for a free account
   - Generate your access token

3. **Configure API Token**
   - Open `src/main/resources/config.properties`
   - Replace `YOUR_ACCESS_TOKEN_HERE` with your actual token:
   ```properties
   access.token=your_actual_token_here
   ```

4. **Install Dependencies**
   ```bash
   mvn clean install
   ```

5. **Run Tests**
   ```bash
   mvn clean test
   ```

## 🧪 Test Execution

### Quick Test Commands

We provide convenient scripts for running different test suites:

**Linux/Mac:**
```bash
# Make script executable
chmod +x scripts/run-tests.sh

# Run different test types
./scripts/run-tests.sh smoke      # Critical functionality
./scripts/run-tests.sh regression # Full test suite
./scripts/run-tests.sh api        # All API tests
./scripts/run-tests.sh get        # GET operations only
./scripts/run-tests.sh post       # POST operations only
./scripts/run-tests.sh delete     # DELETE operations only
./scripts/run-tests.sh negative   # Error scenarios
./scripts/run-tests.sh validation # Data validation tests
```

**Windows:**
```cmd
scripts\run-tests.bat smoke
scripts\run-tests.bat regression
scripts\run-tests.bat api
```

### Maven Commands

```bash
# Install dependencies
mvn clean install

# Run all tests
mvn clean test

# Run tests with specific tags
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@regression"
mvn test -Dcucumber.filter.tags="@api and @users"
mvn test -Dcucumber.filter.tags="@post or @delete"

# Run tests excluding certain tags
mvn test -Dcucumber.filter.tags="not @negative"

# Run with different profiles
mvn test -P smoke
mvn test -P regression

# Generate reports
mvn surefire-report:report
```

## 📊 Test Reports and Logging

### Generated Reports
After test execution, multiple report formats are available:

- **HTML Report**: `target/cucumber-reports/index.html` - Interactive web report
- **JSON Report**: `target/cucumber-reports/Cucumber.json` - Machine-readable format  
- **XML Report**: `target/cucumber-reports/Cucumber.xml` - JUnit format
- **Surefire Report**: `target/site/surefire-report.html` - Maven surefire report

### Opening Reports
```bash
# Using scripts (automatically opens in browser)
./scripts/run-tests.sh report      # Linux/Mac
scripts\run-tests.bat report       # Windows

# Manual approach
open target/cucumber-reports/index.html      # Mac
xdg-open target/cucumber-reports/index.html  # Linux
start target/cucumber-reports/index.html     # Windows
```

### Logging Configuration
- **Console Logging**: Real-time test execution feedback
- **File Logging**: Detailed logs saved to `target/logs/api-tests.log`
- **Rest Assured Logging**: HTTP request/response details
- **Configurable Levels**: Adjust logging in `logback-test.xml`

### Log Files Location
```
target/
├── logs/
│   ├── api-tests.log           # Current test run
│   └── api-tests.2025-01-28.log # Historical logs
├── cucumber-reports/           # Cucumber reports
├── surefire-reports/          # JUnit/Surefire reports
└── site/                      # Maven site reports
```

## 🔧 Configuration Management

### Environment Configuration
The framework supports multiple environment configurations:

**Main Configuration** (`src/main/resources/config.properties`):
```properties
base.url=https://gorest.co.in/public/v2
access.token=YOUR_ACCESS_TOKEN_HERE
timeout=30
```

**Test Configuration** (`src/test/resources/config-test.properties`):
```properties
# Test-specific settings
test.data.cleanup.enabled=true
test.parallel.enabled=false
test.retry.count=2
api.rate.limit.delay=1000
```

**Production Configuration** (`src/main/resources/environments/config-prod.properties`):
```properties
# Production settings
base.url=https://gorest.co.in/public/v2
timeout=60
retry.attempts=3
rate.limit.enabled=true
```

### Environment-Specific Execution
```bash
# Use different configuration files
mvn test -Dconfig.file=config-test.properties
mvn test -Dconfig.file=environments/config-prod.properties

# Override specific properties
mvn test -Dbase.url=https://custom-api.com/v2
mvn test -Daccess.token=your_custom_token
mvn test -Dtimeout=60

# Multiple overrides
mvn test -Dbase.url=https://staging-api.com -Dtimeout=45
```

### Configuration Priority
1. System properties (`-Dkey=value`)
2. Environment-specific config files
3. Default `config.properties`
4. Hardcoded defaults in Config interface

## 🚀 CI/CD Integration

The project includes GitHub Actions workflow (`.github/workflows/ci.yml`) that:

- Runs on push/PR to main branch
- Tests against Java 11 and 17
- Executes daily scheduled runs
- Generates and uploads test reports
- Posts results to PR comments

### Required Secrets

Add these secrets to your GitHub repository:
- `GOREST_ACCESS_TOKEN` - Your GoRest API access token

## 🎯 Usage Examples

### Running Specific Features
```bash
# Run only GET operations
mvn test -Dcucumber.filter.tags="@get"

# Run only POST operations  
mvn test -Dcucumber.filter.tags="@post"

# Run only DELETE operations
mvn test -Dcucumber.filter.tags="@delete"
```

## 🏷️ Test Tags Organization

Our tests are organized using Cucumber tags for flexible execution:

### Feature Tags
- `@api` - All API-related tests
- `@users` - User management tests
- `@get` - GET operation tests
- `@post` - POST operation tests  
- `@delete` - DELETE operation tests

### Quality Tags
- `@smoke` - Critical functionality tests (run first)
- `@regression` - Comprehensive test suite
- `@negative` - Error scenario tests
- `@validation` - Data validation tests
- `@auth` - Authentication tests

### Additional Tags
- `@pagination` - Pagination-related tests
- `@search` - Search functionality tests
- `@data-driven` - Data-driven test scenarios
- `@bulk` - Bulk operation tests
- `@idempotency` - Idempotency tests

### Tag Combinations
```bash
# Run smoke tests for users API
mvn test -Dcucumber.filter.tags="@smoke and @users"

# Run all POST tests except validation
mvn test -Dcucumber.filter.tags="@post and not @validation"

# Run negative tests for all operations
mvn test -Dcucumber.filter.tags="@negative and (@get or @post or @delete)"
```

## 🧪 Test Scenarios Coverage

### GET Operations
- ✅ Retrieve all users with pagination
- ✅ Get specific user by ID  
- ✅ Search users by name
- ✅ Handle non-existent user requests
- ✅ Validate response structure

### POST Operations  
- ✅ Create user with valid data
- ✅ Data-driven user creation
- ✅ Validate required fields
- ✅ Handle duplicate email scenarios
- ✅ Test authentication requirements
- ✅ Invalid data format validation

### DELETE Operations
- ✅ Delete existing users
- ✅ Handle non-existent user deletion
- ✅ Test authentication requirements
- ✅ Bulk deletion scenarios
- ✅ Idempotency verification

## 🧩 Extending the Framework

### Adding New API Endpoints

1. **Create Model Class**
   ```java
   // src/main/java/com/apiautomation/models/Post.java
   public class Post {
       private Integer id;
       private String title;
       private String body;
       // getters and setters
   }
   ```

2. **Create Client Class**
   ```java
   // src/main/java/com/apiautomation/client/PostClient.java
   public class PostClient {
       public Response getAllPosts() {
           return given().spec(BaseClient.getRequestSpec())
                   .when().get("/posts");
       }
   }
   ```

3. **Add Feature File**
   ```gherkin
   # src/test/resources/features/Posts.feature
   Feature: Posts API
     Scenario: Get all posts
       When I request all posts
       Then I should receive posts list
   ```

4. **Implement Step Definitions**
   ```java
   // src/test/java/com/apiautomation/stepdefinitions/PostSteps.java
   @When("I request all posts")
   public void iRequestAllPosts() {
       // implementation
   }
   ```

## 📈 Best Practices Implemented

- **Page Object Pattern** - Organized API clients
- **Data Driven Testing** - Parameterized scenarios  
- **Clean Code** - SOLID principles applied
- **Error Handling** - Comprehensive validation
- **Logging** - Detailed test execution logs
- **Reporting** - Multiple report formats
- **Configuration Management** - Environment-specific configs

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 🏆 Acknowledgments

- [GoRest API](https://gorest.co.in) for providing free testing API
- [Rest Assured](https://rest-assured.io/) community
- [Cucumber](https://cucumber.io/) BDD framework
- Open source testing community

---

**Happy Testing! 🧪✨**