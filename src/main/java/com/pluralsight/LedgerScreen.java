package com.pluralsight;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class LedgerScreen {
    private ArrayList<Transaction> transactions;

    // Constructor to receive the transactions list from HomeScreen
    public LedgerScreen(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    // --- Ledger Menu Methods ---
    public void displayLedger() {
        String ledgerMenu = """
                A) All Entries
                D) Deposits Only
                P) Payments Only
                R) Reports
                H) Home
                """;

        while (true) {
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

    // --- Display Methods ---

    /**
     *  This method display all transactions
     */
    private void displayAllEntries() {
        System.out.println("---------------All Entries:-------------");
        System.out.println(" Current Balance:" + calculateBalance());
        sortTransactionsByNewest(transactions);
        displayTransactions(transactions);
    }

    /**
     * This method calculates and returns the current balance based on all transactions
     */
    private double calculateBalance() {
        double balance = 0;  // Start with a balance of 0
        for (Transaction t : transactions) {  //Loop through each transaction in the list
            balance += t.getAmount();
        }
        return balance; //Return the final calculated balance
    }

    /**
     * filters transactions where amount is < 0
     */
    private void displayDepositsOnly() {
        System.out.println("Deposits:");
        ArrayList<Transaction> deposits = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) deposits.add(t);
        }
        sortTransactionsByNewest(deposits);
        displayTransactions(deposits);
    }

    /**
     * filters transactions where amount is > 0
     */
    private void displayPaymentsOnly() {
        System.out.println("Payments:");

        ArrayList<Transaction> payments = new ArrayList<>();  // Create a new list to store only payment transactions
        for (Transaction t : transactions) {   // Loop through all transactions
            if (t.getAmount() < 0) payments.add(t);  // If the transaction amount is negative
        }
        sortTransactionsByNewest(payments);
        displayTransactions(payments);
    }

    /**
     * --- Reports Menu ---
     */

    private void showReports() {
        String reportMenu = """
            1) Month To Date
            2) Previous Month
            3) Year To Date
            4) Previous Year
            5) Search by Vendor
            0) Back
            """;

        while (true) {   //  Loop continuously until the user enters 0 to exit
            System.out.println(reportMenu);
            String input = ConsoleHelper.promptForString("Enter your INPUT (1, 2, 3, 4, 5, 0)");
            switch (input) {
                case "1":   // Show transactions from the current month
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
    // --- Report Methods ---

    /**
     * //   this report show from start of current month to today
     */
    private void showMonthToDate() {
        System.out.println("Month-To-Date Transactions:");
        LocalDate today = LocalDate.now();
        for (Transaction t : transactions) {   // Loop through each transaction in the list
            if (t.getDate().getYear() == today.getYear() &&    // Check if the transaction happened in the current year
                    t.getDate().getMonth() == today.getMonth()) {
                System.out.println(t);
            }      // Print the transaction details if it matches the current year
        }
    }

    /**
     * // this report is for the previous calendar month
     */
    private void showPreviousMonth() {
        System.out.println("Previous Month Transactions:");
        LocalDate today = LocalDate.now();   // Get today's date
        LocalDate lastMonth = today.minusMonths(1);   // Calculate the date representing the same day in the previous month
        for (Transaction t : transactions) {                           // Loop through every transaction in the list
            if (t.getDate().getYear() == lastMonth.getYear() &&
                    t.getDate().getMonth() == lastMonth.getMonth()) {
                System.out.println(t);                                        // Print the transaction details if it matches the previous month
            }
        }
    }

    /**
     * //this Show report from January 1st to today
     */
    private void showYearToDate() {
        System.out.println("Year-To-Date Transactions:");
        int currentYear = LocalDate.now().getYear();
        for (Transaction t : transactions) {
            if (t.getDate().getYear() == currentYear) {
                System.out.println(t);
            }
        }
    }

    /**
     *  this report shows the entire previous year
     */
    private void showPreviousYear() {
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
    private void searchByVendor() {
        String vendor = ConsoleHelper.promptForString("Enter vendor name to search");
        System.out.println("Transactions for vendor: " + vendor);

        boolean found = false;      //track if any matching transactions are found
        for (Transaction t : transactions) {  // Check if the transaction's vendor matches the search term
            if (t.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Can't find Vendor");
        }
    }

    /**
     * This method sorts a list of transactions so that the most recent ones appear first, based on date and time
     */
    private void sortTransactionsByNewest(ArrayList<Transaction> list) {
        list.sort(Comparator                  // First, compare by date
                .comparing(Transaction::getDate)   // If dates are the same, compare by time
                .thenComparing(Transaction::getTime)   // Reverse the order so newest comes first
                .reversed());
    }
    /**
     *  // This method displays a list of transactions and print each one of them to the console
     */
    private void displayTransactions(ArrayList<Transaction> list) {
        for (Transaction t : list) {
            System.out.println(t);
        }
    }
}
