package repository;

import model.Book;
import utils.MyList;

public class BookRepositoryImpl implements BookRepository {
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
}
