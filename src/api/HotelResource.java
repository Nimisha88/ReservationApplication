package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    public Customer getCustomer(String email) {
        return CustomerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) throws IllegalArgumentException {
        CustomerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return ReservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String email, IRoom room, Date checkIn, Date checkOut) {
        Customer customer = getCustomer(email);
        if (customer != null) {
            return ReservationService.reserveARoom(customer, room, checkIn, checkOut);
        } else {
            return null;
        }
    }

    public Collection<Reservation> getCustomersReservation(String email) {
        Customer customer = getCustomer(email);
        if (customer != null) {
            return ReservationService.getCustomersReservation(customer);
        } else {
            return null;
        }
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return ReservationService.findRooms(checkIn, checkOut);
    }
}
