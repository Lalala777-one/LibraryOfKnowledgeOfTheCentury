package service;

import model.Book;
import model.Role;
import model.User;
import repository.BookRepository;
import repository.UserRepository;
import utils.MyArrayList;
import utils.MyList;

import java.util.*;

public class MainServiceImpl implements MainService {

    private final BookRepository repositoryBook;
    private final UserRepository repositoryUser;

    private final Set<String> adminEmails = Set.of("admin1@example.com");
    //private final Set<String> adminPasswords = Set.of("adminPass1", "adminPass2", "devPass1", "devPass2");

    private final Set<String> adminPasswords = Set.of("e5&Jm0Hs4");

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
        if (activeUser == null || activeUser.getRole() != Role.ADMIN) {
            System.out.println("Добавление новой книги доступно только Администраторам.");
            return;
        }
        if (!title.matches("^[\\p{L}0-9\\s`\\-.,!?()&]+$")) {
            System.out.println("Название книги может содержать только буквы, цифры, пробелы и допустимые специальные символы: '-.,!?");
            return;
        }
        if (!author.matches("^[\\p{L}\\s`\\-.]+$")) {
            System.out.println("Имя автора может содержать только буквы, пробелы и допустимые специальные символы: '-");
            return;
        }
        if (!genre.matches("^[\\p{L}\\s]+$")) {
            System.out.println("Жанр может содержать только буквы и пробелы.");
            return;
        }

