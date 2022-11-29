package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import java.util.*;

public class ReservationService {

    private static ReservationService INSTANCE;
    private Map<String, IRoom> rooms = new HashMap<String, IRoom>();
    private List<Reservation> reservations= new ArrayList<Reservation>();
    private Map<Customer, ArrayList<Integer>> customerReservationMap = new HashMap<Customer, ArrayList<Integer>>();
    private Map<String , ArrayList<Date[]>> roomDatesMap = new HashMap<String , ArrayList<Date[]>>();

    /**
     * ReservationService Constructor
     */
    private ReservationService() {
        // Do nothing!
    }

    /**
     * Getting instance of singleton object
     * @return ReservationService object
     */
    public static ReservationService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ReservationService();
        }
        return INSTANCE;
    }


    /**
     * Add a room
     * @param room Room with info about room number, price and room type
     */
    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    /**
     * Get a room by room number
     * @param roomId Room number
     * @return Room
     */
    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    /**
     * Get all rooms
     * @return Rooms
     */
    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    /**
     * Helper function to store a Customer and Reservation mapping for a new reservation
     * @param customer
     * @param reservationIndex
     */
    void mapCustomerReservation(Customer customer, int reservationIndex) {
        ArrayList<Integer> reservationIndices = new ArrayList<Integer>();
        if (customerReservationMap.get(customer) != null) {
            reservationIndices = customerReservationMap.get(customer);
        }
        reservationIndices.add(reservationIndex);
        customerReservationMap.put(customer, reservationIndices);
    }

    /**
     * Helper function to store a Room Number and CheckIn/CheckOut Dates mapping for a new reservation
     * @param roomNumber
     * @param checkIn
     * @param checkOut
     */
    void mapRoomReservationDates(String roomNumber, Date checkIn, Date checkOut) {
        ArrayList<Date[]> reservationDates = new ArrayList<Date[]>();
        if (roomDatesMap.get(roomNumber) != null) {
            reservationDates = roomDatesMap.get(roomNumber);
        }
        reservationDates.add(new Date[] {checkIn, checkOut});
        roomDatesMap.put(roomNumber, reservationDates);
    }

    /**
     * Create a new reservation
     * @param customer Customer
     * @param room Room
     * @param checkIn Check In Date
     * @param checkOut Check Out
     * @return New Reservation
     */
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
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

    /**
     * Find available rooms for a given check in and check out time
     * @param checkIn
     * @param checkOut
     * @return
     */
    public Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
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

    /**
     * Fetch all reservations of a customer
     * @param customer Customer
     * @return Reservations
     */
    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> customerReservations = new ArrayList<Reservation>();
        ArrayList<Integer> reservationIndices = customerReservationMap.get(customer);
        if (reservationIndices != null) {
            for (int reservationIndex : reservationIndices) {
                customerReservations.add(reservations.get(reservationIndex));
            }
        }
        return customerReservations;
    }

    /**
     * Print all reservations
     */
    public void printAllReservation() {
        for(Reservation reservation: reservations) {
            System.out.println(reservation);
        }
    }
}
