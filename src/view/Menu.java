package view;

import model.Book;
import model.User;
import service.MainService;
import utils.MyList;

import java.util.Scanner;

public class Menu {
    private final MainService service;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(MainService service) {
        this.service = service;
    }

    private void waitRead() {
        System.out.println("\nДля продожения нажмите enter");
        scanner.nextLine();
    }

    private int scanCorrectIntFromUser(int maxInputInt) {

        int number;

        while (true) {
            System.out.print("Введите число от 0 до " + maxInputInt + ": ");
            try {
                number = scanner.nextInt();
                scanner.nextLine();

                if (number >= 0 && number <= maxInputInt) {
                    return number;
                } else {
                    System.out.println("Ошибка: число должно быть от 0 до " + maxInputInt + ".");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: введите целое число.");
                scanner.next();
            }
        }
    }

    public void run() {
        showMenu();
    }

    private void showMenu() {
        while (true) {
            System.out.println("Добро пожаловать в меню");
            System.out.println("1. Меню книг");
            System.out.println("2. Меню пользователя");
            System.out.println("3. Меню администратора");
            System.out.println("0. Выход из системы");
            System.out.println("\nВведите пункт меню:");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("До свидания!");

                System.exit(0);
            }

            showSubMenu(choice);
        }
    }

    private void showSubMenu(int choice) {
        switch (choice) {
            case 1:
                showBookMenu();

                waitRead();

                break;
            case 2:
                showUserMenu();

                waitRead();

                break;
            case 3:
                showAdminMenu();

                waitRead();

                break;
            default:
                System.out.println("Сделайте корректный выбор\n");
        }
    }

    private void showBookMenu() {
        while (true) {
            System.out.println("Список книг");
            System.out.println("1. Список всех книг библиотеки");
            System.out.println("2. Список всех свободных книг");
            System.out.println("3. Список всех занятых книг");
            System.out.println("4. Найти книгу по ID");
            System.out.println("5. Найти книгу по автору");
            System.out.println("6. Найти книгу по названию");
            System.out.println("7. Найти книгу по жанру");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\nСделайте выбор пункта меню");
            int input = scanCorrectIntFromUser(7);

            if (input == 0) break;

            handleBookMenuChoice(input);
        }
    }

    private void handleBookMenuChoice(int input) {
        switch (input) {
            case 1:
                showAllBooksListMenu();
                waitRead();
                break;
            case 2:
                showFreeBooksListMenu();
                waitRead();
                break;
            case 3:
                showBusyBooksListMenu();
                waitRead();
                break;
            case 4:
                findBookById();
                waitRead();
                break;
            case 5:
                findBookByAuthor();
                waitRead();
                break;
            case 6:
                findBookByTitle();
                waitRead();
                break;
            case 7:
                findBookByGenre();
                waitRead();
                break;
            default:
                System.out.println("\nНеверный ввод");
        }

    }

    private void showAllBooksListMenu() {
        while (true) {
            System.out.println("Список всех книг библиотеки");

            MyList<Book> allBooks = service.getAllBooks();

            for (int index = 0; index < allBooks.size(); index++) {
                int bookNumber = (index + 1);
                Book book = allBooks.get(index);
                System.out.println(book.toString());
            }

            System.out.println("0. Вернуться в предыдущее меню");

            int allBookInput = scanCorrectIntFromUser(allBooks.size());

            if (allBookInput == 0) break;

            Book chosenBook = allBooks.get(allBookInput - 1);

            showFreeBookMenu(chosenBook);
        }
    }

    private void showFreeBooksListMenu() {
        while (true) {
            System.out.println("Список свободных книг:");

            MyList<Book> freeBooks = service.getAllFreeBooks();

            for (int index = 0; index < freeBooks.size(); index++) {
                int bookNumber = (index + 1);
                Book book = freeBooks.get(index);
                System.out.println(book.toString());
            }

            System.out.println("0. Вернуться в предыдущее меню");

            int allBookInput = scanCorrectIntFromUser(freeBooks.size());

            if (allBookInput == 0) break;

            Book chosenBook = freeBooks.get(allBookInput - 1);

            showFreeBookMenu(chosenBook);
        }
    }

