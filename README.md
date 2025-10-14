# Financial Ledger Application

The goal of this application is to enables users to record deposits and payments, view their current balance, and organize their transaction history efficiently.
Users can choose to display all transactions or filter them by type (deposits or payments) for easier review.
All data is securely stored in a CSV file, ensuring long-term record keeping and data persistence between sessions.

---

## Home Screen

When the program starts, users see a main menu with these options:

- **D) Add Deposit** – Add a deposit and save it to the CSV file  
- **P) Make Payment (Debit)** – Record a payment and save it to the CSV file  
- **L) Ledger** – Open the ledger to view transactions and reports  
- **X) Exit** – Exit the program  

The program continues to run until the user chooses to exit.

---

##  Ledger Menu

The Ledger screen allows users to view and filter transactions.  
It includes the following options:

- **A) All Entries** – Display all transactions  
- **D) Deposits Only** – Show only positive transactions  
- **P) Payments Only** – Show only negative transactions  
- **R) Reports** – Run pre-defined reports or custom searches  
- **H) Home** – Return to the main menu  

---

## Reports Menu

The Reports section gives users detailed insights into their financial data:

1. **Month To Date** – Show all transactions for the current month  
2. **Previous Month** – Display transactions from the last calendar month  
3. **Year To Date** – Show transactions from the start of the current year  
4. **Previous Year** – Show transactions from the previous year  
5. **Search by Vendor** – Find transactions by vendor name  
0. **Back** – Return to the Ledger menu  

---

##  How It Works

### Transaction Storage


Each new transaction (deposit or payment) is appended to the file automatically.

### HomeScreen
- Controls the flow of the entire application  
- Handles the main menu, deposits, payments, ledger, and report options  
- Reads and writes data to `transactions.csv`

- <img width="623" height="275" alt="image" src="https://github.com/user-attachments/assets/c7bf13d9-a753-4d1a-b38e-191174116d57" />
<img width="1798" height="754" alt="image" src="https://github.com/user-attachments/assets/2ca09d3e-1f16-41ea-8c79-61830fee3fd9" />
<img width="1018" height="890" alt="image" src="https://github.com/user-attachments/assets/8a5fac38-1808-45e1-aef1-c039b10c1134" />
<img width="887" height="808" alt="image" src="https://github.com/user-attachments/assets/34931389-6e33-42ea-9276-2e418f969b65" />









### Transaction
- Represents each transaction with:
  - Date
  - Time
  - Description
  - Vendor
  - Amount  
- Provides a formatted output and helper methods for deposits/payments  

### ConsoleHelper
- Handles all user input and ensures validation for:
  - Dates (`YYYY-MM-DD`)
  - Times (`HH:MM:SS`)
  - Numbers (for deposit or payment amounts)  

---

