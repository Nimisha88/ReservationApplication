package menu;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainMenu {

    public static Scanner scanner = new Scanner(System.in);
    public static HotelResource hotelAPI = new HotelResource();
    private static void printMenu() {
        System.out.println("\u001B[33m\u001B[1m" + "***** Main Menu *****" + "\u001B[0m");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("\u001B[33m\u001B[1m" + "*********************" + "\u001B[0m");
    }

    private static void findAndReserve() {
        Date checkIn;
        Date checkOut;
        while(true) {
            try {
                System.out.println("Please enter Check In date (MM/DD/YYYY):");
                checkIn = new Date(scanner.nextLine().trim());
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("\u001B[31m" + "Please input a valid date" + "\u001B[0m");
            }
        }

        while(true) {
            try {
                System.out.println("Please enter Check Out date (MM/DD/YYYY):");
                checkOut = new Date(scanner.nextLine().trim());
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("\u001B[31m" + "Please input a valid date" + "\u001B[0m");
            }
        }

        try {
            Collection<IRoom> rooms = hotelAPI.findARoom(checkIn, checkOut);
            for(IRoom room : rooms) {
                System.out.println(room);
            }
            if(rooms.isEmpty()) {
                System.out.println("\u001B[31m" + "Sorry! No rooms are available for that period!" + "\u001B[0m");
                return;
            }
        }
        catch (Exception e) {
            System.out.println("\u001B[31m" + "Encountered error in finding available rooms. Please try again later!" + "\u001B[0m");
            return;
        }
        System.out.println("Enter Room Number of the room you would like to reserve:");
        String response = scanner.nextLine().trim();
        IRoom reserveRoom = hotelAPI.getRoom(response);
        if (reserveRoom != null) {
            System.out.println("Please provide email for reservation:");
            String email = scanner.nextLine().trim();
            Reservation newReservation = hotelAPI.bookARoom(email, reserveRoom, checkIn, checkOut);
            if (newReservation == null) {
                System.out.println("You are a new Customer, creating a new account.");
                if (!createAnAccount(email)) {
                    System.out.println("\u001B[31m" + "Reservation unsuccessful!" + "\u001B[0m");
                    return;
                }
                newReservation = hotelAPI.bookARoom(email, reserveRoom, checkIn, checkOut);
            }
            System.out.println("\u001B[32m" + "Reservation successful!" + "\u001B[0m");
            System.out.println(newReservation);
        } else {
            System.out.println("\u001B[31m" + "No such room exists or is available for reservation! Please try again." + "\u001B[0m");
        }
    }

    private static void fetchMyReservations() {
        System.out.println("Please enter your email:");
        String email = scanner.next().trim();
        Collection<Reservation> myReservations = hotelAPI.getCustomersReservation(email);
        if (myReservations == null) {
            System.out.println("\u001B[31m" + "No such Customer Account found with the given email!" + "\u001B[0m");
            return;
        } else if (myReservations.isEmpty()) {
            System.out.println("\u001B[32m" + hotelAPI.getCustomer(email).getFullName() + " (" + email + ") has no reservations" + "\u001B[0m");
        } else {
            for (Reservation reservation : myReservations) {
                System.out.println(reservation);
            }
        }
    }

    private static boolean createAnAccount(String email) {
        System.out.println("Please enter your first name:");
        String fname = scanner.nextLine().trim().toUpperCase();
        System.out.println("Please enter your last name:");
        String lname = scanner.nextLine().trim().toUpperCase();
        if (email == null) {
            System.out.println("Please enter your email:");
            email = scanner.nextLine().trim();
        }
        if (hotelAPI.getCustomer(email) == null) {
            try {
                hotelAPI.createACustomer(email, fname, lname);
                System.out.println("\u001B[32m" + "Account created successfully!" + "\u001B[0m");
                System.out.println(hotelAPI.getCustomer(email));
            } catch (IllegalArgumentException e) {
                System.out.println("\u001B[31m" + "Invalid email. Account not created. Please try again!" + "\u001B[0m");
                return false;
            }
        } else {
            System.out.println("\u001B[31m" + "Account with given email already exists. Account not created." + "\u001B[0m");
        }
        return true;
    }

    public static void displayMainMenu() {
        boolean displayMenu = true;
        boolean printMenuAvailable = false;
        String userOption;
        while (displayMenu) {
            if (!printMenuAvailable) {
                printMenu();
            } else {
                printMenuAvailable = false;
            }

            userOption = scanner.nextLine().trim();
            switch (userOption) {
                case "":
                    printMenuAvailable = true;
                    break;
                case "1":
                    findAndReserve();
                    break;
                case "2":
                    fetchMyReservations();
                    break;
                case "3":
                    createAnAccount(null);
                    break;
                case "4":
                    AdminMenu.displayAdminMenu();
                    break;
                case "5":
                    displayMenu = false;
                    System.out.println("Bye " + "\uD83D\uDC4B" + " See you next time!");
                    break;
                default:
                    System.out.println("\u001B[31m" + "Invalid input [" + userOption + "]! Please enter 1, 2, 3, 4 or 5." + "\u001B[0m");
            }
        }
    }
}