    private void showFreeBookMenu(Book book) {
        System.out.println("Список свободных книг:");
        while (true) {
            System.out.println("Книга: \"" + book.getTitle() + "\", " + book.getAuthor());
            System.out.println("1. Взять");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\n Сделайте выбор пункта меню");
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) break;

            service.takeBook(book.getId());
            System.out.println("Книга \"" + book.getTitle() + "\", " + book.getAuthor() + " успешно взята.");

        }
    }

    private void returnBookMenu(Book book) {
        System.out.println("Вернуть книгу:");
        while (true) {
            System.out.println("Книга: \"" + book.getTitle() + "\", " + book.getAuthor());
            System.out.println("1. Вернуть");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\n Сделайте выбор пункта меню");
            int input = scanCorrectIntFromUser(1);

            if (input == 0) break;

            service.returnBook(book.getId());
            System.out.println("Книга \"" + book.getTitle() + "\", " + book.getAuthor() + " успешно возвращена.");
        }
    }

    private void showBusyBooksListMenu() {
        System.out.println("Список занятых книг:");
        while (true) {

            MyList<Book> busyBooks = service.getAllBusyBooks();

            for (int index = 0; index < busyBooks.size(); index++) {
                int bookNumber = (index + 1);
                Book book = busyBooks.get(index);
                System.out.println(bookNumber + ". \"" + book.getTitle() + "\", " + book.getAuthor());
            }

            System.out.println("0. Вернуться в предыдущее меню");

            int busyBookInput = scanCorrectIntFromUser(busyBooks.size());

            if (busyBookInput == 0) break;

            Book chosenBook = busyBooks.get(busyBookInput - 1);

            returnBookMenu(chosenBook);
        }
    }

    private void findBookById() {
        System.out.println("Введите ID книги:");
        int inputedId = scanner.nextInt();
        scanner.nextLine();

        if (inputedId < 0) {
            System.out.println("ID книги должен быть больше 0");
            return;
        }

        Book book = service.getBookById(inputedId);

        if (book == null) {
            System.out.println("Книга с таким ID не найдена");
        } else {
            System.out.println("Книга с ID " + inputedId + ":");
            System.out.println(book.toString());
        }

        //проверяем, не занята ли книга
        if (book.isBusy()) {
            System.out.println("Эта книга находится у другого читателя");
        } else {
            System.out.println("0. Вернуться в предыдущее меню");
            System.out.println("1. Взять книгу");
        }
        int choice = scanCorrectIntFromUser(1);
        if (choice == 1) {
            boolean successTakeBook = service.takeBook(inputedId);
        } else {
            System.out.println("Не удалось взять книгу");
        }
    }

    private void findBookByAuthor() {
        System.out.println("Введите автора книги: ");
        String inputedAuthor = scanner.nextLine();
        if (inputedAuthor == null) {
            System.out.println("Автор не введен");
            return;
        }
        if (inputedAuthor.isEmpty()) {
            System.out.println("Строка не должна быть пустой");
            return;
        }

        MyList<Book> books = service.getBookByAuthor(inputedAuthor);

        if (books == null || books.isEmpty()) {
            System.out.println("Книги данного автора не найдены");
        } else {
            System.out.println("Книги автора " + inputedAuthor + ":");
            for (Book book : books) {
                System.out.println(book.toString());
            }
        }

        //проверяем, не занята ли книга
        System.out.println();
        System.out.println("Список свободных книг этого автора");
        for (Book book : books) {
            if (book.isBusy()) {
                System.out.println("Книга (занята): id: " + book.getId() + "; название: " + book.getTitle() + "; жанр: " + book.getGenre());
            } else {
                System.out.println("Книга (свободна): id: " + book.getId() + "; название: " + book.getTitle() + "; жанр: " + book.getGenre());
            }
        }

        System.out.println("0. Вернуться в предыдущее меню");
        System.out.println("1. Взять книгу");
        int choice = scanCorrectIntFromUser(1);
        if (choice == 1) {
            System.out.println("Введите id книги: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            boolean successTakeBook = service.takeBook(bookId);
        } else {
            System.out.println("Не удалось взять книгу");
        }
    }

    private void findBookByTitle() {
        System.out.println("Введите название книги: ");
        String inputTitle = scanner.nextLine();
        if (inputTitle == null) {
            System.out.println("Ошибка: название книги не указано");
            return;
        }
        if (inputTitle.isEmpty()) {
            System.out.println("Строка не должна быть пустой");
            return;
        }

        MyList<Book> books = service.getBookByTitle(inputTitle);

        if (books == null || books.isEmpty()) {
            System.out.println("Книги с таким названием не найдены");
        } else {
            System.out.println("Книги с названием " + inputTitle + ":");
            for (Book book : books) {
                System.out.println(book.toString());
            }
        }

        //проверяем, не занята ли книга
        System.out.println();
        System.out.println("Список свободных книг этого автора");
        for (Book book : books) {
            if (book.isBusy()) {
                System.out.println("Книга (занята): id: " + book.getId() + "; название: " + book.getTitle() + "; жанр: " + book.getGenre());
            } else {
                System.out.println("Книга (свободна): id: " + book.getId() + "; название: " + book.getTitle() + "; жанр: " + book.getGenre());
            }
        }

        System.out.println("0. Вернуться в предыдущее меню");
        System.out.println("1. Взять книгу");
        int choice = scanCorrectIntFromUser(1);
        if (choice == 1) {
            System.out.println("Введите id книги:");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            boolean successTakeBook = service.takeBook(bookId);
        } else {
            System.out.println("Не удалось взять книгу");
        }
    }

    private void findBookByGenre() {
        System.out.println("Введите жанр:");
        String inputGenre = scanner.nextLine();

        if (inputGenre == null) {
            System.out.println("Жанр не введён");
            return;
        }
        if (inputGenre.isEmpty()) {
            System.out.println("Строка не должна быть пустой");
            return;
        }

        MyList<Book> books = service.getBookByGenre(inputGenre);

        if (books == null || books.isEmpty()) {
            System.out.println("Книги данного жанра не найдены!");
            return;
        } else {
            System.out.println("Список найденных книг жанра - " + inputGenre + ":");
            for (Book book : books) {
                System.out.println(book.toString());
            }
        }

        //проверяем, не заняты ли книги
        System.out.println();
        System.out.println("Список свободных книг этого жанра:");
        for (Book book : books) {
            if(book.isBusy()) {
                System.out.println("Книга (занята): id: " + book.getId() + "; название: " + book.getTitle() + "; жанр: " + book.getGenre());
            } else {
                System.out.println("Книга (свободна): id: " + book.getId() + "; название: " + book.getTitle() + "; жанр: " + book.getGenre());
            }
        }

        System.out.println("0. Вернуться в предыдущее меню");
        System.out.println("1. Взять книгу");
        int choice = scanCorrectIntFromUser(1);
        if (choice == 1) {
            System.out.println("Введите id книги: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            boolean successTakeBook = service.takeBook(bookId);
        } else {
            System.out.println("Не удалось взять книгу");
        }


    }

    private void showUserMenu() {
        while (true) {
            System.out.println("Меню пользователя");
            System.out.println("1. Вход в систему");
            System.out.println("2. Регистрация нового пользователя");
            System.out.println("3. Logout");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\nСделайте выбор пункта меню");
            int input = scanCorrectIntFromUser(3);

            if (input == 0) break;

            handleUserMenuChoice(input);
        }
    }

    private void handleUserMenuChoice(int input) {
        switch (input) {
            case 1:
                // Подтягиваем авторизацию
                System.out.println("Введите ваш email:");
                String email = scanner.nextLine();

                System.out.println("Введите ваш пароль:");
                String password = scanner.nextLine();

                boolean isLoggedIn = service.loginUser(email, password);

                if (isLoggedIn) {
                    System.out.println("Добро пожаловать в ЗНАНИЯ ВЕКА!");
                    //TODO добавить выход в меню книг
                    showBookMenu();
                } else {
                    System.out.println("Неверный email или пароль. Попробуйте снова");
                }
                waitRead();
                break;
            case 2:
                System.out.println("Регистрация нового пользователя");
                System.out.println("Введите email:");
                String email1 = scanner.nextLine();

                System.out.println("Введите пароль");
                String password1 = scanner.nextLine();

                User user = service.registerUser(email1, password1);

                if (user != null) {
                    System.out.println("Вы успешно зарегистрировались в системе");
                    System.out.println("Предлагаем вам перейти к выбору книги)");
                    showBookMenu();
                } else {
                    System.out.println("Регистрация провалена!");
                }
                waitRead();
                break;
            case 3:
                service.logOutUser();
                System.out.println("Вы вышли из системы");
                waitRead();
                break;
            default:
                System.out.println("\nНеверный ввод");
        }

    }


    private void showAdminMenu() {
        while (true) {
            System.out.println("Меню админа");
            System.out.println("1. Добавить книгу");
            System.out.println("2. Удалить книгу");
            System.out.println("3. Найти, у кого книга сейчас");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\n Сделайте выбор пункта меню");
            int input = scanCorrectIntFromUser(3);

            if (input == 0) break;

            handleAdminMenuChoice(input);
        }
    }

    private void handleAdminMenuChoice(int input) {
        switch (input) {
            case 1:
                addBook();

                break;
            case 2:
                deleteBook();

                break;
            case 3:
                whoHasBook();

                break;
            default:
                System.out.println("\nНеверный ввод");
        }
    }

    private void addBook() {

        System.out.println("Добавить новую книгу");

        System.out.println("Введите название: ");
        String title = scanner.nextLine();
//        scanner.nextLine();

        System.out.println("Введите автора: ");
        String author = scanner.nextLine();
//        scanner.nextLine();

        System.out.println("Введите жанр: ");
        String genre = scanner.nextLine();
//        scanner.nextLine();

        service.addBook(title, author, genre);
        System.out.println("Книга \"" + title + "\", " + author + "\", " + genre
                + "\", " + " успешно добавлена.");

    }

    private void deleteBook() {
        System.out.println("Удалить книгу");

        while (true) {
            System.out.println("Введите ID книги, которую надо удалить: ");

            int bookID = scanner.nextInt();
            scanner.nextLine();

            Book book = service.getBookById(bookID);

            System.out.println("Книга: \"" + book.getTitle() + "\", " + book.getAuthor() + "\", " + book.getGenre());
            System.out.println("1. Удалить");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\n Сделайте выбор пункта меню");
            int input = scanCorrectIntFromUser(1);

            if (input == 0) break;

            service.deleteBook(book);

            System.out.println("Книга \"" + book.getTitle() + "\", " + book.getAuthor() + "\", " + book.getGenre()
                    + "\", " + " успешно удалена.");
        }

    }

    private void whoHasBook() {

        System.out.println("У какого читателя находится книга: ");
        System.out.println("Введите ID книги: ");

        int bookID = scanner.nextInt();
        scanner.nextLine();

        Book book = service.getBookById(bookID);
        User user = service.whoHasBook(bookID);

        System.out.println("Книга \"" + book.getTitle() + "\", " + book.getAuthor() + ", " + book.getGenre()
                + " находится у читателя c email " + user.getEmail());

    }

}
