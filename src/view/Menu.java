package view;

import model.Book;
import model.User;
import service.MainService;
import utils.MyList;

import java.awt.*;
import java.util.Scanner;

public class Menu {
    private final MainService service;
    private final Scanner scanner = new Scanner(System.in);

    private final static String COLOR_BLACK = "\u001B[0m";

    public static final String COLOR_RESET = "\u001B[0m";
    public static final String COLOR_YELLOW = "\u001B[33m";
    public static final String COLOR_BLUE = "\u001B[34m";
    public static final String COLOR_WHITE = "\u001B[37m";

    public Menu(MainService service) {
        this.service = service;
    }

    private void waitRead(){
        System.out.println("\n Для продожения нажмите enter");
        scanner.nextLine();
    }

    private int scanCorrectIntFromUser(int maxInputInt){

        int number;

        while (true) {
            System.out.print("Введите число от 0 до " + maxInputInt + ": ");
            try {
                number = scanner.nextInt();

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

    public void run() {showMenu();}

    private void showMenu() {
        while (true){
            System.out.println(Color.BLUE + "Добро пожаловать в меню" + COLOR_RESET);
            System.out.println(Color.BLUE + "1. Меню книг" + COLOR_RESET);
            System.out.println(Color.BLUE + "2. Меню пользователей" + COLOR_RESET);
            System.out.println(Color.BLUE + "3. Меню администратора" + COLOR_RESET);
            System.out.println(Color.BLUE + "0. Выход из системы" + COLOR_RESET);
            System.out.println(Color.YELLOW + "\nВведите пункт меню:" + COLOR_RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0){
                System.out.println(Color.YELLOW + "До свидания!" + COLOR_RESET);

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

    private void showBookMenu(){
        while (true){
            System.out.println(Color.BLUE + "Список книг" + COLOR_RESET);
            System.out.println(Color.BLUE + "1. Список всех книг" + COLOR_RESET);
            System.out.println(Color.BLUE + "2. Список всех свободных книг" + COLOR_RESET);
            System.out.println(Color.BLUE + "3. Список всех занятых книг" + COLOR_RESET);
            System.out.println(Color.BLUE + "0. Вернуться в предыдущее меню" + COLOR_RESET);

            System.out.println(Color.YELLOW + "\n Сделайте выбор пункта меню" + COLOR_RESET);
            int input = scanCorrectIntFromUser(3);
            scanner.nextLine();

            if (input == 0) break;

            handleBookMenuChoice(input);
        }
    }

    private void handleBookMenuChoice(int input){
        switch (input) {
            case 1:
                System.out.println("Метод в разработке. Приходите завтра"); //дописать

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

            int freeBookInput = scanCorrectIntFromUser(freeBooks.size());

            if (freeBookInput == 0) break;

            Book chosenBook = freeBooks.get(freeBookInput - 1);

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
            int input = scanCorrectIntFromUser(1);

            if (input == 0) break;

            service.returnBook(book.getId());
            System.out.println("Книга \"" + book.getTitle() + "\", " + book.getAuthor() + " успешно возвращена.");
        }
    }

    private void showBusyBooksListMenu(){
        System.out.println("Список занятых книг:");
        while (true){

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

    private void showUserMenu(){
        while (true){
            System.out.println(Color.BLUE + "Меню пользователя" + COLOR_RESET);
            System.out.println(Color.BLUE + "1. Вход в систему" + COLOR_RESET);
            System.out.println(Color.BLUE + "2. Регистрация нового пользователя" + COLOR_RESET);
            System.out.println(Color.BLUE + "3. Logout" + COLOR_RESET);
            System.out.println(Color.BLUE + "0. Вернуться в предыдущее меню" + COLOR_RESET);

            System.out.println(Color.YELLOW + "\n Сделайте выбор пункта меню" + COLOR_RESET);
            int input = scanCorrectIntFromUser(3);

            if (input == 0) break;

            handleUserMenuChoice(input);
        }
    }

    private void handleUserMenuChoice(int input){
        switch (input) {
            case 1:
                //Todo подтянуть авторизацию
                //service.loginUser(email, password);
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
    private void handleAdminMenuChoice(int input){
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
        scanner.nextLine();

        System.out.println("Введите автора: ");
        String author = scanner.nextLine();
        scanner.nextLine();

        System.out.println("Введите жанр: ");
        String genre = scanner.nextLine();
        scanner.nextLine();

        service.addBook(title, author, genre);
        System.out.println("Книга \"" + title + "\", " + author + "\", " + genre
                + "\", " + " успешно добавлена.");

    }

    private void deleteBook(){
        System.out.println("Удалить книгу");

        while (true){
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
                    + "\", " +  " успешно удалена.");
        }

    }
    private void whoHasBook(){

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
