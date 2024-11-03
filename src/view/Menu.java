package view;

import model.Book;
import model.User;
import service.MainService;
import utils.MyList;

import java.util.Random;
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
                scanner.nextLine();
            }
        }
    }

    public void run() {
        showMenu();
    }

    private void showMenu() {
        while (true) {
            System.out.println(Color.GREEN + "Добро пожаловать в меню библиотеки" + Color.RESET);
            System.out.println("1. Меню книг");
            System.out.println("2. Меню пользователя");
            System.out.println("3. Меню администратора");
            System.out.println("0. Выход из системы");
            System.out.println(Color.YELLOW + "\nВведите пункт меню:" + Color.RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println(Color.CYAN + "До свидания!" + Color.RESET);
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
                System.out.println(Color.YELLOW + "Сделайте корректный выбор\n" + Color.RESET);
        }
    }

    private void showBookMenu() {
        while (true) {
            System.out.println(Color.CYAN + "Посмотрите наши книги!" + Color.RESET);
            System.out.println("1. Список всех книг библиотеки");
            System.out.println("2. Список всех свободных книг");
            System.out.println("3. Список всех занятых книг");
            System.out.println("4. Найти книгу по ID");
            System.out.println("5. Найти книгу по автору");
            System.out.println("6. Найти книгу по названию");
            System.out.println("7. Найти книгу по жанру");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println(Color.GREEN + "\nСделайте выбор пункта меню" + Color.RESET);
            int input = scanCorrectIntFromUser(7);
            scanner.nextLine();
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
                System.out.println(Color.RED + "\nНеверный ввод" + Color.RESET);
        }
    }

    private void showAllBooksListMenu() {
        while (true) {
            System.out.println(Color.CYAN + "Вот список всех книг нашей библиотеки:" + Color.RESET);

            MyList<Book> allBooks = service.getAllBooks();

            for (int index = 0; index < allBooks.size(); index++) {
                int bookNumber = (index + 1);
                Book book = allBooks.get(index);
                System.out.println(book.toString());
            }

            System.out.println(Color.BLUE + "0. Вернуться в предыдущее меню" + Color.RESET);

            int allBookInput = scanCorrectIntFromUser(allBooks.size());

            if (allBookInput == 0) break;

            Book chosenBook = allBooks.get(allBookInput - 1);

            showFreeBookMenu(chosenBook);
        }
    }

    private void showFreeBooksListMenu() {
        while (true) {
            System.out.println(Color.CYAN + "Вот список свободных книг нашей библиотеки" + Color.RESET);

            MyList<Book> freeBooks = service.getAllFreeBooks();

            for (int index = 0; index < freeBooks.size(); index++) {
                int bookNumber = (index + 1);
                Book book = freeBooks.get(index);
                System.out.println(book.toString());
            }

            System.out.println(Color.BLUE + "0. Вернуться в предыдущее меню" + Color.RESET);
            int allBookInput = scanCorrectIntFromUser(freeBooks.size());
            if (allBookInput == 0) break;
            Book chosenBook = freeBooks.get(allBookInput - 1);
            showFreeBookMenu(chosenBook);
        }
    }

    private void showFreeBookMenu(Book book) {
        System.out.println(Color.CYAN + "Список свободных книг:" + Color.RESET);
        while (true) {
            System.out.println(book.toString());
            System.out.println(Color.BLUE + "1. Взять" + Color.RESET);
            System.out.println(Color.BLUE + "0. Вернуться в предыдущее меню" + Color.RESET);

            System.out.println(Color.GREEN + "\nСделайте выбор пункта меню" + Color.RESET);
            int input = scanner.nextInt();
            scanner.nextLine();
            if (input == 0) break;
            service.takeBook(book.getId());
        }
    }

    private void returnBookMenu(Book book) {
        System.out.println(Color.BLUE + "Вернуть книгу:" + Color.RESET);
        while (true) {
            System.out.println("Книга: \"" + book.getTitle() + "\", " + book.getAuthor());
            System.out.println(Color.BLUE + "1. Вернуть" + Color.RESET);
            System.out.println(Color.BLUE + "0. Вернуться в предыдущее меню" + Color.RESET);

            System.out.println(Color.GREEN + "\n Сделайте выбор пункта меню" + Color.RESET);
            int input = scanCorrectIntFromUser(1);

            if (input == 0) break;

            service.returnBook(book.getId());
            System.out.println(Color.PURPLE + "Книга \"" + book.getTitle() + "\", " + book.getAuthor() + " успешно возвращена." + Color.RESET);
        }
    }

    private void showBusyBooksListMenu() {
        System.out.println(Color.BLUE + "Вот список книг, находящихся у насших читателей" + Color.RESET);
        MyList<Book> busyBooks = service.getAllBusyBooks();

        if (busyBooks == null || busyBooks.isEmpty()) {
            System.out.println(Color.BLUE + "Не найдено занятых книг" + Color.RESET);
            return;
        } else {
            System.out.println(Color.BLUE + "Найдено " + busyBooks.size() + "занятых книг:" + Color.RESET);
            for (Book book : busyBooks) {
                System.out.println(book.toString());
            }
        }

        while (true) {
            System.out.println(Color.BLUE + "0. Вернуться в предыдущее меню" + Color.RESET);
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
        System.out.println(Color.PURPLE + "Введите автора книги: " + Color.RESET);
        String inputedAuthor = scanner.nextLine();
        if (inputedAuthor == null) {
            System.out.println(Color.RED + "Автор не введен" + Color.RESET);
            return;
        }
        if (inputedAuthor.isEmpty()) {
            System.out.println(Color.RED + "Строка не должна быть пустой" + Color.RESET);
            return;
        }

        MyList<Book> books = service.getBookByAuthor(inputedAuthor);

        if (books == null || books.isEmpty()) {
            System.out.println(Color.RED + "Книги данного автора не найдены" + Color.RESET);
        } else {
            System.out.println(Color.CYAN + "Книги автора " + inputedAuthor + ":" + Color.RESET);
            for (Book book : books) {
                System.out.println(book.toString());
            }
        }

        //проверяем, не занята ли книга
        System.out.println();
        System.out.println(Color.CYAN + "Вот список свободных книг этого автора" + Color.RESET);
        for (Book book : books) {
            if (book.isBusy()) {
                System.out.println("Книга (занята): id: " + book.getId() + "; название: " + book.getTitle() + "; жанр: " + book.getGenre());
            } else {
                System.out.println("Книга (свободна): id: " + book.getId() + "; название: " + book.getTitle() + "; жанр: " + book.getGenre());
            }
        }

        System.out.println(Color.BLUE + "0. Вернуться в предыдущее меню" + Color.RESET);
        System.out.println(Color.BLUE + "1. Взять книгу" + Color.RESET);
        int choice = scanCorrectIntFromUser(1);
        if (choice == 1) {
            System.out.println(Color.YELLOW + "Введите id книги: " + Color.RESET);
            int bookId = scanner.nextInt();
            scanner.nextLine();
            boolean successTakeBook = service.takeBook(bookId);
        } else {
            System.out.println(Color.RED + "Не удалось взять книгу" + Color.RESET);
        }
    }

    private void findBookByTitle() {
        System.out.println(Color.YELLOW + "Введите название книги: " + Color.RESET);
        String inputTitle = scanner.nextLine();
        if (inputTitle == null) {
            System.out.println(Color.RED + "Ошибка: название книги не указано" + Color.RESET);
            return;
        }
        if (inputTitle.isEmpty()) {
            System.out.println(Color.RED + "Строка не должна быть пустой" + Color.RESET);
            return;
        }

        MyList<Book> books = service.getBookByTitle(inputTitle);

        if (books == null || books.isEmpty()) {
            System.out.println(Color.RED + "К сожалению книги с таким названием не найдены" + Color.RESET);
        } else {
            System.out.println(Color.CYAN + "Книги с названием " + inputTitle + ":" + Color.RESET);
            for (Book book : books) {
                System.out.println(book.toString());
            }
        }

        //проверяем, не занята ли книга
        System.out.println();
        System.out.println(Color.CYAN + "Вот список свободных и занятых книг этого автора" + Color.RESET);
        for (Book book : books) {
            if (book.isBusy()) {
                System.out.println("Книга (занята): id: " + book.getId() + "; название: " + book.getTitle() + "; жанр: " + book.getGenre());
            } else {
                System.out.println("Книга (свободна): id: " + book.getId() + "; название: " + book.getTitle() + "; жанр: " + book.getGenre());
            }
        }

        System.out.println(Color.BLUE + "0. Вернуться в предыдущее меню" + Color.RESET);
        System.out.println(Color.BLUE + "1. Взять книгу" + Color.RESET);
        int choice = scanCorrectIntFromUser(1);
        if (choice == 1) {
            System.out.println(Color.YELLOW + "Введите id книги:" + Color.RESET);
            int bookId = scanner.nextInt();
            scanner.nextLine();
            boolean successTakeBook = service.takeBook(bookId);
        } else {
            System.out.println(Color.RED + "К сожалению, не удалось взять книгу" + Color.RESET);
        }
    }

    private void findBookByGenre() {
        System.out.println(Color.YELLOW + "Введите жанр:" + Color.RESET);
        String inputGenre = scanner.nextLine();

        if (inputGenre == null) {
            System.out.println(Color.RED + "Жанр не введён" + Color.RESET);
            return;
        }
        if (inputGenre.isEmpty()) {
            System.out.println(Color.RED + "Строка не должна быть пустой" + Color.RESET);
            return;
        }

        MyList<Book> books = service.getBookByGenre(inputGenre);

        if (books == null || books.isEmpty()) {
            System.out.println(Color.RED + "К ожалению книги данного жанра не найдены!" + Color.RESET);
            return;
        } else {
            System.out.println(Color.CYAN + "Список найденных книг жанра - " + inputGenre + ":" + Color.RESET);
            for (Book book : books) {
                System.out.println(book.toString());
            }
        }

        //проверяем, не заняты ли книги
        System.out.println();
        for (Book book : books) {
            if (book.isBusy()) {
                System.out.println("Книга (занята): id: " + book.getId() + "; название: " + book.getTitle() + "; жанр: " + book.getGenre());
            } else {
                System.out.println("Книга (свободна): id: " + book.getId() + "; название: " + book.getTitle() + "; жанр: " + book.getGenre());
            }
        }

        System.out.println(Color.BLUE + "0. Вернуться в предыдущее меню" + Color.RESET);
        System.out.println(Color.BLUE + "1. Взять книгу" + Color.RESET);
        int choice = scanCorrectIntFromUser(1);
        if (choice == 1) {
            System.out.println("Введите id книги: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            boolean successTakeBook = service.takeBook(bookId);
        } else {
            System.out.println(Color.RED + "Не удалось взять книгу" + Color.RESET);
        }
    }

    private void showUserMenu() {
        while (true) {
            System.out.println(Color.GREEN + "Добро пожаловать в меню пользователя" + Color.RESET);
            System.out.println("1. Вход в систему");
            System.out.println("2. Регистрация нового пользователя");
            System.out.println("3. Logout");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println(Color.GREEN + "\nСделайте выбор пункта меню" + Color.RESET);
            int input = scanCorrectIntFromUser(3);
            if (input == 0) break;
            handleUserMenuChoice(input);
        }
    }

    private void handleUserMenuChoice(int input) {
        switch (input) {
            case 1:
                // Подтягиваем авторизацию
                System.out.println(Color.YELLOW + "Введите ваш email:" + Color.RESET);
                String email = scanner.nextLine();

                System.out.println(Color.YELLOW + "Введите ваш пароль:" + Color.RESET);
                String password = scanner.nextLine();

                boolean isLoggedIn = service.loginUser(email, password);

                if (isLoggedIn) {
                    System.out.println(Color.PURPLE + "Добро пожаловать в ЗНАНИЯ ВЕКА!" + Color.RESET);
                    //TODO добавить выход в меню книг
                    showBookMenu();
                } else {
                    System.out.println(Color.RED + "Неверный email или пароль. Попробуйте снова" + Color.RESET);
                }
                waitRead();
                break;
            case 2:
                System.out.println(Color.GREEN + "Давайте зарегистрируемся!" + Color.RESET);
                System.out.println(Color.YELLOW + "Введите email:" + Color.RESET);
                String email1 = scanner.nextLine();

                System.out.println(Color.YELLOW + "Введите пароль" + Color.RESET);
                String password1 = scanner.nextLine();

                User user = service.registerUser(email1, password1);

                if (user != null) {
                    System.out.println(Color.PURPLE + "Вы успешно зарегистрировались в системе" + Color.RESET);
                    System.out.println(Color.YELLOW + "Предлагаем перейти к выбору книги)" + Color.RESET);
                    showBookMenu();
                } else {
                    System.out.println(Color.RED + "Регистрация провалена!" + Color.RESET);
                }
                waitRead();
                break;
            case 3:
                service.logOutUser();
                System.out.println(Color.PURPLE + "Вы вышли из системы. До новых встреч в нашей библиотеке" + Color.RESET);
                waitRead();
                break;
            default:
                System.out.println(Color.RED + "\nНеверный ввод" + Color.RESET);
        }
    }

    private void showAdminMenu() {
        while (true) {
            System.out.println(Color.GREEN + "Добро аожаловать в меню администратора" + Color.RESET);
            System.out.println("1. Добавить книгу");
            System.out.println("2. Удалить книгу");
            System.out.println("3. Найти, у кого книга сейчас");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println(Color.GREEN + "\nСделайте выбор пункта меню" + Color.RESET);
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
                System.out.println(Color.RED + "\nНеверный ввод" + Color.RESET);
        }
    }

    private void addBook() {

        System.out.println(Color.BLUE + "Добавить новую книгу" + Color.RESET);

        System.out.println(Color.YELLOW + "Введите название:" + Color.RESET);
        String title = scanner.nextLine();

        System.out.println(Color.YELLOW + "Введите автора:" + Color.RESET);
        String author = scanner.nextLine();

        System.out.println(Color.YELLOW + "Введите жанр:" + Color.RESET);
        String genre = scanner.nextLine();

        if(service.addBook(title, author, genre)){
            System.out.println(Color.PURPLE + "Книга \"" + title + "\", " + author + "\", " + genre
                    + "\", " + " успешно добавлена!" + Color.RESET);
        }else System.out.println("Добавление книги невозможно");

    }

    private void deleteBook() {
        System.out.println(Color.BLUE + "Удалить книгу" + Color.RESET);

        while (true) {
            System.out.println(Color.YELLOW + "Введите ID книги, которую надо удалить:" + Color.RESET);

            int bookID = scanner.nextInt();
            scanner.nextLine();

            Book book = service.getBookById(bookID);

            System.out.println(book.toString());
            System.out.println(Color.BLUE + "1. Удалить" + Color.RESET);
            System.out.println(Color.BLUE + "0. Вернуться в предыдущее меню" + Color.RESET);

            System.out.println(Color.GREEN + "\n Сделайте выбор пункта меню" + Color.RESET);
            int input = scanCorrectIntFromUser(1);
            if (input == 0) break;
            if (service.deleteBook(book)) {
                System.out.println(Color.PURPLE + "Книга \"" + book.getTitle() + "\", " + book.getAuthor() + "\", " + book.getGenre()
                        + "\", " + " успешно удалена!" + Color.RESET);
                break;
            }
        }
    }

    private void whoHasBook() {

        System.out.println(Color.BLUE + "У какого читателя находится книга: " + Color.RESET);
        System.out.println(Color.YELLOW + "Введите ID книги: " + Color.RESET);

        int bookID = scanner.nextInt();
        scanner.nextLine();

        Book book = service.getBookById(bookID);
        User user = service.whoHasBook(bookID);

        System.out.println(Color.PURPLE + "Книга \"" + book.getTitle() + "\", " + book.getAuthor() + ", " + book.getGenre()
                + " находится у читателя c email " + user.getEmail() + Color.RESET);
    }

}
