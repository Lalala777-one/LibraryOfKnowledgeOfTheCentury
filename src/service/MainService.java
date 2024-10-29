package service;

import model.Book;
import model.User;
import utils.MyList;

public interface MainService {

    // ---------------- методы для Books -------------------------------------//

    void addBook(String title, String author);

    MyList<Book> getAllBooks();

    MyList<Book> getAllFreeBooks();

    MyList<Book> getAllBusyBooks();

    Book getBookById(int id);

    Book getBookByAuthor(String author);

    Book getBookByTitle(String title);

    void deleteBook(Book book); // нельзя взять на прокат, больше не доступна // Доступно только АДМИНУ

    boolean takeBook(int id);

    boolean returnBook(int id); // пользователь прочитал и возвращает книгу в библиотеку и она снова достуна

    // список книг которые сейчас у конкретного пользователя ОПЦИОНАЛЬНО
    MyList<Book> getAllUsersBook(User user);


    // ---------------------- методы для USER -----------------------------//

    User registerUser(String email, String password);

    boolean loginUser(String email, String password);

    boolean logOutUser(); // если USER залогинен, user == null


    // --------------------- методы для Admin -------------------------- //

    void updateBook(int id); // ОПЦИОНАЛЬНО
    User whoHasBook(int id); // возвращает user у кого сейчас книга









}
