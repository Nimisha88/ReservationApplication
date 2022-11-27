package model;

import java.util.regex.Pattern;

public class Customer {
    String firstname;
    String lastname;
    String email;

    private static final String emailRegex = "^(.+)@(.+)\\.com$";
    private static final Pattern pattern = Pattern.compile(emailRegex);

    /**
     * Helper function to validate whether email matches format name@domain.com
     * @param email Customer email
     * @return True if valid email, False if not
     */
    private static boolean isValidEmail(String email) {
        return pattern.matcher(email).matches();
    }

    /**
     * Customer constructor
     * @param email Customer email
     * @param firstname Customer Firstname
     * @param lastname Customer Lastname
     */
    private Customer(String email, String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    /**
     * Create new customer after validating the email
     * @param email Customer email
     * @param firstname Customer Firstname
     * @param lastname Customer Lastname
     * @return New Customer
     * @throws IllegalArgumentException
     */
    public static Customer createNewCustomer(String email, String firstname, String lastname) throws IllegalArgumentException {
        if(isValidEmail(email)) {
            return new Customer(email, firstname, lastname);
        } else {
            throw new IllegalArgumentException("Email does not match the format name@domain.com. Account not created.");
        }
    }

    /**
     * Fetch Customer's full name
     * @return Customer's full name
     */
    public String getFullName() {
        return firstname + " " + lastname;
    }

    /**
     * Overriding toString
     * @return Customer Object as String
     */
    @Override
    public String toString() {
        return "Customer *** " +
                " Name: " + firstname + " " + lastname + " *** " +
                "Email: " + email + " *** ";
    }
}
