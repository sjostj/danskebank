# Danske Bank

# Problem Description
We are a bank and would like to modernize our Account Management system in our Retail
Banking. We would like to use Micro Service architecture to replace the monolith application.
The system is supposed to contain the following functionalities:
- Create a Savings Account for the Customer
- Deposit money (e.g. 100$) to Account
- Withdraw money (e.g. 50$) from Account
- Read Available Balance
- List last 10 transactions of the Account 

# Back-end
You are expected to create a solution that exposes REST API’s for the mentioned
functionalities by using a test driven approach in a publicly shared GitHub repository.
The REST API must be able to handle exceptions such as invalid input, as well as business
validations from a common sense perspective.
Technical requirements
- Java 8 or Java 11
- Junit 5
- Spring-boot
- Maven/Gradle 

Initial entrypoint is dk.jost.danskebank.DanskebankApplication

Supported endpoints are
- /v1/accounts
  - GET
  - POST
- /v1/accounts/{id}
  - GET
- /v1/accounts/{id}/transactions
  - GET
  - POST