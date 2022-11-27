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
    public static Map<String , ArrayList<Date[]>> roomDatesMap = new HashMap<String , ArrayList<Date[]>>();

    public static void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public static IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public static Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    static void mapCustomerReservation(Customer customer, int reservationIndex) {
        ArrayList<Integer> reservationIndices = new ArrayList<Integer>();
        if (customerReservationMap.get(customer) != null) {
            reservationIndices = customerReservationMap.get(customer);
        }
        reservationIndices.add(reservationIndex);
        customerReservationMap.put(customer, reservationIndices);
    }

    static void mapRoomReservationDates(String roomNumber, Date checkIn, Date checkOut) {
        ArrayList<Date[]> reservationDates = new ArrayList<Date[]>();
        if (roomDatesMap.get(roomNumber) != null) {
            reservationDates = roomDatesMap.get(roomNumber);
        }
        reservationDates.add(new Date[] {checkIn, checkOut});
        roomDatesMap.put(roomNumber, reservationDates);
    }

    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        Reservation newBooking = new Reservation(customer, room, checkIn, checkOut);
        // Add new reservation in reservations list
        reservations.add(newBooking);
        // Fetch the index of new reservation
        int newReservationIndex = reservations.indexOf(newBooking);
        // Add new reservation index in customerReservationMap
        mapCustomerReservation(customer, newReservationIndex);
        // Add new reservation index in roomReservationMap
        mapRoomReservationDates(room.getRoomNumber(), checkIn, checkOut);
        return newBooking;
    }

    public static Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
        // TODO: Deduce the logic to derive unbooked rooms for the tenure
        Collection<IRoom> allRooms = getAllRooms();
        Collection<IRoom> availableRooms = new ArrayList<>(getAllRooms());
        for (IRoom room: allRooms) {
            ArrayList<Date[]> reservationDates = roomDatesMap.get(room.getRoomNumber());
            if (reservationDates != null) {
                for (Date[] reservationDate: reservationDates) {
                    if (checkIn.equals(reservationDate[0])
                            || (checkIn.after(reservationDate[0]) && checkIn.before(reservationDate[1]))
                            || checkIn.equals(reservationDate[1])
                            || checkOut.equals(reservationDate[0])
                            || (checkOut.after(reservationDate[0]) && checkOut.before(reservationDate[1]))
                            || checkOut.equals(reservationDate[1])) {
                        availableRooms.remove(room);
                    }
                }
            }
        }
        return availableRooms;
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
