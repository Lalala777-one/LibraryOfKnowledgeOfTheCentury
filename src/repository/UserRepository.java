package repository;

import model.Book;
import model.User;
import service.MainService;
import utils.MyList;

public interface UserRepository {

    void addUser(String email, String password);

    boolean isEmailExist(String email);

    User getUserEmail(String email);


    // ОПЦИОНАЛЬНО

    // пользователи библиотеки
    MyList<User> getAllUsers();


    // список книг которые сейчас у конкретного пользователя
    MyList<Book> getAllUsersBook(User user);




}
