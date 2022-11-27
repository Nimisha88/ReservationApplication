package model;

import java.util.regex.Pattern;

public class Customer {
    String firstname;
    String lastname;
    String email;

    private static final String emailRegex = "^(.+)@(.+)\\.com$";
    private static final Pattern pattern = Pattern.compile(emailRegex);

    private static boolean isValidEmail(String email) {
        return pattern.matcher(email).matches();
    }

    public Customer(String email, String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public static Customer createNewCustomer(String email, String firstname, String lastname) throws IllegalArgumentException {
        if(isValidEmail(email)) {
            return new Customer(email, firstname, lastname);
        } else {
            throw new IllegalArgumentException("Email does not match the format name@domain.com. Account not created.");
        }
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }

    @Override
    public String toString() {
        return "Customer *** " +
                " Name: " + firstname + " " + lastname + " *** " +
                "Email: " + email + " *** ";
    }
}
