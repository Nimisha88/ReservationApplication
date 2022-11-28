import api.AdminResource;
import api.HotelResource;
import menu.AdminMenu;
import menu.MainMenu;
import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class HotelApplication {

    private static AdminResource adminAPI = new AdminResource();
    private static HotelResource hotelAPI = new HotelResource();
    public static void main(String[] args) {
        MainMenu.displayMainMenu();
    }
}
