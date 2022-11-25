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

    public void createACustomer(String email, String firstName, String lastName) {
        CustomerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return ReservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String email, IRoom room, Date checkIn, Date checkOut) {
        return ReservationService.reserveARoom(getCustomer(email), room, checkIn, checkOut);
    }

    public Collection<Reservation> getCustomersReservation(String email) {
        return ReservationService.getCustomersReservation(getCustomer(email));
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return ReservationService.findRooms(checkIn, checkOut);
    }
}
