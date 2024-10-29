package service;

import model.Book;
import model.User;
import utils.MyList;

public class MainServiceImpl  implements  MainService{
    @Override
    public void addBook(String title, String author) {

    }

    @Override
    public MyList<Book> getAllBooks() {
        return null;
    }

    @Override
    public MyList<Book> getAllFreeBooks() {
        return null;
    }

    @Override
    public MyList<Book> getAllBusyBooks() {
        return null;
    }

    @Override
    public Book getBookById(int id) {
        return null;
    }

    @Override
    public Book getBookByAuthor(String author) {
        return null;
    }

    @Override
    public Book getBookByTitle(String title) {
        return null;
    }

    @Override
    public void deleteBook(Book book) {

    }

    @Override
    public boolean takeBook(int id) {
        return false;
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
