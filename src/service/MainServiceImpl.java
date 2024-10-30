package service;

import model.Book;
import model.User;
import repository.BookRepository;
import repository.UserRepository;
import utils.MyArrayList;
import utils.MyList;

public class MainServiceImpl implements MainService {

    private final BookRepository repositoryBook;
    private final UserRepository repositoryUser;

    private User activeUser;

    public MainServiceImpl(BookRepository repositoryBook, UserRepository repositoryUser) {
        this.repositoryBook = repositoryBook;
        this.repositoryUser = repositoryUser;
    }

    @Override
    public void addBook(String title, String author, String genre) {
    }


    @Override
    public MyList<Book> getAllBooks() {
        return null;
    }

    @Override
    public MyList<Book> getAllFreeBooks() {
        return null;
    }

    @Override
    public MyList<Book> getAllBusyBooks() {
        return null;
    }

    @Override
    public Book getBookById(int id) {
        return null;
    }

    @Override
    public Book getBookByAuthor(String author) {
        return null;
    }

    @Override
    public Book getBookByTitle(String title) {
        return null;
    }

    @Override
    public void deleteBook(Book book) {

    }

    //-----------------------------------------Alla Nazarenko------------------------------

    //-----------------------------------------Methods of Books------------------------------

        @Override
    public boolean takeBook(int id) {
         /*
        получить книгу из репозитория и присвоить её в переменную
        проверить, что она найдена и свободна,
        если нет - закончить работу метода,
        если да  - пометить книгу как занятую,
                   добавить книгу в список книг текущего пользователя
         */
        Book book = repositoryBook.getBookById(id);
        if (book == null || book.isBusy()) {
            System.out.println("Книга не найдена или занята");
            return false;
        } else {
            book.isBusy();
            activeUser.getUserBooks().add(book);
            System.out.println("Книга успешно взята читателем");
            return true;
        }
    }

    @Override
    public boolean returnBook(int id) {
        /*
        получить книгу из репозитория и присвоить её в переменную
        проверить, что она найдена и занята,
        если нет - закончить работу метода,
        если да  - пометить книгу как свободную,
                   удалить книгу из списка книг текущего пользователя
         */
        Book book = repositoryBook.getBookById(id);
        if (book == null || !book.isBusy()) {
            System.out.println("Книга не найдена");
            return false;
        } else {
            book.setBusy(false);
            activeUser.getUserBooks().remove(book);
            System.out.println("Книга успешно возвращена в библиотеку");
            return true;
        }
    }

    @Override
    public MyList<Book> getAllUsersBook(User user) {
        /*
        проверяем переданного в метод юзера:
            не равен ли null
            есть ли он в нашей базе данных (по имейлу)
        получаем список книг этого юзера из репозитория
         */
        if (user == null || repositoryUser.isEmailExist(user.getEmail())) {
            System.out.println("Пользователь не найден");
            return new MyArrayList<>();
        } else {
            MyList<Book> usersBooks = repositoryUser.getAllUsersBook(user);
            return usersBooks;
        }
    }

    //-----------------------------------------Methods of Users------------------------------

    @Override
    public User registerUser(String email, String password) {
        if (repositoryUser.isEmailExist(email)) {
            System.out.println("Пользователь с таким email уже существует");
            return null;
        } else {

        }
        return null;
    }




    @Override
    public boolean loginUser(String email, String password) {
        return false;
    }

    @Override
    public boolean logOutUser() {
        return false;
    }

    @Override
    public void updateBook(int id) {

    }

    @Override
    public User whoHasBook(int id) {
        return null;
    }
}
