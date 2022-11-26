package service;

import model.Customer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomerService {

    public static Map<String, Customer> customers = new HashMap<String, Customer>();

    private static boolean isValidEmail(String email) {
        String emailRegex = "^(.+)@(.+)\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static Customer getCustomer(String email) {
        return customers.get(email);
    }

    public static void addCustomer(String email, String firstname, String lastname) {
        if (isValidEmail(email)) {
            customers.put(email, new Customer(firstname, lastname, email));
        } else {
            throw new IllegalArgumentException("Email does not match the format name@domain.com. Account not created.");
        }
    }

    public static Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
