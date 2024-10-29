package repository;

import model.Book;
import model.User;
import utils.MyList;

public class UserRepositoryImpl implements UserRepository {
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
