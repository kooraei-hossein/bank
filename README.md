Simplified Banking System
=========================

This is a simplified banking system implemented in Kotlin using Spring Boot, Hibernate, and PostgreSQL. The system supports multiple user accounts and provides functionalities such as account creation, deposits, withdrawals, fund transfers, and balance inquiries. The application is designed with thread safety in mind, ensuring that concurrent transactions are handled effectively.

Table of Contents
-----------------

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Transaction Logging](#transaction-logging)
- [Concurrency and Thread Safety](#concurrency-and-thread-safety)
- [License](#license)

Features
--------

-   Create bank accounts with an initial balance.
-   Deposit and withdraw funds.
-   Transfer funds between accounts.
-   Check account balance.
-   Manage user information.
-   Thread-safe operations to handle concurrent transactions.
-   Transaction logging to a file.
-   RESTful API interface for easy interaction.

Technologies Used
-----------------

-   **Kotlin**: Programming language for implementing the application.
-   **Spring Boot**: Framework for building the application.
-   **Hibernate**: ORM framework for database interaction.
-   **PostgreSQL**: Database for data persistence.
-   **Java**: Version 17 used for development.
-   **Java ExecutorService**: For managing concurrent tasks.

Getting Started
---------------

### Prerequisites

-   **Java JDK 17**: Ensure you have JDK 17 installed.
-   **Maven**: Build and dependency management.
-   **PostgreSQL**: Database server for data persistence.

### Installation

1.  **Clone the repository**:

    `git clone https://github.com/kooraei-hossein/bank.git
    cd bank`

2.  **Configure PostgreSQL**:

    -   Create a PostgreSQL database and user for the application.
    -   Update the `application.properties` file with your database connection details.
3.  **Build the project**:

    `mvn clean install`

4.  **Run the application**:

    `mvn spring-boot:run`

5.  Access the application in your browser or through Postman at `http://localhost:8080`.

Usage
-----

You can interact with the banking system through the following API endpoints:

### API Endpoints

-   **Create a new account**:

    `POST /api/bank/create`

    **Request Body**:

    `{
        "accountHolderName": "John Doe",
        "balance": 1000.0,
        "userId": 1
    }`

-   **Deposit funds**:

    `POST /api/bank/deposit`

    **Request Body**:

    `{
        "accountNumber": "123456",
        "amount": 500.0
    }`

-   **Withdraw funds**:


    `POST /api/bank/withdraw`

    **Request Body**:

    `{
        "accountNumber": "123456",
        "amount": 200.0
    }`

-   **Transfer funds**:

    `POST /API/bank/transfer`

    **Request Body**:

    `{
        "fromAccount": "123456",
        "toAccount": "654321",
        "amount": 300.0
    }`

-   **Check balance**:


    `GET /api/bank/balance/{accountNumber}`

-   **User Management**:

    -   **Create a new user**:

        `POST /api/users`

        **Request Body**:

        `{
            "firstName": "John",
            "lastName": "Doe",
            "phone": "123456789",
            "idCard": "987654321"
        }`

    -   **Get all users**:

        `GET /api/users`

    -   **Get user by ID**:

        `GET /api/users/{id}`

    -   **Update user**:

        `PUT /api/users/{id}`

        **Request Body**:

        `{
            "firstName": "Jane",
            "lastName": "Doe",
            "phone": "987654321"
        }`

Transaction Logging
-------------------

All transactions (deposits, withdrawals, and transfers) are logged to a file. This includes details such as the account number, transaction type, and the amount involved.

Concurrency and Thread Safety
-----------------------------

The banking system uses `ReentrantLock` to ensure thread-safe modifications of account balances. Transactions are processed asynchronously using `ExecutorService`, allowing for concurrent execution while maintaining data integrity. The operations are designed to be non-blocking, ensuring that multiple users can perform transactions simultaneously without conflicts.

License
-------

This project is licensed under the MIT License. See the LICENSE file for more information.
