package bankmanagementsystem;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Account account; // Declare account variable

    public static void main(String[] args) {
        // Set default values for the account
        String defaultCustomerName = "darshit";
        String defaultAccountId = "1002";

        BankManagementSystem bankSystem = new BankManagementSystem();

        // Open a new account with default values
        openNewAccount(defaultAccountId, defaultCustomerName);
        // Run the bank management system
        bankSystem.run();
        scanner.close();
    }

    // Method to open a new account
    public static void openNewAccount(String accountId, String customerName) {
        Account newAccount = new Account(accountId, customerName);
        AccountManagement.accounts.add(newAccount);
        newAccount.setBalance(10000);

        // Add default transaction history
        String defaultTransaction1 = "Initial deposit of $10000";
        String defaultTransaction2 = "Account created";

        newAccount.addTransaction(defaultTransaction1);
        newAccount.addTransaction(defaultTransaction2);

        System.out.println("New account opened successfully.");
    }
}
