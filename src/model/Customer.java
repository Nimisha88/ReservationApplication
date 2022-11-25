package model;

import java.util.regex.Pattern;

public class Customer {

    public static final String emailRegex = "^(.+)@(.+).com$";
    public static final Pattern pattern = Pattern.compile(emailRegex);
    String firstname;
    String lastname;
    String email;

    private boolean isValidEmail(String email) {
        return pattern.matcher(email).matches();
    }

    public Customer(String firstname, String lastname, String email) throws IllegalArgumentException {
        this.firstname = firstname;
        this.lastname = lastname;
        if(!isValidEmail(email)) {
            throw new IllegalArgumentException("Email does not match the format name@domain.com");
        } else {
            this.email = email;
        }
    }

    @Override
    public String toString() {
        return "Customer *** " +
                " Name: " + firstname + " " + lastname + " *** " +
                "Email: " + email + " *** ";
    }
}
