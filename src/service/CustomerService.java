package service;

import model.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static CustomerService INSTANCE;
    private Map<String, Customer> customers = new HashMap<String, Customer>();

    /**
     * CustomerService Constructor
     */
    private CustomerService() {
        //Do nothing!
    }

    /**
     * Getting instance of singleton object
     * @return CustomerService object
     */
    public static CustomerService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CustomerService();
        }
        return INSTANCE;
    }

    /**
     * Fetch Customer Account
     * @param email Customer email
     * @return Customer
     */
    public Customer getCustomer(String email) {
        return customers.get(email);
    }

    /**
     * Add a new Customer
     * @param email Customer email
     * @param firstname Customer Firstname
     * @param lastname Customer Lastname
     * @throws IllegalArgumentException
     */
    public void addCustomer(String email, String firstname, String lastname) throws IllegalArgumentException {
        Customer customer = Customer.createNewCustomer(email, firstname, lastname);
        customers.put(email, customer);
    }

    /**
     * Fetch all customers
     * @return Collection of Customers
     */
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
