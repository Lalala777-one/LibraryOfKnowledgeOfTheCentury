package tests;


import model.Book;
import model.Role;
import model.User;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.MainService;
import service.MainServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.MyList;

public class TestMainService {

    BookRepository repositoryBook = new BookRepositoryImpl();
    UserRepository repositoryUser = new UserRepositoryImpl();
    protected MainService mainService;
    protected User activeUser;

    @BeforeEach
    public void setUp() {
        // Инициализация сервиса
        mainService = new MainServiceImpl( repositoryBook, repositoryUser );

        // Инициализация активного пользователя (если требуется)
        activeUser = null; // или создайте нового пользователя для тестов
    }

    @Test
    public void testAddBook_AdminRole() {
        activeUser = new User("testAdmin@gmail.com", "qwerty123!!");
        activeUser.setRole(Role.ADMIN);

        mainService.addBook("Гарри Поттер и Орден Феникс", "Дж.К. Роулинг", "фэнтези");

        Book addedBook = repositoryBook.getBookByTitle("Гарри Поттер и Орден Феникс");
        assertNotNull(addedBook, "Книга може бути додана тільки Адміністратором");
        assertEquals("Гарри Поттер и Орден Феникс", addedBook.getTitle());
        assertEquals("Дж.К. Роулинг", addedBook.getAuthor());
    }


    @Test
    public void testAddBook_NonAdminRole() {
        activeUser.setRole(Role.USER);
        mainService.addBook("Гарри Поттер и Принц-полукровка", "Дж.К. Роулинг", "фэнтези");

        Book addedBook = repositoryBook.getBookByTitle("Гарри Поттер и Принц-полукровка");
        assertNull(addedBook, "Книга не может быть додана Пользователем");
    }

    @Test
    public void testGetAllBooks_UserLoggedIn() {
        activeUser.setRole(Role.USER);
        mainService.addBook("Портрет Дориана Грея", "Оскар Уайльд", "мистика");
        mainService.addBook("Оно", "Стивен Кинг", "Мистика");

        MyList<Book> allBooks = mainService.getAllBooks();
        assertEquals(2, allBooks.size(), "Должен возвращать все книги в репозитории");
    }

    @Test
    public void testGetAllBooks_UserNotLoggedIn() {
        activeUser.setRole(Role.USER);
        MyList<Book> allBooks = mainService.getAllBooks();

        assertTrue(allBooks.isEmpty(), "Должен возвращать пустой список, если пользователь не вошел в систему");
    }

    @Test
    public void testGetAllFreeBooks() {
        activeUser.setRole(Role.USER);
        mainService.addBook("Сияние", "Стивен Кинг", "ужасы");

        MyList<Book> freeBooks = mainService.getAllFreeBooks();
        assertEquals(1, freeBooks.size(), "Следует возвращать только бесплатные книги");
        assertEquals("Title5", freeBooks.get(0).getTitle());
    }

    @Test
    public void testGetBookByGenre() {
        activeUser.setRole(Role.USER);
        mainService.addBook("Title6", "Author6", "фэнтези");
        mainService.addBook("Title7", "Author7", "фэнтези");
        mainService.addBook("Title8", "Author8", "Science Fiction");

        MyList<Book> fantasyBooks = mainService.getBookByGenre("фэнтези");
        assertEquals(2, fantasyBooks.size(), "Должны возвращаться книги, соответствующие указанному жанру");
        for (Book book : fantasyBooks) {
            assertEquals("фэнтези", book.getGenre(), "Каждая книга должна соответствовать указанному жанру");
        }
    }

}



