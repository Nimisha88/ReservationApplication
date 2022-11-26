package model;

public class Customer {
    String firstname;
    String lastname;
    String email;

    public Customer(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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
