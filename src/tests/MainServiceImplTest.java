package tests;

import model.Book;
import model.Role;
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
import utils.MyList;

import java.util.List;

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

    @Test
    public void testAddBook_AsAdmin() {
        activeUser.setRole(Role.ADMIN);

        mainService.addBook("Гарри Поттер и Принц-полукровка", "Дж.К. Роулинг", "фэнтези");

        Book addedBook = bookRepository.getBookByTitle("Гарри Поттер и Принц-полукровка");
        assertNotNull(addedBook, "Книга должна быть добавлена администраторами");
        assertEquals("Гарри Поттер и Принц-полукровка", addedBook.getTitle(), "Название книги не соответствует ожиданиям");
    }

    @Test
    public void testAddBook_NonAdminRole() {
        activeUser.setRole(Role.USER);

        mainService.addBook("Гарри Поттер и Орден Феникса", "Дж.К. Роулинг", "фэнтези");

        Book addedBook = bookRepository.getBookByTitle("Гарри Поттер и Принц-полукровка");
        assertNull(addedBook, "Книга не может быть добавлена пользователем без прав администратора!");
    }

    @Test
    public void testGetAllBooks() {
        MyList<Book> allBooks = mainService.getAllBooks();

        assertNotNull(allBooks, "Список книг не должен быть пустым");

        int expectedBooksCount = 17;
        assertEquals(expectedBooksCount, allBooks.size(), "Количество книг не соответствует ожидаемому");

        // Дополнительная проверка: Убедимся, что каждый элемент в списке является книгой
        for (Book book : allBooks) {
            assertNotNull(book, "Каждый элемент в списке должен быть объектом книги");
        }
    }

    @Test
    public void testGetAllFreeBooks() {
        MyList<Book> freeBooks = mainService.getAllFreeBooks();

        for (Book book : freeBooks) {
            assertFalse(book.isBusy(), "Книга должна быть свободной");
        }
    }

    @Test
    public void testGetBookByGenre() {
        // Книги жанра "фэнтези"
        MyList<Book> fantasyBooks = mainService.getBookByGenre("фэнтези");

        for (Book book : fantasyBooks) {
            assertEquals("фэнтези", book.getGenre());
        }

        // Проверяем, чтобы количество книг в списке соответствовало ожидаемому
        assertEquals(5, fantasyBooks.size());
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