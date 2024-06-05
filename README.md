# Southeast Banking System

## Course Title
Advance Java

## Faculty Name
Mosarrof Hossain, Lecturer, Department of Computer Science and Engineering

## Project Name
Southeast University - Final Project

## Task
Create a simple banking system that supports concurrent transactions using Java threading and JDBC with SQL.

## Design and Implementation

### Design Overview
SEU Banking System is a Java console based application for managing banking systems. Including account creation, deposits, withdrawals, balance checks, and transfers, using a MySQL database.

### Key Components
- Main Class (SeuBankingProject):
  - Manages database connection and initializes the application.
  - Creates necessary database tables.
  - Handles user interactions via a control console.
- Database Tables:
  - (Accounts) Stores account details.
  - (Transactions) Logs transaction details.
  - (Users) Manages user login details.

### Functionality
- User Registration and Login: Users register and log in to perform operations.
- Account Operations: Create Account, Deposit, Withdraw, Check Balance, Transfer, List Accounts.

### Implementation Used
- Used a JDBC connector driver to connect to MySQL.
- Handles user input via console with Scanner.
- Manages concurrent deposits using threads.

## Instructions to Set Up and Run the Application

### Prerequisites
- JDK installed.
- XAMPP installed.
- MySQL server installed and running.
- MySQL JDBC Driver added.

### Database Setup
1. Create database (seu_bank_project) in MySQL PHPmyAdmin.
2. Create user (seu_bank_db) using password 12345678.

### Running the Application
1. Copy the source code.
2. Open the project in an IDE e.g NetBeans.
3. Create database table and columns using provided SQL Query.
4. Add MySQL JDBC Driver to the project.
5. Run the main method in SeuBankingProject.

## Report

### Testing Process and Results

#### Testing Strategy
- Run the program, the control console will open.
- Create some users for a bank account.
- Deposit balance on a particular bank account.
- Withdraw balance on a particular bank account.
- Transfer balance to another account.
- Check the all account holder lists.

#### Test Results
All tests passed.

### Challenges and Solutions

#### Database Connection Management
- **Issue**: Database connection: First of all I thought it was an URL issue, I’ve tried to change default IP instead of localhost, then tried with many types of port.
- **Solution**: Finally it was solved by a JDBC connector jar file, and I needed to add this to the project library.

#### Concurrency Handling
- **Issue**: I have used synchronized blocks for thread safety in deposits.
- **Solution**: Implemented synchronized blocks to handle concurrency.

#### Error Handling
- **Issue**: Faced many errors in the program.
- **Solution**: Added error messages for better error handling.
