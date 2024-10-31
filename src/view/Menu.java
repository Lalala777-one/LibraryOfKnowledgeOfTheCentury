package view;

import model.Book;
import model.User;
import service.MainService;
import utils.MyList;

import java.awt.*;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    private final MainService service;
    private final Scanner scanner = new Scanner(System.in);

    private final static String COLOR_BLACK = "\u001B[0m";

    public static final String COLOR_RESET = "\u001B[0m";
    public static final String COLOR_RED = "\u001B[31m";
    public static final String COLOR_GREEN = "\u001B[32m";
    public static final String COLOR_YELLOW = "\u001B[33m";
    public static final String COLOR_BLUE = "\u001B[34m";
    public static final String COLOR_PURPLE = "\u001B[35m";
    public static final String COLOR_CYAN = "\u001B[36m";

    public static final String COLOR_WHITE = "\u001B[37m";

    public Menu(MainService service) {
        this.service = service;
    }

    private void waitRead(){
        System.out.println("\n Для продожения нажмите enter");
        scanner.nextLine();
    }

    public void run() {showMenu();}

    private void showMenu() {
        while (true){
            System.out.println(Color.BLUE + "Добро пожаловать в меню" + COLOR_RESET);
            System.out.println("1. Меню книг");
            System.out.println("2. Меню пользователей");
            System.out.println("3. Меню администратора");
            System.out.println("0. Выход из системы");
            System.out.println(Color.YELLOW + "\nВведите пункт меню:" + COLOR_RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0){
                System.out.println("До свидания!");

                System.exit(0);
            }

            showSubMenu(choice);
        }
    }

    private void showSubMenu(int choice){
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
                System.out.println(Color.YELLOW + "Сделайте корректный выбор\n" + COLOR_RESET);
        }
    }
    //todo
    private void showBookMenu(){
        while (true){
            System.out.println("Список книг");
            System.out.println("1. Список всех книг");
            System.out.println("2. Список всех свободных книг");
            System.out.println("3. Список всех занятых книг");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\n Сделайте выбор пункта меню");
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) break;

            //проверка на неверные цифры

            handleBookMenuChoice(input);
        }
    }

    private void handleBookMenuChoice(int input){
        switch (input) {
            case 1:
                System.out.println("Метод в разработке. Приходите завтра");

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
                //поиск по автору, id, титель

            case 5:
                returnBookMenu();

                waitRead();

                break;
            default:
                System.out.println("\nНеверный ввод");
        }
    }

    private void showFreeBooksListMenu(){
        while (true){
            System.out.println("Список свободных книг:");

            MyList<Book> freeBooks = service.getAllFreeBooks();
            for (int index = 0; index < freeBooks.size(); index++) {
                int bookNumber = (index + 1);
                Book book = freeBooks.get(index);
                System.out.println(bookNumber + ". \"" + book.getTitle() + "\", " + book.getAuthor());
            }

            System.out.println("0. Вернуться в предыдущее меню");

            int freeBookInput = scanner.nextInt();
            scanner.nextLine();

            if (freeBookInput == 0) break;

            Book chosenBook = freeBooks.get(freeBookInput - 1);

            if (Objects.isNull(chosenBook)) {
                System.out.println("\nНеверный ввод");
            }

            showFreeBookMenu(chosenBook);
        }
    }


    private void showFreeBookMenu(Book book){
        System.out.println("Список свободных книг:");
        while (true){
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

    private void returnBookMenu (Book book){
        System.out.println("Вернуть книгу:");
        while (true){
            System.out.println("Книга: \"" + book.getTitle() + "\", " + book.getAuthor());
            System.out.println("1. Вернуть");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\n Сделайте выбор пункта меню");
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) break;

            service.returnBook(book.getId());
            System.out.println("Книга \"" + book.getTitle() + "\", " + book.getAuthor() + " успешно возвращена.");
        }
    }


    private void showBusyBooksListMenu(){
        while (true){
            System.out.println("Список занятых книг:");

            MyList<Book> busyBooks = service.getAllBusyBooks();
            for (int index = 0; index < busyBooks.size(); index++) {
                int bookNumber = (index + 1);
                Book book = busyBooks.get(index);
                System.out.println(bookNumber + ". \"" + book.getTitle() + "\", " + book.getAuthor());
            }

            System.out.println("0. Вернуться в предыдущее меню");

            int busyBookInput = scanner.nextInt();
            scanner.nextLine();

            if (busyBookInput == 0) break;

            Book chosenBook = busyBooks.get(busyBookInput - 1);

            if (Objects.isNull(chosenBook)) {
                System.out.println("\nНеверный ввод");
            }
        }
    }

    private void showUserMenu(){
        while (true){
            System.out.println("Меню пользователя");
            System.out.println("1. Вход в систему");
            System.out.println("2. Регистрация нового пользователя");
            System.out.println("3. Logout");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\n Сделайте выбор пункта меню");
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) break;
            //проверка на другие цифры

            handleUserMenuChoice(input);
        }
    }

    private void handleUserMenuChoice(int input){
        switch (input) {
            case 1:
                //Todo написать авторизацию
                System.out.println("Метод в разработке. Приходите завтра");

                waitRead();

                break;
            case 2:
                System.out.println("Регистрация нового пользователя");
                System.out.println("Введите email:");
                String email = scanner.nextLine();

                System.out.println("Введите пароль");
                String password = scanner.nextLine();

                User user = service.registerUser(email, password);

                if (user != null) {
                    System.out.println("Вы успешно зарегистрировались в системе");
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


    private void showAdminMenu(){
        while (true){
            System.out.println("Меню админа");
            System.out.println("1. Вход в систему");
            System.out.println("2. Изменение книг");
            System.out.println("3. Logout");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\n Сделайте выбор пункта меню");
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) break;
            //проверка на другие цифры

            handleAdminMenuChoice(input);
        }
    }
    private void handleAdminMenuChoice(int input){
        switch (input) {
            case 1:
                System.out.println("Метод в разработке. Приходите завтра");

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
                addBook(String title, String author, String genre);

                waitRead();

                break;
            case 5:
                updateBook();

                waitRead();

                break;
            case 6:
                deleteBook(Book book);

                waitRead();

                break;
            case 7:
                whoHasBook();

                waitRead();

                break;
            default:
                System.out.println("\nНеверный ввод");
        }
    }
    private void addBook(String title, String author, String genre) {

        System.out.println("Добавить новую книгу");
        //  что здесь должно быть?

    }

    private void updateBook(){

        System.out.println("Редактировать книгу");
        //  что здесь должно быть?
    }

    private void deleteBook(Book book){
        System.out.println("Удалить книгу");

        while (true){
            System.out.println("Книга: \"" + book.getTitle() + "\", " + book.getAuthor());
            System.out.println("1. Удалить");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\n Сделайте выбор пункта меню");
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) break;

            int deleteBookPerID = service.deleteBook(Book book);

            service.deleteBookPerID(book.getId());
            System.out.println("Книга \"" + book.getTitle() + "\", " + book.getAuthor() + " успешно удалена.");
        }

    }
    private void whoHasBook(){

        System.out.println("У какого читателя находится книга?");
        //  что здесь должно быть?
    }

}
