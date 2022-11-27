package menu;

import api.AdminResource;
import model.*;
import java.util.*;

public class AdminMenu {

    public static Scanner scanner = new Scanner(System.in);
    public static AdminResource adminAPI = new AdminResource();

    /**
     * Display Admin Menu
     */
    private static void printMenuOptions() {
        System.out.println("\u001B[33m\u001B[1m" + "***** Admin Menu *****" + "\u001B[0m");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("\u001B[33m\u001B[1m" + "**********************" + "\u001B[0m");
    }

    /**
     * Fetch and display registered customers with their email ids
     */
    public static void fetchCustomers() {
        Collection<Customer> customers = adminAPI.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    /**
     * Fetch and display all existing rooms
     */
    public static void fetchRooms() {
        Collection<IRoom> rooms = adminAPI.getAllRooms();
        for (IRoom room : rooms) {
            System.out.println(room);
        }
    }

    /**
     * Helper function to fetch price of the new room being created
     * @return Price of the new room
     */
    static Double fetchRoomPrice() {
        Double roomPrice;
        while (true) {
            System.out.println("Please enter room price in $:");
            try {
                roomPrice = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m" + "Invalid price!" + "\u001B[0m");
            }
        }
        return roomPrice;
    }

    /**
     * Helper function to fetch type of room for the new room being created
     * @return Type of Room - single/double
     */
    static RoomType fetchRoomType() {
        String roomTypeResponse;
        RoomType roomType;
        while (true) {
            System.out.println("Please enter room type - Single/Double:");
            roomTypeResponse = scanner.nextLine().trim().toLowerCase();
            if (Arrays.asList("single", "double").contains(roomTypeResponse)) {
                roomType = roomTypeResponse.equals("single") ? RoomType.SINGLE : RoomType.DOUBLE;
                break;
            } else {
                System.out.println("\u001B[31m" + "Invalid room type." + "\u001B[0m");
            }
        }
        return roomType;
    }

    /**
     * Helper function to determine is more rooms are to be created
     * @return True is more rooms to be created, False if not
     */
    static Boolean createMoreRooms() {
        boolean addMoreRooms = true;
        while (true) {
            System.out.println("Would you like to add more rooms? Please enter y/n");
            String moreRoom = scanner.nextLine().trim().toLowerCase();
            if (moreRoom.equals("n")) {
                addMoreRooms = false;
                break;
            } else if (moreRoom.equals("y")) {
                break;
            } else {
                System.out.println("\u001B[31m" + "Invalid input!" + "\u001B[0m");
            }
        }
        return addMoreRooms;
    }

    /**
     * Create one or more rooms
     */
    public static void createRooms() {
        boolean addMoreRooms = true;
        String roomNum;
        Double roomPrice;
        RoomType roomType;
        List<IRoom> newRooms = new ArrayList<IRoom>();
        while (addMoreRooms) {
            // Fetch Room Number
            System.out.println("Please enter room number:");
            roomNum = scanner.nextLine().trim();
            // Fetch Room Price
            roomPrice = fetchRoomPrice();
            // Fetch Room Type
            roomType = fetchRoomType();
            // Create a Room/Free Room
            newRooms.add(roomPrice == 0 ? new FreeRoom(roomNum, roomType) : new Room(roomNum, roomPrice, roomType));
            // Create more rooms
            addMoreRooms = createMoreRooms();
        }
        adminAPI.addRoom(newRooms);
    }

    /**
     * Display admin menu as well as handle the user input
     */
    public static void displayAdminMenu() {
        boolean displayMenu = true;
        while (displayMenu) {
            printMenuOptions();
            switch (scanner.nextLine().trim()) {
                case "1":
                    fetchCustomers();
                    break;
                case "2":
                    fetchRooms();
                    break;
                case "3":
                    adminAPI.displayAllReservations();
                    break;
                case "4":
                    createRooms();
                    break;
                case "5":
                    displayMenu = false;
                    break;
                default:
                    System.out.println("\u001B[31m" + "Invalid input! Please enter 1, 2, 3, 4 or 5." + "\u001B[0m");
            }
        }
    }
}
