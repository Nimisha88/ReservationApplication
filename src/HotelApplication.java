import api.AdminResource;
import api.HotelResource;
import menu.AdminMenu;
import menu.MainMenu;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class HotelApplication {

    private static AdminResource adminAPI = new AdminResource();
    private static HotelResource hotelAPI = new HotelResource();
    public static void main(String[] args) {

        List<IRoom> rooms = new ArrayList<IRoom>();
        rooms.add(new Room("101", 20.00, RoomType.SINGLE));
        rooms.add(new Room("102", 60.00, RoomType.DOUBLE));
        rooms.add(new Room("103", 50.00, RoomType.SINGLE));
        rooms.add(new Room("104", 70.00, RoomType.DOUBLE));
        adminAPI.addRoom(rooms);

        hotelAPI.createACustomer("nv@gmail.com", "N", "V");
        hotelAPI.createACustomer("vg@gmail.com", "V", "G");
        hotelAPI.createACustomer("vrg@gmail.com", "VR", "G");

        hotelAPI.bookARoom("nv@gmail.com",hotelAPI.getRoom("104"), new Date("12/10/2022"), new Date("12/12/2022"));

        MainMenu.displayMainMenu();
    }
}
