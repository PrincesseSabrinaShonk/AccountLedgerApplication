package com.pluralsight;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

public class HomeScreen {
    public static ArrayList<Transaction> transactions = getTransactionsFromFile(); // this line create a list of transaction by reading them from the file
    public static void main(String[] args) {
        //--- Main Menu Methods --
        String mainMenu = """       
                What do you want to do?
                D) Add Deposit
                P) Make Payment (Debit)
                L) Ledger
                X) Exit
                """;
        System.out.println("--------------------------------");
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

    /**
     * This method add a new deposit and save it to the transactions file.
     */
    private static void addDeposit() {
        System.out.println("Add Deposit");
        LocalDate date = ConsoleHelper.promptForDate("Enter date (YYYY-MM-DD)");
        LocalTime time = ConsoleHelper.promptForTime("Enter time (HH:MM:SS)");
        String description = ConsoleHelper.promptForString("Enter description");
        String vendor = ConsoleHelper.promptForString("Enter vendor");
        double amount = ConsoleHelper.promptForDouble("Enter deposit amount");
        amount = Math.abs(amount);  // Ensure the deposit amount is positive in case the user accidentally enters a negative number

        // this code create a new transaction and add it to the list
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);
        saveTransaction(newTransaction);
        System.out.println("Deposit added successfully!");
    }

