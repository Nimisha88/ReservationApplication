package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;

public class ReservationService {

    public static Map<String, IRoom> rooms = new HashMap<String, IRoom>();
    public static List<Reservation> reservations= new ArrayList<Reservation>();
    public static Map<Customer, ArrayList<Integer>> customerReservationMap = new HashMap<Customer, ArrayList<Integer>>();
    public static Map<IRoom , ArrayList<Integer>> roomReservationMap = new HashMap<IRoom , ArrayList<Integer>>();

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
        // Add new reservation in reservations list
        reservations.add(newBooking);
        int newReservationIndex = reservations.indexOf(newBooking);
        ArrayList<Integer> reservationIndices = new ArrayList<Integer>();
        // Add new reservation index in customerReservationMap
        if (customerReservationMap.get(customer) != null) {
            reservationIndices = customerReservationMap.get(customer);
        }
        reservationIndices.add(newReservationIndex);
        customerReservationMap.put(customer, reservationIndices);
        // Add new reservation index in roomReservationMap
        reservationIndices = new ArrayList<Integer>();
        if (roomReservationMap.get(room) != null) {
            reservationIndices = roomReservationMap.get(room);
        }
        reservationIndices.add(newReservationIndex);
        roomReservationMap.put(room, reservationIndices);
        return newBooking;
    }

    public static Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
        // TODO: Deduce the logic to derive unbooked rooms for the tenure
        return getAllRooms();
    }

    public static Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> customerReservations = new ArrayList<Reservation>();
        ArrayList<Integer> reservationIndices = customerReservationMap.get(customer);
        if (reservationIndices != null) {
            for (int reservationIndex : reservationIndices) {
                customerReservations.add(reservations.get(reservationIndex));
            }
        }
        return customerReservations;
    }

    public static void printAllReservation() {
        for(Reservation reservation: reservations) {
            System.out.println(reservation);
        }
    }
}
