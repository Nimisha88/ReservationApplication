package menu;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    private static Calendar cal = Calendar.getInstance();
    private static Scanner scanner = new Scanner(System.in);
    private static HotelResource hotelAPI = new HotelResource();
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * Display Main Menu
     */
    private static void printMenu() {
        System.out.println("\u001B[33m\u001B[1m" + "***** Main Menu *****" + "\u001B[0m");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("\u001B[33m\u001B[1m" + "*********************" + "\u001B[0m");
    }

    /**
     * Helper function to fetch Check In and Check Out dates
     * @param dateType Check In/Check Out
     * @return Check In/Check Out Date
     */
    private static Date fetchDateForReservation(String dateType) {
        Date date = null;
        Date today = new Date();
        boolean getDate = true;
        while(getDate) {
            try {
                System.out.println("Please enter " + dateType + " date (MM/DD/YYYY):");
                date = dateFormatter.parse(scanner.nextLine().trim());
                if (date.after(today)) {
                    getDate = false;
                } else {
                    System.out.println("\u001B[31m" + "Date is before today! Please provide a valid input." + "\u001B[0m");
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println("\u001B[31m" + "Please input a valid date" + "\u001B[0m");
            }
            catch (ParseException e) {
                System.out.println("\u001B[31m" + "Please input a valid date" + "\u001B[0m");
            }
        }
        return date;
    }

    /**
     * Helper function to fetch and display Available Rooms for the reservation period
     * @param checkIn Check In Date
     * @param checkOut Check Out Date
     * @return True if rooms are available, False if not
     */
    private static Collection<IRoom> displayAvailableRooms(Date checkIn, Date checkOut) {
        try {
            Collection<IRoom> rooms = hotelAPI.findARoom(checkIn, checkOut);
            if(rooms.isEmpty()) {
                System.out.println("\u001B[31m" + "Sorry! No rooms available for " + dateFormatter.format(checkIn) + " - " + dateFormatter.format(checkOut) + "\u001B[0m");
            } else {
                for(IRoom room : rooms) {
                    System.out.println(room);
                }
            }
            return rooms;
        }
        catch (Exception e) {
            System.out.println("\u001B[31m" + "Encountered error in finding available rooms. Please try again later!" + "\u001B[0m");
            return null;
        }
    }

    /**
     * Helper function to reserve a room
     * @param room Room to be reserved
     * @param checkIn Check In Date
     * @param checkOut Check Out Date
     */
    private static void reserveRoom(IRoom room, Date checkIn, Date checkOut) {
        System.out.println("Please provide email for reservation:");
        String email = scanner.nextLine().trim();
        Reservation newReservation = hotelAPI.bookARoom(email, room, checkIn, checkOut);
        if (newReservation == null) {
            System.out.println("You are a new Customer, creating a new account.");
            if (!createAnAccount(email)) {
                System.out.println("\u001B[31m" + "Reservation unsuccessful!" + "\u001B[0m");
                return;
            }
            newReservation = hotelAPI.bookARoom(email, room, checkIn, checkOut);
        }
        System.out.println("\u001B[32m" + "Reservation successful!" + "\u001B[0m");
        System.out.println(newReservation);
    }

    /**
     * Add a number of days to a given Date
     * @param date Date
     * @param numOfDays Number of days to be added
     * @return Date + Number of Days
     */
    private static Date addDaysToADate(Date date, int numOfDays) {
        cal.setTime(date);
        cal.add(Calendar.DATE, numOfDays);
        return cal.getTime();
    }

    /**
     * Find available rooms and reserve a room
     */
    private static void findAndReserve() {
        Date today = new Date();
        Date checkIn;
        Date checkOut;
        Collection<IRoom> availableRooms;

        // Fetch Reservation Dates
        while(true) {
            checkIn = fetchDateForReservation("Check In");
            checkOut = fetchDateForReservation("Check Out");
            if (checkIn.before(checkOut)) {
                break;
            } else {
                System.out.println("\u001B[31m" + "CheckOut is before CheckIn! Please provide a valid input." + "\u001B[0m");
            }
        }

        // Display available rooms
        availableRooms = displayAvailableRooms(checkIn, checkOut);

        if (availableRooms == null) {
            return;
        }

        if(availableRooms.isEmpty()) {
            checkIn = addDaysToADate(checkIn, 7);
            checkOut = addDaysToADate(checkOut, 7);
            System.out.println("\u001B[32m" + "Fetching rooms available after 7 days i.e. " + dateFormatter.format(checkIn) + " - " + dateFormatter.format(checkOut) + "\u001B[0m");
            availableRooms = displayAvailableRooms(checkIn, checkOut);
        }

        if(!availableRooms.isEmpty()) {
            System.out.println("Enter Room Number of the room you would like to reserve:");
            String response = scanner.nextLine().trim();
            IRoom reserveRoom = hotelAPI.getRoom(response);
            if (reserveRoom != null && availableRooms.contains(reserveRoom)) {
                reserveRoom(reserveRoom, checkIn, checkOut);
            } else {
                System.out.println("\u001B[31m" + "No such room exists or is available for reservation! Please try again." + "\u001B[0m");
            }
        }
    }

    /**
     * Fetch reservations associated to an account
     */
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

    /**
     * Create a Customer Account
     * @param email email of Customer
     * @return True if account was created successfully, False if not
     */
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

    /**
     * Display Main Menu and handle user input
     */
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
