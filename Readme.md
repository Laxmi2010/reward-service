# Retailer Rewards Program API

## Project Overview
A Spring Boot REST API that calculates reward points for customers based on their transaction history.

### Business Rules:
- A customer receives 2 points for every dollar spent over $100 in each transaction.
- A customer receives 1 point for every dollar spent between $50 and $100 in each transaction.

## Architecture
- *Controller:* Exposes a single GET /api/rewards/getRewards endpoint that utilizes a pre-populated dataset to demonstrate the solution.
- *Service:* Handles the core business logic for point calculation and uses java.time.YearMonth to dynamically map transactions to months without hardcoding.
- *Exception Handling:* Global @ControllerAdvice gracefully catches errors and returns structured JSON 400 Bad Request responses.
- *Testing:* Comprehensive JUnit tests cover point calculations, data aggregation, and negative edge-case scenarios.

## Use below request body for Post API "/calculate" 
```json
[
  {
    "customerId": 1,
    "amount": 120.00,
    "transactionDate": "2023-01-15"
  },
  {
    "customerId": 1,
    "amount": 80.00,
    "transactionDate": "2023-02-20"
  }
]
```

## How to Run
```bash
mvn clean install
mvn spring-boot:run
