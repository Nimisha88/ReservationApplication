package service;

import model.Customer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomerService {

    public static Map<String, Customer> customers = new HashMap<String, Customer>();

    public static Customer getCustomer(String email) {
        return customers.get(email);
    }

    public static void addCustomer(String email, String firstname, String lastname) throws IllegalArgumentException {
        Customer customer = Customer.createNewCustomer(email, firstname, lastname);
        customers.put(email, customer);
    }

    public static Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
