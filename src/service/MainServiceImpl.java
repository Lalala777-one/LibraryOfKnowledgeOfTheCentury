package service;

import model.Book;
import model.Role;
import model.User;
import repository.BookRepository;
import repository.UserRepository;
import utils.MyArrayList;
import utils.MyList;

public class MainServiceImpl  implements  MainService{

    private final BookRepository repositoryBook;
    private final UserRepository repositoryUser;

    private User activeUser;

    public MainServiceImpl(BookRepository repositoryBook, UserRepository repositoryUser) {
        this.repositoryBook = repositoryBook;
        this.repositoryUser = repositoryUser;
    }

    @Override
    public void addBook(String title, String author, String genre) {
        if (activeUser == null || activeUser.getRole() != Role.ADMIN) {
            System.out.println("Добавление новой книги доступно только Администраторам");
            return;
        }
        if (repositoryBook.getBookByTitle(title) != null) {
            System.out.println("Книга з таким названием уже существует.");
            return;
        }
        repositoryBook.addBook(title, author, genre);
        System.out.println("Книга успешно добавлена: " + title);
    }

    @Override
    public MyList<Book> getAllBooks() {
        if (activeUser == null) {
            System.out.println("Пользователь не вошел в систему. Пожалуйста, войдите в систему для просмотра книг.");
            return new MyArrayList<>();
        }
        MyList<Book> books = repositoryBook.getAllBooks();
        System.out.println("Общее количество книг: " + books.size());
        return books;
    }

    @Override
    public MyList<Book> getAllFreeBooks() {
        if (activeUser == null) {
            System.out.println("Пользователь не вошел в систему. Пожалуйста, войдите в систему для просмотра книг.");
            return new MyArrayList<>();
        }
        MyList<Book> freeBooks = repositoryBook.getAllFreeBooks();
        System.out.println("Количество свободных книг: " + freeBooks.size());
        return freeBooks;
    }

    @Override
    public MyList<Book> getAllBusyBooks() {
        if (activeUser == null) {
            System.out.println("Пользователь не вошел в систему. Пожалуйста, войдите в систему для просмотра книг.");
            return new MyArrayList<>();
        }
        MyList<Book> busyBooks = repositoryBook.getAllBusyBooks();
        System.out.println("Количество занятых книг: " + busyBooks.size());
        return busyBooks;
    }

    @Override
    public Book getBookById(int id) {
        if (activeUser == null) {
            System.out.println("Пользователь не вошел в систему. Пожалуйста, войдите в систему для просмотра книг.");
            return null;
        }
        if (id <= 0) {
            System.out.println("Неверный идентификатор книги.");
            return null;
        }
        Book book = repositoryBook.getBookById(id);
        if (book == null) {
            System.out.println("Книга с таким идентификатором не найдена.");
        }
        return book;
    }

    @Override
    public MyList<Book> getBookByAuthor(String author) {
        if (activeUser == null) {
            System.out.println("Пользователь не вошел в систему. Пожалуйста, войдите в систему для просмотра книг.");
            return null;
        }
        if (author == null || author.isEmpty()) {
            System.out.println("Имя автора не может быть пустым.");
            return null;
        }
        MyList<Book> booksByAuthor = repositoryBook.getBookByAuthor(author);
        if (booksByAuthor.isEmpty()) {
            System.out.println("Книги автора " + author + " не найдены.");
        }
        return booksByAuthor;
    }

    @Override
    public Book getBookByTitle(String title) {
        if (activeUser == null) {
            System.out.println("Пользователь не вошел в систему. Пожалуйста, войдите в систему для просмотра книг.");
            return null;
        }
        if (title == null || title.isEmpty()) {
            System.out.println("Название книги не может быть пустым.");
            return null;
        }
        Book book = repositoryBook.getBookByTitle(title);
        if (book == null) {
            System.out.println("Книга с таким названием не найдена.");
        }
        return book;
    }

    @Override
    public MyList<Book> getBookByGenre(String genre) {
        if (activeUser == null) {
            System.out.println("Пользователь не вошел в систему. Пожалуйста, войдите в систему для просмотра книг.");
            return null;
        }
        if (genre == null || genre.isEmpty()) {
            System.out.println("Жанр не может быть пустым.");
            return null;
        }
        MyList<Book> booksByGenre = repositoryBook.getBookByGenre(genre);
        if (booksByGenre.isEmpty()) {
            System.out.println("Книги жанра " + genre + " не найдены.");
        }
        return booksByGenre;
    }

    @Override
    public void deleteBook(Book book) {
        if (activeUser == null || activeUser.getRole() != Role.ADMIN) {
            System.out.println("Доступно только Администраторам");
            return;
        }
        Book existingBook = repositoryBook.getBookById(book.getId());
        if (existingBook == null) {
            System.out.println("Книга за таким Id не найдена");
            return;
        }

        repositoryBook.deleteBook(existingBook);
        System.out.println("Книга удалена: " + book.getId());
    }

    //-----------------------------------------Alla Nazarenko------------------------------

    @Override
    public boolean takeBook(int id) {
         /*
        получить книгу из репозитория
        проверить, что она найдена и свободна,
        если нет - закончить работу метода,
        если да  - пометить машину как занятую,
                   добавить машину в список книг текущего пользователя
         */
        Book book = repositoryBook.getBookById(id);
        if (book == null || book.isBusy()) {
            return false;
        } else {

        }

        return true;
    }

    @Override
    public boolean returnBook(int id) {
        return false;
    }

    @Override
    public MyList<Book> getAllUsersBook(User user) {
        return null;
    }

    @Override
    public User registerUser(String email, String password) {
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
