package com.pluralsight;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class HomeScreen {
    public static ArrayList<Transaction> transactions = getTransactionsFromFile(); // this line create a list of transaction by reading them from the file
    public static void main(String[] args) {
        String mainMenu = """       
                What do you want to do?
                D) Add Deposit
                P) Make Payment (Debit)
                L) Ledger
                X) Exit
                """;
        while (true) {  // As long as its true keep showing the menu until the user exit out
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
                    displayLedger();
                    break;
                case "X":
                    System.out.println("Exiting application.");
                    return;
                default:
                    System.out.println("INVALID COMMAND! Please select a valid option.");
            }
        }
    }

    private static void addDeposit() {           // this method add a new deposit and save it to the transactions.
        System.out.println("Add Deposit");
        LocalDate date = ConsoleHelper.promptForDate("Enter date (YYYY-MM-DD)"); // this is command that ask the user for deposit details.
        LocalTime time = ConsoleHelper.promptForTime("Enter time (HH:MM:SS)");
        String description = ConsoleHelper.promptForString("Enter description");
        String vendor = ConsoleHelper.promptForString("Enter vendor");
        double amount = ConsoleHelper.promptForDouble("Enter deposit amount");
        amount = Math.abs(amount); // this is to make sure deposit is positive

        // this code create a new transaction and add it to the list
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);
        saveTransaction(newTransaction);
        System.out.println("Deposit added successfully!");
    }

    private static void makePayment() {      // this is a method to record payment
        System.out.println("Make Payment");
        LocalDate date = ConsoleHelper.promptForDate("Enter date (YYYY-MM-DD)");
        LocalTime time = ConsoleHelper.promptForTime("Enter time (HH:MM:SS)");
        String description = ConsoleHelper.promptForString("Enter description");
        String vendor = ConsoleHelper.promptForString("Enter vendor");
        double amount = ConsoleHelper.promptForDouble("Enter payment amount");

        amount = -Math.abs(amount); // make sure that the payment is recorded as a negative amount
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);
        saveTransaction(newTransaction);
        System.out.println("Payment recorded successfully!");
    }

    private static void displayLedger() {  // this method display the ledger menu for customer to pick from
        String ledgerMenu = """
                A) All Entries
                D) Deposits Only
                P) Payments Only
                R) Reports
                H) Home
                """;
        while (true) { // it said while true keep showing the ledger until the user return to the main menu
            System.out.println(ledgerMenu);
            String input = ConsoleHelper.promptForString("Enter your Input (A, D, P, R, H)").toUpperCase();

            switch (input) {
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
                    System.out.println("INVALID COMMAND! Please select a valid option.");
            }
        }
    }
    private static void displayAllEntries() { // This method display all transactions
        System.out.println("All Entries:");
        displayTransactions(transactions);
    }
    // This method display only deposit transactions
    private static void displayDepositsOnly() {
        System.out.println("Deposits:");
        ArrayList<Transaction> deposits = new ArrayList<>();
        for (Transaction t : transactions) {  // filter deposits (amount > 0)
            if (t.getAmount() > 0) {
                deposits.add(t);
            }
        }
        displayTransactions(deposits);
    }
    private static void displayPaymentsOnly() {
        System.out.println("Payments:");
        ArrayList<Transaction> payments = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                payments.add(t);
            }
        }
        displayTransactions(payments);
    }
    // This method shows future report
    private static void showReports() {
        String reportMenu =  """
            1) Month To Date
            2) Previous Month
            3) Year To Date
            4) Previous Year
            5) Search by Vendor
            0) Back
            """;
        while(true){ //  Loop continuously until the user enters 0 to exit
            System.out.println(reportMenu);
            String input= ConsoleHelper.promptForString("Enter your INPUT (1, 2, 3, 4, 5, 0)");
            switch (input) {
                case "1":
                    showMonthToDate();
                    break;
                case "2":
                    showPreviousMonth();
                    break;
                case "3":
                    showYearToDate();
                    break;
                case "4":
                    showPreviousYear();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    return; //Go back to Ledger menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    public static void showMonthToDate(){ //   this report show from start of current month to today

    }
    public static void showPreviousMonth(){  // this report is for the previous calendar month

    }
    public static void showYearToDate(){   // this Show report from January 1st to today

    }
    public static void showPreviousYear(){  // this report shows the entire previous year

    }
    public static void searchByVendor(){  // this method search vendor by their given name

    }
    public static ArrayList<Transaction> getTransactionsFromFile() {  // This method reads transactions from the CSV file
        ArrayList<Transaction> transactions = new ArrayList<>();

        try (FileReader fileReader = new FileReader("transactions.csv");
             BufferedReader br = new BufferedReader(fileReader)) {

            String lineFromFile;

            while ((lineFromFile = br.readLine()) != null) {   // Read each line in the files,
                if (lineFromFile.startsWith("date")) continue; // it helps skip heater row from the csv file
                String[] parts = lineFromFile.split("\\|"); //  Split the line into parts
                if (parts.length < 5) continue;

                LocalDate date = LocalDate.parse(parts[0]);    // this line  convert date text into localDate
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction t = new Transaction(date, time, description, vendor, amount);    // this line create a transaction object and add it to the list
                transactions.add(t);
            }
        } catch (Exception e) {   //  to catch an exception mean to show error if something goes wrong while reading the file
            System.out.println("There was an error reading the transactions file.");
        }

        return transactions;
    }

    private static void saveTransaction(Transaction t) {  // This line of code adds a new transaction to the end of the CSV file,
        try (FileWriter fw = new FileWriter("transactions.csv", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.printf("%s|%s|%s|%s|%.2f%n",
                    t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        } catch (Exception e) {
            System.out.println("Error saving transaction to file.");
        }
    }

    private static void displayTransactions(ArrayList<Transaction> list) {   // This method displays a list of transactions
        for (Transaction t : list) {
            System.out.println(t);
        }
    }
}