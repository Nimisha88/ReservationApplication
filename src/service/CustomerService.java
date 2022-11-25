package service;

import model.Customer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    public static Map<String, Customer> customers = new HashMap<String, Customer>();

    public static Customer getCustomer(String email) {
        return customers.get(email);
    }

    public static void addCustomer(String email, String firstname, String lastname) {
        customers.put(email, new Customer(firstname, lastname, email));
    }

    public static Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
