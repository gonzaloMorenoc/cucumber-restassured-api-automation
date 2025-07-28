@echo off
REM Cucumber RestAssured API Automation Test Runner for Windows
REM This script provides easy commands to run different types of tests

setlocal enabledelayedexpansion

REM Check if Maven is installed
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Maven is not installed or not in PATH
    exit /b 1
)

REM Function to display help
if "%1"=="" goto show_help
if "%1"=="help" goto show_help
if "%1"=="--help" goto show_help
if "%1"=="-h" goto show_help

REM Process command line arguments
if "%1"=="smoke" goto run_smoke
if "%1"=="regression" goto run_regression  
if "%1"=="api" goto run_api
if "%1"=="users" goto run_users
if "%1"=="get" goto run_get
if "%1"=="post" goto run_post
if "%1"=="delete" goto run_delete
if "%1"=="negative" goto run_negative
if "%1"=="validation" goto run_validation
if "%1"=="auth" goto run_auth
if "%1"=="all" goto run_all
if "%1"=="clean" goto clean_project
if "%1"=="install" goto install_deps
if "%1"=="report" goto generate_report

echo [ERROR] Unknown option: %1
goto show_help

:show_help
echo Cucumber RestAssured API Automation Test Runner
echo.
echo Usage: run-tests.bat [OPTION]
echo.
echo Options:
echo   smoke         Run smoke tests (@smoke tag)
echo   regression    Run regression tests (@regression tag)
echo   api           Run all API tests (@api tag)
echo   users         Run user-related tests (@users tag)
echo   get           Run GET operation tests (@get tag)
echo   post          Run POST operation tests (@post tag)
echo   delete        Run DELETE operation tests (@delete tag)
echo   negative      Run negative test cases (@negative tag)
echo   validation    Run validation tests (@validation tag)
echo   auth          Run authentication tests (@auth tag)
echo   all           Run all tests
echo   clean         Clean target directory
echo   install       Install dependencies
echo   report        Generate and open test report
echo   help          Show this help message
echo.
echo Examples:
echo   run-tests.bat smoke
echo   run-tests.bat regression
echo   run-tests.bat get
echo   run-tests.bat negative
goto end

:run_smoke
echo [INFO] Running smoke tests...
mvn clean test -Dcucumber.filter.tags="@smoke"
if %errorlevel% equ 0 (
    echo [SUCCESS] Smoke tests completed successfully!
) else (
    echo [ERROR] Smoke tests failed!
    exit /b 1
)
goto end

:run_regression
echo [INFO] Running regression tests...
mvn clean test -Dcucumber.filter.tags="@regression"
if %errorlevel% equ 0 (
    echo [SUCCESS] Regression tests completed successfully!
) else (
    echo [ERROR] Regression tests failed!
    exit /b 1
)
goto end

:run_api
echo [INFO] Running API tests...
mvn clean test -Dcucumber.filter.tags="@api"
if %errorlevel% equ 0 (
    echo [SUCCESS] API tests completed successfully!
) else (
    echo [ERROR] API tests failed!
    exit /b 1
)
goto end

:run_users
echo [INFO] Running user tests...
mvn clean test -Dcucumber.filter.tags="@users"
if %errorlevel% equ 0 (
    echo [SUCCESS] User tests completed successfully!
) else (
    echo [ERROR] User tests failed!
    exit /b 1
)
goto end

:run_get
echo [INFO] Running GET operation tests...
mvn clean test -Dcucumber.filter.tags="@get"
if %errorlevel% equ 0 (
    echo [SUCCESS] GET tests completed successfully!
) else (
    echo [ERROR] GET tests failed!
    exit /b 1
)
goto end

:run_post
echo [INFO] Running POST operation tests...
mvn clean test -Dcucumber.filter.tags="@post"
if %errorlevel% equ 0 (
    echo [SUCCESS] POST tests completed successfully!
) else (
    echo [ERROR] POST tests failed!
    exit /b 1
)
goto end

:run_delete
echo [INFO] Running DELETE operation tests...
mvn clean test -Dcucumber.filter.tags="@delete"
if %errorlevel% equ 0 (
    echo [SUCCESS] DELETE tests completed successfully!
) else (
    echo [ERROR] DELETE tests failed!
    exit /b 1
)
goto end

:run_negative
echo [INFO] Running negative test cases...
mvn clean test -Dcucumber.filter.tags="@negative"
if %errorlevel% equ 0 (
    echo [SUCCESS] Negative tests completed successfully!
) else (
    echo [ERROR] Negative tests failed!
    exit /b 1
)
goto end

:run_validation
echo [INFO] Running validation tests...
mvn clean test -Dcucumber.filter.tags="@validation"
if %errorlevel% equ 0 (
    echo [SUCCESS] Validation tests completed successfully!
) else (
    echo [ERROR] Validation tests failed!
    exit /b 1
)
goto end

:run_auth
echo [INFO] Running authentication tests...
mvn clean test -Dcucumber.filter.tags="@auth"
if %errorlevel% equ 0 (
    echo [SUCCESS] Authentication tests completed successfully!
) else (
    echo [ERROR] Authentication tests failed!
    exit /b 1
)
goto end

:run_all
echo [INFO] Running all tests...
mvn clean test
if %errorlevel% equ 0 (
    echo [SUCCESS] All tests completed successfully!
) else (
    echo [ERROR] Some tests failed!
    exit /b 1
)
goto end

:clean_project
echo [INFO] Cleaning project...
mvn clean
echo [SUCCESS] Project cleaned successfully!
goto end

:install_deps
echo [INFO] Installing dependencies...
mvn clean install
if %errorlevel% equ 0 (
    echo [SUCCESS] Dependencies installed successfully!
) else (
    echo [ERROR] Failed to install dependencies!
    exit /b 1
)
goto end

:generate_report
echo [INFO] Generating test report...
mvn surefire-report:report
if exist "target\cucumber-reports\index.html" (
    echo [SUCCESS] Report generated successfully!
    echo [INFO] Opening report in browser...
    start target\cucumber-reports\index.html
) else (
    echo [WARNING] Report not found. Please run tests first.
    echo [INFO] You can find the report at: target\cucumber-reports\index.html
)
goto end

:end
endlocal