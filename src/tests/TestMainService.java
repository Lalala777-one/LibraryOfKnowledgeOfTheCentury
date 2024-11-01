package tests;


import model.User;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.MainService;
import service.MainServiceImpl;

import org.junit.jupiter.api.BeforeEach;

public abstract class TestMainService {

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
}


