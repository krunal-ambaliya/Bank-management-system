package bankmanagementsystem;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountManagement {
    public static List<Account> accounts;
    private Scanner scanner;
    private File file;

    public AccountManagement() {
        accounts = new ArrayList<>();
        scanner = new Scanner(System.in);
        file = new File("accounts.txt"); // File to store account details
        readFromFile();
        if (accounts == null) {
            accounts = new ArrayList<>(); // If no accounts found, initialize a new list
        }
    }

    public void run() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=======================================================");
            System.out.println("\t\t\t\tAccount Management");
            System.out.println("=======================================================");
            System.out.println("1. Open New Account");
            System.out.println("2. Delete Account");
            System.out.println("3. Update Account Information");
            System.out.println("4. View Account Details");
            System.out.println("9. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getValidIntegerInput();

            switch (choice) {
                case 1:
                    openNewAccount();
                    break;
                case 2:
                    deleteAccount();
                    break;
                case 3:
                    updateAccountInformation();
                    break;
                case 4:
                    viewAccountDetails();
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private int getValidIntegerInput() {
        int choice = -1;
        while (true) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
                break;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return choice;
    }

    public void openNewAccount() {
        System.out.println("\n--------------- | Open New Account | ---------------");
        System.out.print("\n ➥Enter Account ID: ");
        String accountId = scanner.nextLine();
        System.out.print(" ➥Enter Customer Name: ");
        String customerName = scanner.nextLine();
        Account newAccount = new Account(accountId, customerName);
        accounts.add(newAccount);
        System.out.println("New account opened successfully.");

        // Write updated account list to file
        writeToFile();
    }

    private void deleteAccount() {
        System.out.println("\n--------------- | Delete Account | ---------------");
        System.out.print("\n➥Enter Account ID to delete: ");
        String accountId = scanner.nextLine();
        boolean found = false;
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                accounts.remove(account);
                System.out.println("Account deleted successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Account not found. ⚠\uFE0F");
        }

        // Write updated account list to file
        writeToFile();
    }

    private void updateAccountInformation() {
        System.out.println("\n--------------- | Update Account Information| ---------------");
        System.out.print("\n➥ Enter Account ID to update: ");
        String accountId = scanner.nextLine();
        boolean found = false;
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                System.out.print("➥ Enter new customer name: ");
                String newCustomerName = scanner.nextLine();
                account.setCustomerName(newCustomerName);
                System.out.println("Account information updated successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Account not found.");
        }

        // Write updated account list to file
        writeToFile();
    }

    private void viewAccountDetails() {
        System.out.println("\n--------------- | View Account Details| ---------------");
        System.out.print("\n➥ Enter Account ID to view details: ");
        String accountId = scanner.nextLine();
        boolean found = false;
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                System.out.println("Account ID: " + account.getAccountId());
                System.out.println("Customer Name: " + account.getCustomerName());
                System.out.println("Balance: " + account.getBalance());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Account not found.");
        }
    }

    // Method to write account list to file
    private void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read account list from file
    public void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            accounts = (List<Account>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