        try {
            repositoryBook.addBook(title, author, genre);
            //System.out.println("Книга успешно добавлена: " + title);
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении книги: " + e);
        }
    }

    @Override
    public MyList<Book> getAllBooks() {
        MyList<Book> books = repositoryBook.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("Список книг пуст.");
        }else {
            System.out.println("Общее количество книг: " + books.size());
        }
        return books;
    }

    @Override
    public MyList<Book> getAllFreeBooks() {
        MyList<Book> freeBooks = repositoryBook.getAllFreeBooks();
        if (freeBooks.isEmpty()) {
            System.out.println("В данный момент нет доступных книг.");
        }
        return freeBooks;
    }

    @Override
    public MyList<Book> getAllBusyBooks() {
        MyList<Book> busyBooks = repositoryBook.getAllBusyBooks();
        if (busyBooks.isEmpty()) {
            System.out.println();
        }
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

        MyList<Book> booksByAuthor =  new MyArrayList<>();
        MyList<Book> allBooks = repositoryBook.getAllBooks(); // get all books

        for (Book book : allBooks) { // check all matches by author
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                booksByAuthor.add(book);
            }
        }
        if (!booksByAuthor.isEmpty()) {
            System.out.println("Найдено книг автора: " + booksByAuthor.size());
        }
        return booksByAuthor;
    }

    @Override
    public MyList<Book> getBookByTitle(String title) {
        if (title == null || title.isEmpty()) {
            System.out.println("Название книги не может быть пустым.");
            return null;
        }

        MyList<Book> booksByTitle = new MyArrayList<>();
        MyList<Book> allBooks = repositoryBook.getAllBooks();

        for (Book book : allBooks) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                booksByTitle.add(book);
            }
        }

        if (!booksByTitle.isEmpty()) {
            System.out.println("Найдено книг с подобным названием: " + booksByTitle.size());
        }
        return booksByTitle;
    }


    @Override
    public MyList<Book> getBookByGenre(String genre) {
        if (genre == null || genre.isEmpty()) {
            System.out.println("Жанр не может быть пустым.");
            return null;
        }

        MyList<Book> booksByGenre =  new MyArrayList<>();
        MyList<Book> allBooks = repositoryBook.getAllBooks();

        for (Book book : allBooks) {
            if (book.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                booksByGenre.add(book);
            }
        }

        if (booksByGenre.isEmpty()) {
            System.out.println("Найдено книг с подобным жанром: " + booksByGenre.size());
        }
        return booksByGenre;
    }

    @Override
    public void deleteBook(Book book) {
        if (book == null) {
            System.out.println("Книга не может быть null");
            return;
        }

        // Только админ. Значит должен быть пользователь с ролью ADMIN, а не какой-то другой
        if (activeUser == null || activeUser.getRole() != Role.ADMIN) {
            System.out.println("Доступно только Администраторам");
            return;
        }
        Book existingBook = repositoryBook.getBookById(book.getId());
        if (existingBook == null) {
            System.out.println("Книга за таким Id не найдена");
            return;
        }

        try {
            repositoryBook.deleteBook(existingBook);
            //System.out.println("Книга удалена: " + existingBook.getTitle());
        } catch (Exception e) {
            System.out.println("Ошибка при удалении книги: " + e);
        }

    }

    // sort

    @Override
    public MyList<Book> sortByAuthor() {
        MyList<Book> allBooks = repositoryBook.getAllBooks();
        MyList<Book> booksByAuthor = new MyArrayList<>();

        // Перетворюємо MyList<Book> на масив об'єктів
        Object[] objectArray = allBooks.toArray();

        // Приводимо об'єкти до типу Book і створюємо масив Book[]
        Book[] bookArray = Arrays.copyOf(objectArray, objectArray.length, Book[].class);

        // Сортуємо масив за автором
        Arrays.sort(bookArray, Comparator.comparing(Book::getAuthor));

        // Додаємо відсортовані книги в booksByAuthor
        for (Book book : bookArray) {
            booksByAuthor.add(book);
        }

        return booksByAuthor;
    }


    @Override
    public  MyList<Book> sortByTitle() {
        MyList<Book> allBooks = repositoryBook.getAllBooks();
        MyList<Book> booksByTitle = new MyArrayList<>();

        Object[] objectArray = allBooks.toArray();

        Book[] bookArray = Arrays.copyOf(objectArray, objectArray.length, Book[].class);

        Arrays.sort(bookArray, Comparator.comparing(Book::getTitle));

        for (Book book : bookArray) {
            booksByTitle.add(book);
        }

        return booksByTitle;
    }

    @Override
    public MyList<Book> sortByGenre() {
        MyList<Book> allBooks = repositoryBook.getAllBooks();
        MyList<Book> booksByGenre = new MyArrayList<>();

        Object[] objectArray = allBooks.toArray();

        Book[] bookArray = Arrays.copyOf(objectArray, objectArray.length, Book[].class);

        Arrays.sort(bookArray, Comparator.comparing(Book::getGenre));

        for (Book book : bookArray) {
            booksByGenre.add(book);
        }

        return booksByGenre;
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
        if (!activeUser.getUserBooks().contains(book)) {
            System.out.println("Книга не находится у активного пользователя");
            return false;
        }

        book.setBusy(false);
        activeUser.getUserBooks().remove(book);
        System.out.println("Книга успешно возвращена в библиотеку");
        return true;
    }

    // А каким образом сервис получит целого пользователя?
    // Наверное админ в меню сможет выбрать из списка пользователя email? А тут уже искать пользователя по email?
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
            MyList<Book> usersBooks = user.getUserBooks();
            //  MyList<Book> usersBooks = repositoryUser.getAllUsersBook(user);
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

    public boolean isAdmin(String email) {
        return adminEmails.contains(email);
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
        //System.out.println("Добро пожаловать, пользователь!");

        return true;
    }

    public boolean loginAdmin(String email, String password) {
        // Перевірка наявності email
        if (!isAdmin(email)) {
            //System.out.println("Доступ запрещен. Вы не являетесь администратором.");
            return false;
        }

        // Перевірка пароля
        int adminIndex = adminEmails.stream().toList().indexOf(email);
        if (adminIndex == -1 || !adminPasswords.contains(password) || !adminPasswords.toArray()[adminIndex].equals(password)) {
            System.out.println("Неверный пароль для администратора.");
            return false;
        }

        this.activeUser = new User(email, password);
        this.activeUser.setRole(Role.ADMIN);
        //System.out.println("Добро пожаловать, администратор!");
        return true;
    }

    @Override
    public boolean logOutUser() {
        if (activeUser == null) {
            System.out.println("Вы не авторизированны");
            return false;
        } else {
            activeUser = null;
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
        }
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
        //System.out.println("Книга с ID " + id + " не найдена в списке взятых читателями");
        return null;
    }
}

