package menu;

import api.AdminResource;
import model.*;

import java.sql.Array;
import java.util.*;

public class AdminMenu {

    public static Scanner scanner = new Scanner(System.in);
    public static AdminResource adminAPI = new AdminResource();

    private static void printMenuOptions() {
        System.out.println("\u001B[33m\u001B[1m" + "***** Admin Menu *****" + "\u001B[0m");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("\u001B[33m\u001B[1m" + "**********************" + "\u001B[0m");
    }

    public static void fetchCustomers() {
        Collection<Customer> customers = adminAPI.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public static void fetchRooms() {
        Collection<IRoom> rooms = adminAPI.getAllRooms();
        for (IRoom room : rooms) {
            System.out.println(room);
        }
    }

    public static void createRooms() {
        boolean addMoreRooms = true;
        String roomNum;
        Double roomPrice;
        String roomTypeResponse;
        RoomType roomType;
        List<IRoom> newRooms = new ArrayList<IRoom>();
        while (addMoreRooms) {
            System.out.println("Please enter room number:");
            roomNum = scanner.nextLine().trim();
            while (true) {
                System.out.println("Please enter room price in $:");
                try {
                    roomPrice = Double.parseDouble(scanner.nextLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("\u001B[31m" + "Invalid price!" + "\u001B[0m");
                }
            }
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
            if (roomPrice == 0) {
                newRooms.add(new FreeRoom(roomNum, roomType));
            }
            else {
                newRooms.add(new Room(roomNum, roomPrice, roomType));
            }
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
        }
        adminAPI.addRoom(newRooms);
    }

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
