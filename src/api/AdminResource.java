package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;
import java.util.Collection;
import java.util.List;

public class AdminResource {
    /**
     * Fetch a customer
     * @param email Customer email
     * @return Customer
     */
    public Customer getCustomer(String email) {
        return CustomerService.getCustomer(email);
    }

    /**
     * Add a list of rooms
     * @param rooms List of rooms to be added
     */
    public void addRoom(List<IRoom> rooms) {
        for (IRoom room: rooms) {
            ReservationService.addRoom(room);
        }
    }

    /**
     * Fetch all rooms
     * @return Rooms
     */
    public Collection<IRoom> getAllRooms() {
        return ReservationService.getAllRooms();
    }

    /**
     * Fetch all customers
     * @return Customers
     */
    public Collection<Customer> getAllCustomers() {
        return CustomerService.getAllCustomers();
    }

    /**
     * Print all reservations
     */
    public void displayAllReservations() {
        ReservationService.printAllReservation();
    }
}
