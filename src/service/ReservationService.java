package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    public static Map<String, IRoom> rooms = new HashMap<String, IRoom>();
    public static Map<Customer, ArrayList<Reservation>> reservations = new HashMap<Customer, ArrayList<Reservation>>();

    public static void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public static IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public static Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        Reservation newBooking = new Reservation(customer, room, checkIn, checkOut);
        ArrayList<Reservation> customerReservations = reservations.get(customer);
        customerReservations.add(newBooking);
        reservations.put(customer, customerReservations);
        return newBooking;
    }

    public static Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
        // TODO: Deduce the logic to derive unbooked rooms for the tenure
        return null;
    }

    public static Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer);
    }

    public static void printAllReservation() {
        for(ArrayList<Reservation> listOfReservations: reservations.values()) {
            for(Reservation reservation: listOfReservations) {
                System.out.println(reservation);
            }
        }
    }
}
