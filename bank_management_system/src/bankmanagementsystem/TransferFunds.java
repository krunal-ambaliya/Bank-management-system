package bankmanagementsystem;

import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TransferFunds extends ManageAccount {
    private Scanner scanner;

    public TransferFunds() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        boolean exit = false;
        while (!exit) {

            System.out.println("\n=======================================================");
            System.out.println("\t\t\t\tTransfer Funds ");
            System.out.println("=======================================================");
            System.out.println("1. View Transaction History");
            System.out.println("2. To Another Account (External Transfer)");
            System.out.println("9. Back to Main Menu");

            int choice = getValidIntegerInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    printTransactionHistory();
                    break;
                case 2:
                    externalTransfer();
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private int getValidIntegerInput(String prompt) {
        int choice = -1;
        while (true) {
            try {
                System.out.print(prompt);
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return choice;
    }

    private double getValidDoubleInput(String prompt) {
        double value = -1;
        while (true) {
            try {
                System.out.print(prompt);
                value = scanner.nextDouble();
                scanner.nextLine(); // Consume newline left-over
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return value;
    }

    public void printTransactionHistory() {
        System.out.println("\n--------------- | Transaction History | ---------------");
        System.out.print("\n➥ Enter Account ID to view transaction history: ");
        String accountId = scanner.next();
        boolean found = false;
        List<Account> accounts = FileHandler.readFromFile();
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                System.out.println("Transaction History for Account ID: " + accountId);
                for (String transaction : account.getTransactionHistory()) {
                    if (!transaction.startsWith("Added")) { // Skip balance addition transactions
                        // Split the transaction string to get the details
                        String[] parts = transaction.split(",");
                        if (parts.length >= 3) { // Ensure there are at least 3 parts
                            String type = parts[0];
                            double amount = Double.parseDouble(parts[1]);
                            String fromTo = parts[2];
                            System.out.println("Type: " + type + ", Amount: " + amount + ", From/To: " + fromTo);
                        } else {
                            System.out.println(" " + transaction);
                        }
                    }
                }
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Account not found.");
        }
    }

    private void externalTransfer() {
        System.out.println("\n--------------- | Transfer funds (external) | ---------------");
         // Consume newline left-over
        System.out.print("\n➥ Enter Account ID to transfer from: ");
        String fromAccountId = scanner.nextLine();
        System.out.print("➥ Enter Account ID to transfer to: ");
        String toAccountId = scanner.nextLine();
        double amount = getValidDoubleInput("➥ Enter amount to transfer: ");
        boolean fromAccountFound = false;
        boolean toAccountFound = false;
        List<Account> accounts = FileHandler.readFromFile();
        for (Account account : accounts) {
            if (account.getAccountId().equals(fromAccountId)) {
                fromAccountFound = true;
                double fromBalance = account.getBalance();
                if (fromBalance >= amount) {
                    for (Account targetAccount : accounts) {
                        if (targetAccount.getAccountId().equals(toAccountId)) {
                            toAccountFound = true;
                            double toBalance = targetAccount.getBalance();
                            account.setBalance(fromBalance - amount);
                            targetAccount.setBalance(toBalance + amount);
                            System.out.println("Transfer successful.");

                            // Update transaction history for source account
                            String fromTransaction = "Transfer to " + "ID " + toAccountId + " " + "amount: " + amount;
                            account.addTransaction(fromTransaction);

                            // Update transaction history for target account
                            String toTransaction = "Received from " + "ID " + fromAccountId + " " + "amount: " + amount;
                            targetAccount.addTransaction(toTransaction);

                            // Write accounts to file after each transfer
                            FileHandler.writeToFile(accounts);

                            break;
                        }
                    }
                } else {
                    System.out.println("Insufficient balance in the source account.");
                }
                break;
            }
        }
        if (!fromAccountFound) {
            System.out.println("Source account not found.");
        }
        if (!toAccountFound) {
            System.out.println("Target account not found.");
        }
    }
}
