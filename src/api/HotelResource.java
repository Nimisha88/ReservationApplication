package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;
import java.util.Collection;
import java.util.Date;

public class HotelResource {
    /**
     * Fetch customer
     * @param email Customer email
     * @return Customer
     */
    public Customer getCustomer(String email) {
        return CustomerService.getCustomer(email);
    }

    /**
     * Create a new customer account
     * @param email Customer email
     * @param firstName Customer firstname
     * @param lastName Customer lastname
     * @throws IllegalArgumentException
     */
    public void createACustomer(String email, String firstName, String lastName) throws IllegalArgumentException {
        CustomerService.addCustomer(email, firstName, lastName);
    }

    /**
     * Fetch a room by room number
     * @param roomNumber Room Number
     * @return Room
     */
    public IRoom getRoom(String roomNumber) {
        return ReservationService.getARoom(roomNumber);
    }

    /**
     * Book a room
     * @param email Customer email
     * @param room Room to be reserved
     * @param checkIn Check In date
     * @param checkOut Check Out date
     * @return New Reservation
     */
    public Reservation bookARoom(String email, IRoom room, Date checkIn, Date checkOut) {
        Customer customer = getCustomer(email);
        if (customer != null) {
            return ReservationService.reserveARoom(customer, room, checkIn, checkOut);
        } else {
            return null;
        }
    }

    /**
     * Fetch all reservations of a Customer
     * @param email Customer email
     * @return Reservations
     */
    public Collection<Reservation> getCustomersReservation(String email) {
        Customer customer = getCustomer(email);
        if (customer != null) {
            return ReservationService.getCustomersReservation(customer);
        } else {
            return null;
        }
    }

    /**
     * Fina all available rooms between Check In date and Check Out date
     * @param checkIn Check In date
     * @param checkOut Check Out date
     * @return Rooms
     */
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return ReservationService.findRooms(checkIn, checkOut);
    }
}
