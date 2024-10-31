package model;

import utils.MyArrayList;
import utils.MyList;

public class User {

    private String email;
    private String password;
    private Role role;

    private final MyList<Book> userBooks;

    public User(String email, String password) {
        this.password = password;
        this.email = email;
        this.role = Role.USER;
        this.userBooks = new MyArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public MyList<Book> getUserBooks() {
        return userBooks;
    }

    @Override
    public String toString() {
        return "User{" +
                "email = '" + email + '\'' +
                ", userBooks = " + userBooks +
                '}';
    }
}
