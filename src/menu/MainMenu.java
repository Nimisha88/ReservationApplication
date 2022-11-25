package menu;

import java.util.Arrays;
import java.util.Scanner;

public class MainMenu {

    public static Scanner scanner = new Scanner(System.in);
    private static void printMenu() {
        System.out.println("\u001B[33m\u001B[1m" + "***** Main Menu *****" + "\u001B[0m");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("\u001B[33m\u001B[1m" + "*********************" + "\u001B[0m");
    }
    public static String getUserChoice() {
        String response = null;
        while (true) {
            printMenu();
            response = scanner.nextLine().trim();
            if (Arrays.asList("1", "2", "3", "4", "5").contains(response)) {
                break;
            } else {
                System.out.println("\u001B[31m" + "Invalid input! Please enter 1, 2, 3, 4 or 5." + "\u001B[0m");
            }
        }
        return response;
    }
}
