package repository;

import model.Book;
import model.User;
import utils.MyArrayList;
import utils.MyList;

public class UserRepositoryImpl implements UserRepository {

    private final MyList<User> users;

    public UserRepositoryImpl() {

        users = new MyArrayList<>();
    }


    @Override
    public void addUser(String email, String password) {

    }

    @Override
    public boolean isEmailExist(String email) {
        return false;
    }

    @Override
    public User getUserEmail(String email) {
        return null;
    }

    @Override
    public MyList<User> getAllUsers() {
        return null;
    }

    @Override
    public MyList<Book> getAllUsersBook(User user) {
        return null;
    }
}
