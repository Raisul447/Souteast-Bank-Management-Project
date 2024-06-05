CREATE DATABASE seu_bank_project;

CREATE TABLE Accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT PRIMARY,
    account_holder_name VARCHAR(255),
    balance DECIMAL(10, 2)
);

CREATE TABLE Transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT,
    transaction_type VARCHAR(10),
    amount DECIMAL(10, 2),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES Accounts(account_id)
);
