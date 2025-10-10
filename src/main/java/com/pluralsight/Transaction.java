package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
        // Fields
        private LocalDate date;
        private LocalTime time;
        private String description;
        private String vendor;
        private double amount;

        // Constructor
        public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
            this.date = date;
            this.time = time;
            this.description = description;
            this.vendor = vendor;
            this.amount = amount;
        }

        public LocalDate getDate() {
            return date;
        }

        public LocalTime getTime() {
            return time;
        }

        public String getDescription() {
            return description;
        }

        public String getVendor() {
            return vendor;
        }

        public double getAmount() {
            return amount;
        }

        // Check if it's a deposit
        public boolean isDeposit() {
            return amount > 0;
        }

        // Check if it's a payment
        public boolean isPayment() {
            return amount < 0;
        }
    @Override
    public String toString() {
        return String.format("#%-10d %-18s %10.2f", date, time, description, vendor, amount);

    }

    }

