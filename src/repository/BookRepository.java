package repository;

import model.Book;
import utils.MyList;

public interface BookRepository {

    void addBook(String title, String author);

    MyList<Book> getAllBooks();

    MyList<Book> getAllFreeBooks();

    MyList<Book> getAllBusyBooks();


// ???
    Book getBookById(int id);

    Book getBookByAuthor(String author);

    Book getBookByTitle(String title);




    void deleteBook(Book book); // например испортил клиент


    // ОПЦИОНАЛЬНО

}
