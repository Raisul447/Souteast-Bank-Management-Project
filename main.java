package seubankingproject;
import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class SeuBankingProject {
    private static final String url = "jdbc:mysql://localhost:3306/seu_bank_project";
    private static final String username = "seu_bank_db";
    private static final String password = "12345678";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            createTables(connection);
            userControlConsole(connection);
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Accounts (account_id INT PRIMARY KEY, account_holder_name VARCHAR(255), balance DECIMAL(10, 2))");
            statement.execute("CREATE TABLE IF NOT EXISTS Transactions (transaction_id INT PRIMARY KEY, account_id INT, transaction_type VARCHAR(10), amount DECIMAL(10, 2), timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (account_id) REFERENCES Accounts(account_id))");
        }
    }
    
    private static void listAccounts(Connection connection) {
    try (PreparedStatement statement = connection.prepareStatement("SELECT account_id, account_holder_name, balance FROM Accounts");
         ResultSet resultSet = statement.executeQuery()) {
        System.out.println("Account ID\tAccount Holder Name\tBalance");
        System.out.println("===============================================");
        while (resultSet.next()) {
            int accountId = resultSet.getInt("account_id");
            String accountHolderName = resultSet.getString("account_holder_name");
            double balance = resultSet.getDouble("balance");
            System.out.printf("%11d\t%-20s\t%.2f\n", accountId, accountHolderName, balance);
            }
        } catch (SQLException e) {
        System.out.println("Error in SQL accounts: " + e.getMessage());
        }
    }

    private static void userControlConsole(Connection connection) {
    Scanner scanner = new Scanner(System.in);
    boolean running = true;

    while (running) {
        System.out.println("\nSEU Banking System Project:");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Check Balance");
        System.out.println("5. Transfer");
        System.out.println("6. List Accounts");
        System.out.println("7. Exit");

        System.out.print("To choice enter the given number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter account holder name: ");
                    String name = scanner.nextLine();
                    createAccount(connection, name, 0.0);
                    break;
                case 2:
                    System.out.print("Enter account ID number: ");
                    int depositAccountID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine();
                    deposit(connection, depositAccountID, depositAmount);
                    break;
                case 3:
                    System.out.print("Enter account ID number: ");
                    int withdrawAccountID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine();
                    withdraw(connection, withdrawAccountID, withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter account ID number: ");
                    int balanceAccountID = scanner.nextInt();
                    scanner.nextLine();
                    double balance = getBalance(connection, balanceAccountID);
                    System.out.println("Current balance are: " + balance);
                    break;
                case 5:
                    System.out.print("Enter source account ID number: ");
                    int fromAccountID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter destination account ID number: ");
                    int toAccountID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter the transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine();
                    transfer(connection, fromAccountID, toAccountID, transferAmount);
                    break;
                case 6:
                listAccounts(connection);
                break;
                case 7:
                running = false;
                break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void createAccount(Connection connection, String name, double initialBalance) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO Accounts (account_holder_name, balance) VALUES (?, ?)")) {
            statement.setString(1, name);
            statement.setDouble(2, initialBalance);
            statement.executeUpdate();
            System.out.println("Account created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    private static void deposit(Connection connection, int accountId, double depositAmount) {
    try (PreparedStatement statement = connection.prepareStatement("SELECT account_id, balance FROM Accounts WHERE account_id = ?")) {
        statement.setInt(1, accountId);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("account_id");
                double balance = resultSet.getDouble("balance");

                try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE Accounts SET balance = balance + ? WHERE account_id = ?")) {
                    updateStatement.setDouble(1, depositAmount);
                    updateStatement.setInt(2, id);
                    updateStatement.executeUpdate();
                    System.out.println("Deposit successful.");
                }
            } else {
                System.out.println("Account ID number not found.");
                }
            }
        } catch (SQLException e) {
        System.out.println("Error to deposit: " + e.getMessage());
        }
    }

    private static void withdraw(Connection connection, int accountID, double amount) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO Transactions (account_id, transaction_type, amount) VALUES (?, ?, ?)")) {
            statement.setInt(1, accountID);
            statement.setString(2, "WITHDRAWAL");
            statement.setDouble(3, amount);
            statement.executeUpdate();

            try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE Accounts SET balance = balance - ? WHERE account_id = ?")) {
                updateStatement.setDouble(1, amount);
                updateStatement.setInt(2, accountID);
                int rowsAffected = updateStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Withdrawal successful.");
                } else {
                    System.out.println("Insufficient balance.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error to withdrawal: " + e.getMessage());
        }
    }

    private static double getBalance(Connection connection, int accountID) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT balance FROM Accounts WHERE account_id = ?")) {
            statement.setInt(1, accountID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("balance");
                } else {
                    System.out.println("Account not found.");
                    return 0.0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error to get balance: " + e.getMessage());
            return 0.0;
        }
    }

    private static void transfer(Connection connection, int fromAccountID, int toAccountID, double amount) {
        try (PreparedStatement statement1 = connection.prepareStatement("INSERT INTO Transactions (account_id, transaction_type, amount) VALUES (?, ?, ?)");
             PreparedStatement statement2 = connection.prepareStatement("INSERT INTO Transactions (account_id, transaction_type, amount) VALUES (?, ?, ?)");
             PreparedStatement updateStatement1 = connection.prepareStatement("UPDATE Accounts SET balance = balance - ? WHERE account_id = ?");
             PreparedStatement updateStatement2 = connection.prepareStatement("UPDATE Accounts SET balance = balance + ? WHERE account_id = ?")) {

            connection.setAutoCommit(false);

            statement1.setInt(1, fromAccountID);
            statement1.setString(2, "TRANSFER");
            statement1.setDouble(3, amount);
            statement1.executeUpdate();

            statement2.setInt(1, toAccountID);
            statement2.setString(2, "TRANSFER");
            statement2.setDouble(3, amount);
            statement2.executeUpdate();

            updateStatement1.setDouble(1, amount);
            updateStatement1.setInt(2, fromAccountID);
            updateStatement1.executeUpdate();

            updateStatement2.setDouble(1, amount);
            updateStatement2.setInt(2, toAccountID);
            updateStatement2.executeUpdate();

            connection.commit();
            System.out.println("Transfer successful.");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error in transaction to transfer: " + ex.getMessage());
            }
            System.out.println("Error in transfer: " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error setting auto-commit: " + e.getMessage());
            }
        }
    }
}
