package bankmanagementsystem;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountId;
    private String customerName;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountId, String customerName) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    // Getters and setters
    public String getAccountId() {
        return accountId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Method to add transaction
    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
        saveAccountDetails();
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    // Method to print transaction history
    public void printTransactionHistory() {
        System.out.println("Transaction History for Account ID: " + accountId);
        for (String transaction : this.transactionHistory) {
            System.out.println(transaction);
        }
    }

    // Save account details to file
    private void saveAccountDetails() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("accounts.dat"))) {
            oos.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Retrieve account details from file
    public static Account retrieveAccountDetails(String accountId) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("accounts.dat"))) {
            Object obj;
            while ((obj = ois.readObject()) != null) {
                if (obj instanceof Account) {
                    Account account = (Account) obj;
                    if (account.getAccountId().equals(accountId)) {
                        return account;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
