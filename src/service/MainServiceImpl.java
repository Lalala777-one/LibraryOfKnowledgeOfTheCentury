package service;

import model.Book;
import model.Role;
import model.User;
import repository.BookRepository;
import repository.UserRepository;
import utils.MyArrayList;
import utils.MyList;

import java.util.Scanner;

public class MainServiceImpl implements MainService {

    private final BookRepository repositoryBook;
    private final UserRepository repositoryUser;

    private User activeUser;

    public MainServiceImpl(BookRepository repositoryBook, UserRepository repositoryUser) {
        this.repositoryBook = repositoryBook;
        this.repositoryUser = repositoryUser;
    }

    public User getActiveUser() {
        return activeUser;
    }

    @Override
    public void addBook(String title, String author, String genre) {
        if (activeUser == null || activeUser.getRole() != Role.USER) {
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
        MyList<Book> books = repositoryBook.getAllBooks();
        System.out.println("Общее количество книг: " + books.size());
        return books;
    }

    @Override
    public MyList<Book> getAllFreeBooks() {
        MyList<Book> freeBooks = repositoryBook.getAllFreeBooks();
        System.out.println("Количество свободных книг: " + freeBooks.size());
        return freeBooks;
    }

    @Override
    public MyList<Book> getAllBusyBooks() {
        MyList<Book> busyBooks = repositoryBook.getAllBusyBooks();
        System.out.println("Количество занятых книг: " + busyBooks.size());
        return busyBooks;
    }

    @Override
    public Book getBookById(int id) {
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
        if (book == null) {
            System.out.println("Книга не может быть null");
            return;
        }

        if (activeUser == null || activeUser.getRole() != Role.USER) {
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
        if (activeUser == null) {
            System.out.println("Вы не вошли в библиотеку. Залогинтесь пожалуйста");
            return false;
        }
        Book book = repositoryBook.getBookById(id);
        if (book == null || book.isBusy()) {
            System.out.println("Книга не найдена или занята");
            return false;
        } else {
            book.setBusy(true);
            activeUser.getUserBooks().add(book);
            System.out.println("Книга успешно взята читателем");
            return true;
        }
    }

    @Override
    public boolean returnBook(int id) {
        if (activeUser == null) {
            System.out.println("Вы не вошли в библиотеку. Залогинтесь пожалуйста");
            return false;
        }
        /*
        получить книгу из репозитория и присвоить её в переменную
        проверить, что она найдена и занята,
        если нет - закончить работу метода,
        если да  - пометить книгу как свободную,
                   удалить книгу из списка книг текущего пользователя
         */
        Book book = repositoryBook.getBookById(id);
        if (book == null || !book.isBusy()) {
            System.out.println("Книга не найдена или свободна");
            return false;
        }

        // проверяем, принадлежит ли книга  активному пользователю
        if(!activeUser.getUserBooks().contains(book)) {
            System.out.println("Книга не находится у активного пользователя");
            return false;
        }

        book.setBusy(false);
        activeUser.getUserBooks().remove(book);
        System.out.println("Книга успешно возвращена в библиотеку");
        return true;
    }

    @Override
    public MyList<Book> getAllUsersBook(User user) {
        if (user == null && user.getRole() != Role.ADMIN) {
            System.out.println("У вас недостаточно прав для просмотра списка книг");
            return new MyArrayList<>();
        }
        /*
        проверяем переданного в метод юзера:
            не равен ли null
            есть ли он в нашей базе данных (по имейлу)
        получаем список книг этого юзера из репозитория
         */
        if (user == null || !repositoryUser.isEmailExist(user.getEmail())) {
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
        }
        // проверка имейла и пароля на валидность
        if (!isEmailValid(email)) {
            System.out.println("Невалидный имейл");
            return null;
        }
        if (!isPasswordValid(password)) {
            System.out.println("Невалидный пароль");
            return null;
        }

        User newUser = new User(email, password);
        repositoryUser.addUser(email, password);
        return newUser;
    }

    private boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        /*
        test_1@gmail.com - пример нормального имейла
            схема проверки:
        1) email должен содержать символ @ и только один
        2) email должен содержать символ . и после символа @
        3) в email после последней точки есть 2 и более символа
        4) email содержит алфавит; цифры; -; _; .; @
        5) в email перед символом @ должен быть хотя бы один символ
        6) в email первый символ должен быть буквой
         */

        // 1) email должен содержать символ @ и только один
        int indexAt = email.indexOf('@');
        if (indexAt == -1 || indexAt != email.lastIndexOf('@')) return false;

        // 2) email должен содержать символ . и после символа @
        int dotIndexAfterAt = email.indexOf('.', indexAt + 1);
        if (dotIndexAfterAt == -1) return false;

        // 3) в email после последней точки есть 2 и более символа
        int indexLastDot = email.lastIndexOf('.');
        if (indexLastDot == -1 || indexLastDot == email.length() - 1) return false;
        if ((email.length() - 1 - indexLastDot) < 2) return false;

        //  4) email содержит алфавит; цифры; -; _; .; @
        // берём каждый символ и проверяем, что он не явл-ся запещённым
        //если нахожу не подходящий - возвращаю false
        for (int i = 0; i < email.length(); i++) {
            char ch = email.charAt(i);
            if (!(Character.isAlphabetic(ch) || Character.isDigit(ch) || ch == '-' || ch == '_' || ch == '.' || ch == '@')) {
                return false;
            }
        }

        // 5) в email перед символом @ должен быть хотя бы один символ
        // если индекс @ стоит первым (индекс 0) - значит перед ним нет никого
        if (indexAt == 0) return false;

        // 6) в email первый символ должен быть буквой
        char firstChar = email.charAt(0);
        if (!Character.isLetter(firstChar)) return false;

        //если все проверки пройдены - возвращаем true (email подходит)
        return true;
    }

    private boolean isPasswordValid(String password) {
        /*
        1) длина >=8
        2) должна быть мин 1 цифра
        3) должна быть мин 1 маленькая буква
        4) должна быть мин 1 большая буква
        5) должен быть мин 1 спецсимвол из набора: ! @ % $ * () [] . ? _
        */

        // ВАЖНО!!! Должны все условия выполняться одновременно

        // 1) длина >=8
        boolean passwordLength = false;
        if (password == null || password.length() < 8) {
            System.out.println("Пароль должен содержать не менее 8 мисволов");
            return passwordLength = false;
        }

        boolean isDigit = false;
        boolean isUpperCase = false;
        boolean isLowerCase = false;
        boolean isSpecialSymbol = false;

        String symbols = "!@%$*()[].?_";

        // 2) должна быть мин 1 цифра
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isDigit(ch)) isDigit = true;
            if (Character.isUpperCase(ch)) isUpperCase = true;
            if (Character.isLowerCase(ch)) isLowerCase = true;
            if (symbols.indexOf(ch) >= 0) isSpecialSymbol = true;
        }
        return isDigit && isUpperCase && isLowerCase && isSpecialSymbol;
    }


    @Override
    public boolean loginUser(String email, String password) {

        // проверка пользователя по email
        if (email == null || !repositoryUser.isEmailExist(email)) {
            System.out.println("К сожалению вы не являетесь нашим пользователем. Зарегистрируйтесь пожалуйста");
            return false;
        }

        // получаем пользователя по email
        User user = repositoryUser.getUserEmail(email);

        // проверка, совпадает ли пароль c паролем этого юзера в базе
        if (!user.getPassword().equals(password)) {
            System.out.println("Неверный пароль");
            return false;
        }

        // Установить пользователя как активного
        this.activeUser = user;
        System.out.println("Добро пожаловать в ЗНАНИЯ ВЕКА!");

        return true;
    }

    @Override
    public boolean logOutUser() {
        if (activeUser == null) {
            System.out.println("Вы не авторизирированы");
            return false;
        } else {
            activeUser = null;
            System.out.println("До новых встреч в нашей библиотеке");
        }
        return true;
    }

    @Override
    public void updateBook(int id) {
        if (activeUser == null && activeUser.getRole() != Role.ADMIN) {
            System.out.println("У вас нет прав на редактирование книги");
            return;
        }
        if (id <= 0) {
            System.out.println("Неверный ID книги");
            return;
        } else {
            Book book = repositoryBook.getBookById(id);
            if (book == null) {
                System.out.println("Книга с ID " + id + " не найдена");
                return;
            }

            // Получение новых значений из пользовательского интерфейса или консоли
            String newTitle = getInput("Введите новое название книги: ");
            String newAuthor = getInput("Введите нового автора книги: ");
            String newGenre = getInput("Введите новый жанр книги: ");

            // Обновление параметров книги
            if (newTitle != null && !newTitle.isEmpty()) book.setTitle(newTitle);
            if (newAuthor != null && !newAuthor.isEmpty()) book.setAuthor(newAuthor);
            if (newGenre != null && !newGenre.isEmpty()) book.setGenre(newGenre);

            System.out.println("Книга " + book + " успешно обновлена");
        }
    }

    private String getInput(String call) {
        System.out.println(call);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public User whoHasBook(int id) {
        if (id <= 0) {
            System.out.println("Неверный ID книги");
            return null;
        }
        Book book = repositoryBook.getBookById(id);
        if (book == null) {
            System.out.println("Книга с ID " + id + " не найдена");
            return null;
        }
        if (!book.isBusy()) {
            System.out.println("Книга с ID " + id + " находится в библиотеке, а не у читателя");
        }

        // Проходимся по списку всех пользователей, чтобы выяснить, у кого эта книга
        for (User user : repositoryUser.getAllUsers()) {
            if (user.getUserBooks().contains(book)) {
                return user;
            }
        }
        System.out.println("Книга с ID " + id + " не найдена в списке взятых читателями");
        return null;
    }
}

