#!/bin/bash

# Cucumber RestAssured API Automation Test Runner
# This script provides easy commands to run different types of tests

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to check if Maven is installed
check_maven() {
    if ! command -v mvn &> /dev/null; then
        print_error "Maven is not installed or not in PATH"
        exit 1
    fi
}

# Function to display help
show_help() {
    echo "Cucumber RestAssured API Automation Test Runner"
    echo ""
    echo "Usage: ./run-tests.sh [OPTION]"
    echo ""
    echo "Options:"
    echo "  smoke         Run smoke tests (@smoke tag)"
    echo "  regression    Run regression tests (@regression tag)"
    echo "  api           Run all API tests (@api tag)"
    echo "  users         Run user-related tests (@users tag)"
    echo "  get           Run GET operation tests (@get tag)"
    echo "  post          Run POST operation tests (@post tag)"
    echo "  delete        Run DELETE operation tests (@delete tag)"
    echo "  negative      Run negative test cases (@negative tag)"
    echo "  validation    Run validation tests (@validation tag)"
    echo "  auth          Run authentication tests (@auth tag)"
    echo "  all           Run all tests"
    echo "  clean         Clean target directory"
    echo "  install       Install dependencies"
    echo "  report        Generate and open test report"
    echo "  help          Show this help message"
    echo ""
    echo "Examples:"
    echo "  ./run-tests.sh smoke"
    echo "  ./run-tests.sh regression"
    echo "  ./run-tests.sh get"
    echo "  ./run-tests.sh negative"
}

# Function to run tests with specific tags
run_tests_with_tags() {
    local tags=$1
    local description=$2
    
    print_status "Running $description..."
    mvn clean test -Dcucumber.filter.tags="$tags"
    
    if [ $? -eq 0 ]; then
        print_success "$description completed successfully!"
    else
        print_error "$description failed!"
        exit 1
    fi
}

# Function to run all tests
run_all_tests() {
    print_status "Running all tests..."
    mvn clean test
    
    if [ $? -eq 0 ]; then
        print_success "All tests completed successfully!"
    else
        print_error "Some tests failed!"
        exit 1
    fi
}

# Function to clean project
clean_project() {
    print_status "Cleaning project..."
    mvn clean
    print_success "Project cleaned successfully!"
}

# Function to install dependencies
install_dependencies() {
    print_status "Installing dependencies..."
    mvn clean install
    
    if [ $? -eq 0 ]; then
        print_success "Dependencies installed successfully!"
    else
        print_error "Failed to install dependencies!"
        exit 1
    fi
}

# Function to generate and open report
generate_report() {
    print_status "Generating test report..."
    mvn surefire-report:report
    
    if [ -f "target/cucumber-reports/index.html" ]; then
        print_success "Report generated successfully!"
        print_status "Opening report in browser..."
        
        # Try to open report in default browser (works on most systems)
        if command -v xdg-open &> /dev/null; then
            xdg-open target/cucumber-reports/index.html
        elif command -v open &> /dev/null; then
            open target/cucumber-reports/index.html
        elif command -v start &> /dev/null; then
            start target/cucumber-reports/index.html
        else
            print_warning "Could not open report automatically."
            print_status "Please open: target/cucumber-reports/index.html"
        fi
    else
        print_warning "Report not found. Please run tests first."
    fi
}

# Main script logic
main() {
    # Check if Maven is available
    check_maven
    
    # Check if any argument is provided
    if [ $# -eq 0 ]; then
        print_error "No option provided."
        show_help
        exit 1
    fi
    
    # Process command line arguments
    case $1 in
        smoke)
            run_tests_with_tags "@smoke" "smoke tests"
            ;;
        regression)
            run_tests_with_tags "@regression" "regression tests"
            ;;
        api)
            run_tests_with_tags "@api" "API tests"
            ;;
        users)
            run_tests_with_tags "@users" "user tests"
            ;;
        get)
            run_tests_with_tags "@get" "GET operation tests"
            ;;
        post)
            run_tests_with_tags "@post" "POST operation tests"
            ;;
        delete)
            run_tests_with_tags "@delete" "DELETE operation tests"
            ;;
        negative)
            run_tests_with_tags "@negative" "negative test cases"
            ;;
        validation)
            run_tests_with_tags "@validation" "validation tests"
            ;;
        auth)
            run_tests_with_tags "@auth" "authentication tests"
            ;;
        all)
            run_all_tests
            ;;
        clean)
            clean_project
            ;;
        install)
            install_dependencies
            ;;
        report)
            generate_report
            ;;
        help|--help|-h)
            show_help
            ;;
        *)
            print_error "Unknown option: $1"
            show_help
            exit 1
            ;;
    esac
}

# Run main function with all arguments
main "$@"