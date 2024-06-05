# Southeast Banking System

## Course Title
Advance Java

## Faculty Name
Mosarrof Hossain, Lecturer, Department of Computer Science and Engineering

## Project Name
Southeast University - Final Project

## Task
Create a simple banking system that supports concurrent transactions using Java threading and JDBC with SQL.

## Part 1: Database Design

### Accounts Table:
- `account_id` (INT, Primary Key)
- `account_holder_name` (VARCHAR)
- `balance` (DECIMAL)

### Transactions Table:
- `transaction_id` (INT, Primary Key)
- `account_id` (INT, Foreign Key)
- `transaction_type` (VARCHAR) - ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')
- `amount` (DECIMAL)
- `timestamp` (TIMESTAMP)

## Part 2: Requirements

### Account Management:
- Implement functionality to create new accounts.
- Implement functionality to view account details, including current balance.

### Transactions:
- Implement functionality to perform deposits, withdrawals, and transfers between accounts.
- Ensure that transactions are thread-safe and that the balance remains consistent.

### Multi-threading:
- Use Java threads to handle multiple transactions concurrently.
- Implement synchronization to handle concurrent access to account balances.
- Use appropriate transaction management to ensure data integrity.

### Database Interaction:
- Use JDBC to connect to a relational database (e.g., MySQL).
- Implement CRUD operations for accounts and transactions using JDBC.
- Ensure proper resource management (e.g., closing connections, statements, and result sets).

## Part 3: Implementation Steps

### Setup the Database:
- Create the database and tables using SQL.
- Populate the tables with some initial data for testing.

### Java Application:
- Create a Java class to represent an Account and a Transaction.
- Implement JDBC utility classes for database operations.
- Implement a multi-threaded application to handle transactions.

### Synchronization and Transaction Management:
- Use synchronized blocks or other synchronization mechanisms to ensure thread safety.
- Implement proper transaction management to handle commits and rollbacks.