    /**
     * // This method allows the user to record a new payment transaction
     */
    private static void makePayment() {
        System.out.println("Make Payment");
        LocalDate date = ConsoleHelper.promptForDate("Enter date (YYYY-MM-DD)");
        LocalTime time = ConsoleHelper.promptForTime("Enter time (HH:MM:SS)");
        String description = ConsoleHelper.promptForString("Enter description");
        String vendor = ConsoleHelper.promptForString("Enter vendor");  // Ask the user to enter the vendor (who the payment is made to)
        double amount = ConsoleHelper.promptForDouble("Enter payment amount");

        amount = -Math.abs(amount); // make sure that the payment is recorded as a negative amount
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);
        saveTransaction(newTransaction);
        System.out.println("Payment recorded successfully!");
    }


    //--- Ledger Menu Methods --
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
                case "A":  // Display all transactions (both deposits and payments)
                    displayAllEntries();
                    break;
                case "D":  // Display only deposit transactions (money coming in)
                    displayDepositsOnly();
                    break;
                case "P": // Display only payment transactions (money going out)
                    displayPaymentsOnly();
                    break;
                case "R": // Open the reports menu for more detailed transaction reports
                    showReports();
                    break;
                case "H":
                    return;
                default:
                    System.out.println("INVALID COMMAND! Please select a valid option.");
            }
        }
    }
    /**
    *  This method display all transactions
     */
    private static void displayAllEntries() {
        System.out.println("---------------All Entries:-------------");
        sortTransactionsByNewest(transactions);  // sort by newest first
        displayTransactions(transactions);
    }

    /**
     * filters transactions where amount is > 0
     */
    private static void displayDepositsOnly() {
        System.out.println("Deposits:");
        ArrayList<Transaction> deposits = new ArrayList<>();
        for (Transaction t : transactions) {  // filter deposits (amount > 0)
            if (t.getAmount() > 0) {
                deposits.add(t);
            }
        }
        sortTransactionsByNewest(deposits);
        displayTransactions(deposits);

    }

    /**
     * //This method display only transactions that are payment, like thing that you buy
     */
    private static void displayPaymentsOnly() {
        System.out.println("Payments:");
        ArrayList<Transaction> payments = new ArrayList<>();   // Create a new list to store only payment transactions
        for (Transaction t : transactions) {  // Loop through all transactions
            if (t.getAmount() < 0) {   // Check if the transaction amount is negative (indicating a payment or expense)
                payments.add(t); // Add this transaction to the payments list
            }
        }
        sortTransactionsByNewest(payments);
        displayTransactions(payments);
    }


    //--- Reports Menu Methods --
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
            switch (input) {    // Use a switch statement to determine what to do based on the user's input
                case "1":   // Show transactions from the current mont
                    showMonthToDate();
                    break;
                case "2":  // Show transactions from the previous month
                    showPreviousMonth();
                    break;
                case "3": // Show transactions from the current year
                    showYearToDate();
                    break;
                case "4":  // Show transactions from the previous year
                    showPreviousYear();
                    break;
                case "5": // Allow the user to search for transactions by vendor name
                    searchByVendor();
                    break;
                case "0": // Exit the loop and return to the main ledger menu
                    return; //Go back to Ledger menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void showMonthToDate(){ //   this report show from start of current month to today
        System.out.println("Month-To-Date Transactions:");
        LocalDate today = LocalDate.now();
        // Loop through each transaction in the list
        for (Transaction t : transactions) {
            if (t.getDate().getYear() == today.getYear() &&  // Check if the transaction happened in the current year
                    t.getDate().getMonth() == today.getMonth()) {
                System.out.println(t);    // Print the transaction details if it matches the current year
            }
        }
    }

    /**
     * // this report is for the previous calendar month
     */
    private static void showPreviousMonth(){
        System.out.println("Previous Month Transactions:");
        LocalDate today = LocalDate.now();     // Get today's date
        LocalDate lastMonth = today.minusMonths(1);  // Calculate the date representing the same day in the previous month

        for (Transaction t : transactions) {   // Loop through every transaction in the list
            if (t.getDate().getYear() == lastMonth.getYear() &&     // Check if the transaction happened in the same year and month as lastMonth
                    t.getDate().getMonth() == lastMonth.getMonth()) {
                System.out.println(t);   // Print the transaction details if it matches the previous month
            }
        }
    }

    private static void showYearToDate(){  //this Show report from January 1st to today
        System.out.println("Year-To-Date Transactions:");
        int currentYear = LocalDate.now().getYear();

        for (Transaction t : transactions) {
            if (t.getDate().getYear() == currentYear) {
                System.out.println(t);
            }
        }
    }
    private static void showPreviousYear(){  // this report shows the entire previous year
        System.out.println("Previous Year Transactions:");
        int lastYear = LocalDate.now().getYear() - 1;

        for (Transaction t : transactions) {
            if (t.getDate().getYear() == lastYear) {
                System.out.println(t);
            }
        }
    }

    /**
     *  // this method search vendor by their given name
     */
    private static void searchByVendor(){
       String vendor = ConsoleHelper.promptForString("Enter vendor name to search"); // this line of code print the header showing the vendor name for which transactions will be displayed
        System.out.println("Transactions for vendor: + vendor");

        boolean isfind = false;
        for(Transaction t: transactions){
            if (t.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(t);
                isfind = true;
                }
            }
            if(!isfind){ // not find for the vendor
                System.out.println("Can't find Vendor");
            }
        }

    /**
     * // This method reads transactions from the CSV file and return them to the list
     */
    public static ArrayList<Transaction> getTransactionsFromFile() {
        ArrayList<Transaction> transactions = new ArrayList<>();  // Create a list to store all the transactions read from the file

        try (FileReader fileReader = new FileReader("transactions.csv");   //Use try-with-resources to automatically close the file after reading
             BufferedReader br = new BufferedReader(fileReader)) {

            String lineFromFile;

            while ((lineFromFile = br.readLine()) != null) {    // Read the file line by line
                if (lineFromFile.startsWith("date")) continue;   // Skip the header row (the first line that usually contains column names)
                String[] parts = lineFromFile.split("\\|"); //  Split each line into parts using a separator
                if (parts.length < 5) continue;            // If the line doesn’t have all required parts, skip it

                LocalDate date = LocalDate.parse(parts[0]);    // this line  convert text into localDate
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction t = new Transaction(date, time, description, vendor, amount);    // this line create a transaction object and add it to the list
                transactions.add(t);
            }
        } catch (Exception e) {   //  to catch an exception mean to show error if something goes wrong while reading the file
            System.out.println("There was an error reading the transactions file.");
        }      // Display an error message if something goes wrong

        return transactions;   // Return the list of transactions
    }

    /**
     *  This line of code adds a new transaction to the end of the CSV file,
     */
    private static void saveTransaction(Transaction t) {
        try (FileWriter fileWriter = new FileWriter("transactions.csv", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.printf("%s|%s|%s|%s|%.2f%n",
                    t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        } catch (Exception e) {
            System.out.println("Error saving transaction to file.");
        }
    }

    private static void sortTransactionsByNewest(ArrayList<Transaction> list) {
        list.sort(Comparator
                .comparing(Transaction::getDate)
                .thenComparing(Transaction::getTime)
                .reversed()   // Reverse to have newest first
        );
    }

    /**
     *  // This method displays a list of transactions
     */
    private static void displayTransactions(ArrayList<Transaction> list) {
        for (Transaction t : list) {
            System.out.println(t);
        }
    }
}