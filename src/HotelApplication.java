import menu.AdminMenu;
import menu.MainMenu;

import java.util.Scanner;

public class HotelApplication {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        switch (MainMenu.getUserChoice()) {
            case "1":
            case "2":
            case "3":
            case "4":
                AdminMenu.getUserChoice();
                break;
            case "5":
                System.out.println("Bye " + "\uD83D\uDC4B" + " See you next time!");
                break;
            default:
                System.out.println("\u001B[31m" + "Something unexpected happened, exiting the application!" + "\u001B[0m");
        }
    }
}
