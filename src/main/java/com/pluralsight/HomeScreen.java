package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class HomeScreen {
    private static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        String mainMenu="""
                        What would you like to do today ?
                        D) Add Deposit
                        P) Make Payment (Debit)
                        L) Ledger
                        X) Exit
                    """;
        while(true){
            String command = ConsoleHelper.promptForString("Enter your command (D, P, L,X)").toUpperCase();
            switch (command) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    addPayment();
                    break;
                case "L":
                    showLedger();
                    break;
                case "X":
                    System.out.println("Goodbye!");
                    return; // Exit the application
                default:
                    System.out.println("INVALID CHOICE! please choose another option");
            }
        }
    }
    private static void addDeposit() {
        System.out.println("----Add Deposit----");
        LocalDate date = ConsoleHelper.promptForDate("Enter date (YYYY-MM-DD)");
        LocalTime time = ConsoleHelper.promptForTime("Enter time (HH:MM:SS)");
        String vendor = ConsoleHelper.promptForString("Enter vendor");
        String description = ConsoleHelper.promptForString("Enter description");
        double amount = ConsoleHelper.promptForDouble("Enter deposit amount");
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);
        System.out.println("Deposit added.");

    }
    private static void addPayment() {
        LocalDate date = ConsoleHelper.promptForDate("Enter date (YYYY-MM-DD)");
        LocalTime time = ConsoleHelper.promptForTime("Enter time (HH:MM:SS)");
        String vendor = ConsoleHelper.promptForString("Enter vendor");
        String description = ConsoleHelper.promptForString("Enter description");
        double amount = ConsoleHelper.promptForDouble("Enter payment amount");
        Transaction newTransaction = new Transaction(date, time, description, vendor, -Math.abs(amount));
        transactions.add(newTransaction);
        System.out.println("Payment added.");

    }
    private static void showLedger(){
        String showLedger= """
              A) All Entries
              D) Deposits
              P) Payments Only
              R) Reports
              H) Home
              """;
        while (true) {
            String command = ConsoleHelper.promptForString(("Choose an option").toUpperCase());
            switch (command){
                case "A":
                    displayAllEntries();
                    break;
                case "D":
                    displayDepositsOnly();
                    break;
                case "P":
                    displayPaymentsOnly();
                    break;
                case "R":
                    showReports();
                    break;
                case "H":
                    return;
                default:
                    System.out.println("Invalid command! Please select a valid option");

            }
        }
    }
private static void displayAllEntries() {
    System.out.println("---- All Entries ----");
    for (Transaction tx : transactions){
        System.out.println(tx);
    }
}
private static void displayDepositsOnly(){
    System.out.println("---- Deposits Only ----");
    for (Transaction tx : transactions){
        if (tx.isDeposit()) {
            System.out.println(tx);
        }
    }
}
private static void displayPaymentsOnly(){
    System.out.println("---- Payments Only ----");
    for (Transaction tx : transactions) {
        if (tx.isPayment()) {
            System.out.println(tx);
        }
    }
}
private static void showReports(){
    System.out.println("feature coming soon");

}
}