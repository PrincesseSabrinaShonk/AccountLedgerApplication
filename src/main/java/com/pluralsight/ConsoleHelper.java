package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class ConsoleHelper {
    private static Scanner scanner = new Scanner(System.in);
    public static int promptForInt(String prompt) {
        System.out.print(prompt + ": ");
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }
        public static double promptForDouble (String prompt){
            while (true) {
                try {
                    System.out.print(prompt + ": ");
                    String input = scanner.nextLine(); // Read input as a string
                    return Double.parseDouble(input); // Convert String to double
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number");
                }
            }
        }

public static String promptForString(String prompt) {
    System.out.print(prompt + ": ");
    return scanner.nextLine();
} //Prompts the user for a string input
public static LocalDate promptForDate(String prompt){
    while(true){
        try {
            System.out.println(prompt +  ": ");
            String dateAsString = scanner.nextLine();
            return LocalDate.parse(dateAsString);
        }
        catch (Exception ex){
            System.out.println("Invalid Entry, please enter a valid date (YYYY-MM-DD)");
        }
    }
}
public static LocalTime promptForTime(String prompt){
    while(true){
        try {
            System.out.println(prompt + ": ");
            String timeAsString = scanner.nextLine();
            return LocalTime.parse(timeAsString);
        } catch (Exception ex) {
            System.out.println( "Invalid Entry, please enter a valid time ( HH:MM:SS)");
            }
        }
    }
}