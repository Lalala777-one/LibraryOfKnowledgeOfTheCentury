package tests;

import model.Book;
import model.Role;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.MainService;
import service.MainServiceImpl;
import utils.MyList;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

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

    // Test addBook
// використати "РЕСУРСИ" з тестів Сергія раніше...
//    @Test
//    void testAddBookAsAdmin() {
//        String title = "Новая книга";
//        String author = "Автор Книги";
//        String genre = "Фантастика";
//
//        mainService.addBook(title, author, genre);
//
//        MyList<Book> addedBook = bookRepository.getBookByTitle(title); // Отримайте додану книгу
//        assertNotNull(addedBook);
//        assertEquals(title, addedBook.getTitle());
//        assertEquals(author, addedBook.getAuthor());
//        assertEquals(genre, addedBook.getGenre());
//    }

    @ParameterizedTest
    @MethodSource("provideBooksForTestAddBookAsAdmin")
    void testAddBookAsAdmin(String title, String author, String genre){
        mainService.addBook(title, author, genre);

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() ||
        !title.matches("^[\\p{L}0-9\\s`\\-,.!?()&]+$") ||
        !author.matches("^[\\p{L}0-9\\s`\\-]+$") ||
        !genre.matches("^\\p{L}\\s}+$") ||
        bookRepository.getBookByTitle(title) != null) {
            assertNull(bookRepository.getBookByTitle(title), "The book should not be added with incorrect data.");
        } else {
            assertNotNull(bookRepository.getBookByTitle(title), "The book must be added successfully.");
        }
    }

    static Stream<Arguments> provideBooksForTestAddBookAsAdmin() {
        return Stream.of(
                Arguments.of("Harry Potter", "J.K. Rowling", "fantasy"),
                Arguments.of("Harry Potter 2", "J.K. Rowling", "fantasy"),
                Arguments.of("", "", "комедія"),  // Неправильні дані
                Arguments.of("Нова Книга", "Автор 123", "наука"),  // Неправильний автор
                Arguments.of("Correct Title", "Author Name", "роман")
        );
    }

//
//    @Test
//    public void testAddBook_NonAdminRole() {
//        activeUser.setRole(Role.USER);
//
//        mainService.addBook("Гарри Поттер и Орден Феникса", "Дж.К. Роулинг", "фэнтези");
//
//        Book addedBook = bookRepository.getBookByTitle("Гарри Поттер и Принц-полукровка");
//        assertNull(addedBook, "Книга не может быть добавлена пользователем без прав администратора!");
//    }

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