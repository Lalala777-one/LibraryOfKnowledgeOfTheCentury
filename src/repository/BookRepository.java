package repository;

import model.Book;
import utils.MyList;

public interface BookRepository {

    void addBook(String title, String author, String genre);

    MyList<Book> getAllBooks();

    MyList<Book> getAllFreeBooks();

    MyList<Book> getAllBusyBooks();

    Book getBookById(int id);

    MyList<Book> getBookByAuthor(String author);

    Book getBookByTitle(String title);

    MyList<Book> getBookByGenre(String genre);

    void deleteBook(Book book); // например испортил клиент

}
