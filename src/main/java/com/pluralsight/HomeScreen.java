package com.pluralsight;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class HomeScreen {
    public static ArrayList<Transaction> transactions = getTransactionsFromFile();
    // this line create a list of transaction by reading them from the file
    public static void main(String[] args) {

        System.out.println("------Welcome to the Accounting Ledger App-------");
        //--- Main Menu Methods --
        String mainMenu = """       
                What do you want to do Today?
                D) Add Deposit
                P) Make Payment (Debit)
                L) Ledger
                X) Exit
                """;

        while (true) {           // Loop until user chooses to exit
            System.out.println(mainMenu);
            String input = ConsoleHelper.promptForString("Enter your INPUT (D, P, L, X)").toUpperCase();

            switch (input) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    // New: Create a Ledger object and call its menu
                    Ledger ledger = new Ledger(transactions);
                    ledger.displayLedger();
                    break;
                case "X":
                    System.out.println("Exiting application.");
                    return;
                default:
                    System.out.println("INVALID COMMAND! Please select a valid option.");
            }
        }
    }
    /**
     * Prompts the user for deposit information and saves it.
     */
    private static void addDeposit() {
        System.out.println("Add Deposit");
        LocalDate date = ConsoleHelper.promptForDate("Enter date (YYYY-MM-DD)");
        LocalTime time = ConsoleHelper.promptForTime("Enter time (HH:MM:SS)");
        String description = ConsoleHelper.promptForString("Enter description");
        String vendor = ConsoleHelper.promptForString("Enter vendor");
        double amount = ConsoleHelper.promptForDouble("Enter deposit amount");

        amount = Math.abs(amount); // Ensure it’s positive

        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);
        saveTransaction(newTransaction);
        System.out.println("Deposit added successfully!");
    }
    // --- Make Payment ---
    /**
     * Prompts the user for payment information and saves it.
     */
    private static void makePayment() {
        System.out.println("Make Payment");
        LocalDate date = ConsoleHelper.promptForDate("Enter date (YYYY-MM-DD)");
        LocalTime time = ConsoleHelper.promptForTime("Enter time (HH:MM:SS)");
        String description = ConsoleHelper.promptForString("Enter description");
        String vendor = ConsoleHelper.promptForString("Enter vendor");
        double amount = ConsoleHelper.promptForDouble("Enter payment amount");

        amount = -Math.abs(amount); // Ensure it’s negative

        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);   // Add to list
        saveTransaction(newTransaction);      // Save to CSV
        System.out.println("Payment recorded successfully!");
    }
    // --- Read from CSV ---
    /**
     * // This method reads transactions from the CSV file and return them to the list
     */
    public static ArrayList<Transaction> getTransactionsFromFile() {
        ArrayList<Transaction> transactions = new ArrayList<>();  // Create a list to store all the transactions read from the file

        try (FileReader fileReader = new FileReader("transactions.csv");    //Use try-with-resources to automatically close the file after reading
             BufferedReader br = new BufferedReader(fileReader)) {
            String lineFromFile;

            while ((lineFromFile = br.readLine()) != null) {  // Read the file line by line
                if (lineFromFile.startsWith("date")) continue;   // Skip the header line that starts with "date"
                String[] parts = lineFromFile.split("\\|");
                if (parts.length < 5) continue;      // If a line doesn't have all required fields, skip it


                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction t = new Transaction(date, time, description, vendor, amount);    // Create a new Transaction object with the parsed data
                transactions.add(t);
            }
        } catch (Exception e) {
            System.out.println("There was an error reading the transactions file.");   //  to catch an exception mean to show error if something goes wrong while reading the file
        }
        return transactions;
    }

    // --- Save to CSV ---
    /**
     *  This line of code adds a new transaction to the end of the CSV file,
     */
    private static void saveTransaction(Transaction t) {
        try (FileWriter fileWriter = new FileWriter("transactions.csv", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.printf("%s|%s|%s|%s|%.2f%n",
                    t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        } catch (Exception e) {
            System.out.println("Error saving transaction to file.");   // If anything goes wrong while writing, display an error message
        }
        // Write the transaction data to the CSV file in a pipe-separated format
        // %.2f ensures the amount is written with two decimal places
        // %n adds a new line at the end of each transaction
    }
}



