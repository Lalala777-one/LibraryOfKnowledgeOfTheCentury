package repository;

import model.Book;
import model.Role;
import model.User;
import utils.MyArrayList;
import utils.MyList;

import java.util.concurrent.atomic.AtomicInteger;

public class UserRepositoryImpl implements UserRepository {

    private final MyList<User> users;
    // private final AtomicInteger usersCount = new AtomicInteger(1);

    public UserRepositoryImpl() {
        users = new MyArrayList<>();
        addUsers();
    }

    private void addUsers() {

        users.addAll(
                new User("blueocean03@gmail.com", "b_5R$hN8s"),
                new User("greenleaf04@gmail.com", "S*2jC?x7s"),
                new User("redrose05@gmail.com", "H1$sKv(3s)"),
                new User("brightmoon06@gmail.com", "jM9?dF@7ps"),
                new User("silvercloud07@gmail.com", "T*4qW_8zRd"),
                new User("goldenriver08@gmail.com", "c3$Qx)2Fs"),
                new User("quietforest09@gmail.com", "L@8nK?5vd"),
                new User("happyplace10@gmail.com", "N*3bF_7ks"),
                new User("dreamcatcher11@gmail.com", "S9!rJm?4s"),
                new User("wildflower12@gmail.com", "xR3%yK(6)s"),
                new User("crystalclear13@gmail.com", "D1$eS[8ps]"),
                new User("oceanbreeze14@gmail.com", "W9!kM*2zs"),
                new User("starrynight15@gmail.com","e5&Jm0Hs"),
                new User("peacefulmind16@gmail.com", "R2@qW5ps"),
                new User("sunsetview17@gmail.com", "T4!bL6rs")
        );
    }


    @Override
    public void addUser(String email, String password) {
        User user = new User(email, password);
        users.add(user);

    }


    @Override
    public boolean isEmailExist(String email) {
        for (User user : users){
            if(user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }


    @Override
    public User getUserEmail(String email) {
        for (User user : users){
            if (user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    @Override
    public MyList<User> getAllUsers() {
        return users;
    }

    @Override
    public MyList<Book> getAllUsersBook(User user) {
        return user.getUserBooks();

    }
}
