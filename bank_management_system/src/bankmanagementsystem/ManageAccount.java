package bankmanagementsystem;

import java.util.List;
import java.io.*;
import java.util.Scanner;
class FileHandler {
    public static List<Account> readFromFile() {
        List<Account> accounts = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("accounts.txt"))) {
            accounts = (List<Account>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static void writeToFile(List<Account> accounts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("accounts.txt"))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
public class ManageAccount {
    public static String accountId;
    private Scanner scanner;

    public ManageAccount() {
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=======================================================");
            System.out.println("\t\t\t\tManage Account");
            System.out.println("=======================================================");
            System.out.println("1. Add Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. View Transaction History");
            System.out.println("9. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addBalance();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    viewTransactionHistory();
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice \n");
            }
        }
    }

    public void addBalance() {
        System.out.println("\n--------------- | Add Balance | ---------------");
        Scanner scanner = new Scanner(System.in);
        System.out.print(" \n➥ Enter Account ID: ");
        String accountId = scanner.nextLine();
        System.out.print(" ➥ Enter amount to add: ");
        double amount = scanner.nextDouble();
        boolean found = false;
        for (Account account : AccountManagement.accounts) {
            if (account.getAccountId().equals(accountId)) {
                double currentBalance = account.getBalance();
                account.setBalance(currentBalance + amount);
                System.out.println("  Balance added successfully. ☑ ");

                // Call addTransaction() on the current account object
                String transactionDetails = "Added " + amount + " to balance";
                account.addTransaction(transactionDetails);

                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println(" ⚠\uFE0FAccount not found. \n ");
        }

        // Write to file after updating account balance
        FileHandler.writeToFile(AccountManagement.accounts);
    }

    private void withdraw() {
        System.out.println("\n--------------- | Withdraw | ---------------");
        Scanner scanner = new Scanner(System.in);
        System.out.print(" \n➥ Enter Account ID: ");
        String accountId = scanner.nextLine();
        System.out.print(" ➥ Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        boolean found = false;
        for (Account account : AccountManagement.accounts) {
            if (account.getAccountId().equals(accountId)) {
                double currentBalance = account.getBalance();
                if (currentBalance >= amount) {
                    account.setBalance(currentBalance - amount);
                    System.out.println("Withdrawal successful. ☑ ");

                    // Record transaction history for withdrawal
                    String transactionDetails = "Withdrawn " + amount + " from balance";
                    account.addTransaction(transactionDetails);

                } else {
                    System.out.println("Insufficient balance. ☑ ");
                }
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Account not found. ⚠\uFE0F \n");
        }

        // Write to file after updating account balance and transaction history
        FileHandler.writeToFile(AccountManagement.accounts);
    }



    private void checkBalance() {
        System.out.println("\n--------------- | Check Balance | ---------------");
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n➥ Enter Account ID: ");
        String accountId = scanner.nextLine();
        boolean found = false;
        List<Account> accounts = FileHandler.readFromFile();
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                System.out.println("Current Balance: " + account.getBalance());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Account not found. ⚠\uFE0F \n");
        }
    }

    public void viewTransactionHistory() {
        System.out.println("\n--------------- | View Transaction History | ---------------");
        Scanner scanner = new Scanner(System.in);
        System.out.print(" \n➥ Enter Account ID to view transaction history: ");
        String accountId = scanner.nextLine();
        boolean found = false;
        List<Account> accounts = FileHandler.readFromFile();
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                account.printTransactionHistory(); // Call printTransactionHistory() on the account instance
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Account not found. ⚠\uFE0F \n");
        }
    }

}
