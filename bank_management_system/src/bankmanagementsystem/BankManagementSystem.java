package bankmanagementsystem;

import java.util.Scanner;
import java.util.InputMismatchException;

public class BankManagementSystem {
    public static Scanner scanner;
    private AccountManagement accountManagement;
    private ManageAccount manageAccount;
    private TransferFunds transferFunds;

    public BankManagementSystem() {
        scanner = new Scanner(System.in);
        accountManagement = new AccountManagement();
        manageAccount = new ManageAccount();
        transferFunds = new TransferFunds();
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("------------------------------------------------------------");
            System.out.println("\t\t\tBank Management System");
            System.out.println("------------------------------------------------------------");
            System.out.println("1. Account Management");
            System.out.println("2. Manage Account");
            System.out.println("3. Transfer Funds");
            System.out.println("9. Exit");

            int choice = getValidIntegerInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    accountManagementMenu();
                    break;
                case 2:
                    manageAccountMenu();
                    break;
                case 3:
                    transferFundsMenu();
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        scanner.close();
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

    private void accountManagementMenu() {
        accountManagement.run();
    }

    private void manageAccountMenu() {
        manageAccount.run();
    }

    private void transferFundsMenu() {
        transferFunds.run();
    }

    public static void main(String[] args) {
        BankManagementSystem system = new BankManagementSystem();
        system.run();
    }
}
