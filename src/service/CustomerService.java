package service;

import model.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    public static Map<String, Customer> customers = new HashMap<String, Customer>();

    /**
     * Fetch Customer Account
     * @param email Customer email
     * @return Customer
     */
    public static Customer getCustomer(String email) {
        return customers.get(email);
    }

    /**
     * Add a new Customer
     * @param email Customer email
     * @param firstname Customer Firstname
     * @param lastname Customer Lastname
     * @throws IllegalArgumentException
     */
    public static void addCustomer(String email, String firstname, String lastname) throws IllegalArgumentException {
        Customer customer = Customer.createNewCustomer(email, firstname, lastname);
        customers.put(email, customer);
    }

    /**
     * Fetch all customers
     * @return Collection of Customers
     */
    public static Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
