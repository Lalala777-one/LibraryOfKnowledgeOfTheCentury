package tests;

import model.Book;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.MainService;
import service.MainServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class MainServiceImplTest {

    private MainService mainService;
    private final BookRepository bookRepository = new BookRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private User activeUser;

    @BeforeEach
    public void setUp() {
        mainService = new MainServiceImpl(bookRepository, userRepository);
        activeUser = new User("alla@gmail.com", "qwerty1Q$");
        userRepository.addUser("alla@gmail.com", "qwerty1Q$");
        mainService.loginUser("alla@gmail.com", "qwerty1Q$");
    }

    //проверка на УСПЕШНЫЙ возрат книги
    @Test
    void returnBookSuccess() {
        //создали книгу, добавили её в репоб пометили как занятую, и добавили в список пользователя
        Book book = new Book(123456, "Мастер", "Булгаков", "Роман");
        book.setBusy(true);
        bookRepository.addBook("Мастер", "Булгаков", "Роман");
        activeUser.getUserBooks().add(book);

        // Проверяем состояние перед вызовом returnBook
        System.out.println("Книга занята? -> " + book.isBusy());
        System.out.println("Книги пользователя до возврата: " + activeUser.getUserBooks());

        // вызываем метод
        boolean returnResult = mainService.returnBook(123456);

        // Проверяем результат
        Assertions.assertTrue(returnResult, "Книга должна быть возвращена читателем");
        Assertions.assertFalse(book.isBusy(), "Книга должна быть помечена как свободная");
        Assertions.assertFalse(activeUser.getUserBooks().contains(book), "Книга должна быть удалена из списка книг читателя");
    }

    // проверка на ПРОВАЛЬНЫЙ возврат книги по причине того, что пользователь незалогиненый
    @Test
    public void testReturnBookNotLogIn() {
        //создали книгу
        Book book = new Book(123456, "Мастер", "Булгаков", "Роман");
        book.setBusy(true);
        bookRepository.addBook("Мастер", "Булгаков", "Роман");
        activeUser.getUserBooks().add(book);

        //пользователь вышел из системы
        mainService.logOutUser();
        //Assertions.assertNull(mainService.getActiveUser(), "Пользователь должен быть разлогинен");
        //TODO почему не срабатывает геттер на ActiveUser?

        // проверяем успешность возврата книги
        boolean returnResult = mainService.returnBook(123456);
        Assertions.assertFalse(returnResult, "Метод должен вернуть false");
    }

    // проверка на ПРОВАЛЬНЫЙ возврат книги по причине того, что книга свободна
    @Test
    public void testReturnBookNotBusy() {
        //создали книгу, добавили её в репо, пометили как свободную, и добавили в репозиторий
        Book book = new Book(123456, "Мастер", "Булгаков", "Роман");
        book.setBusy(false);
        bookRepository.addBook("Мастер", "Булгаков", "Роман");


        // проверяем успешность возврата книги
        boolean returnResult = mainService.returnBook(123456);
        Assertions.assertFalse(returnResult, "Метод должен вернуть false, если книга не занята");
    }

    // проверка на ПРОВАЛЬНЫЙ возврат книги по причине того, что книги нет в списке активного пользователя
    @Test
    public void testReturnBookNotByActivUser() {
        //создали книгу, добавили её в репо, пометили как свободную, и добавили в репозиторий
        Book book = new Book(123456, "Мастер", "Булгаков", "Роман");
        book.setBusy(true);
        bookRepository.addBook("Мастер", "Булгаков", "Роман");
        //но не добавляем её в список активного пользователя


        // проверяем успешность возврата книги
        boolean returnResult = mainService.returnBook(123456);
        Assertions.assertFalse(returnResult, "Метод должен вернуть false, если книга не занята");
    }
}