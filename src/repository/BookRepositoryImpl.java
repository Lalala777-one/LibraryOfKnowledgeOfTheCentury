package repository;

import model.Book;
import utils.MyArrayList;
import utils.MyList;

import java.util.concurrent.atomic.AtomicInteger;

public class BookRepositoryImpl implements BookRepository {


    private final MyList<Book> books;

    public final AtomicInteger currentId = new AtomicInteger(1);

    public BookRepositoryImpl() {
        this.books = new MyArrayList<>();;
        addBooks();
    }

    // currentId.getAndIncrement() аналог currentId++
    private void addBooks() {
        books.addAll(
                new Book(currentId.getAndIncrement(), "Мастер и Маргарита", "Михаил Булгаков" , "мистика"),
                new Book(currentId.getAndIncrement(), "Сто лет одиночества", "Габриэль Гарсия Маркес",  "магический реализм"),
                new Book(currentId.getAndIncrement(), "Преступление и наказание","Фёдор Достоевский", "классическая литература" ),
                new Book(currentId.getAndIncrement(), "Гордость и предубеждение", "Джейн Остин","роман"),
                new Book(currentId.getAndIncrement(), "О дивный новый мир", "Олдос Хаксли", "антиутопия"),
                new Book(currentId.getAndIncrement(), "1984", "Джордж Оруэлл", "антиутопия"),
                new Book(currentId.getAndIncrement(), "Цветы для Элджернона", "Дэниел Киз", "научная фантастика"),
                new Book(currentId.getAndIncrement(), "Книжный вор", "Маркус Зусак", "исторический роман"),
                new Book(currentId.getAndIncrement(),"Три товарища", "Эрих Мария Ремарк", "роман" ),
                new Book(currentId.getAndIncrement(),"Американские боги", "Нил Гейман", "фэнтези"),
                new Book(currentId.getAndIncrement(),"Шантарам", "Грегори Дэвид Робертс", "роман"),
                new Book(currentId.getAndIncrement(),"Трудно быть богом", "Аркадий и Борис Стругацкие", "научная фантастика"),
                new Book(currentId.getAndIncrement(), "Алхимик", "Пауло Коэльо", "философия"),
                new Book(currentId.getAndIncrement(), "Гарри Поттер и философский камень", "Дж. К. Роулинг", "фэнтези"),
                new Book(currentId.getAndIncrement(), "Гарри Поттер и Тайная комната", "Дж.К. Роулинг", "фэнтези" ),
                new Book(currentId.getAndIncrement(), "Гарри Поттер и узник Азкабана", "Дж.К. Роулинг", "фэнтези"),
                new Book(currentId.getAndIncrement(), "Гарри Поттер и Кубок огня", "Дж.К. Роулинг", "фэнтези"));

    }


    @Override
    public void addBook(String title, String author, String genre) {
        Book book = new Book(currentId.getAndIncrement(), title, author, genre);
        books.add(book); // сохраняем в хранилище всех книг
    }

    @Override
    public MyList<Book> getAllBooks() {
        return books;

    }

    @Override
    public MyList<Book> getAllFreeBooks() {
        MyList<Book> result = new MyArrayList<>();

        for (Book book : books){
            if(!book.isBusy()){
                result.add(book);
            }
        }
        return result;
    }


    @Override
    public MyList<Book> getAllBusyBooks() {
        MyList<Book> result = new MyArrayList<>();
       for(Book book : books){
           if(book.isBusy()){
               result.add(book);
           }
       }
        return result;
    }



    @Override
    public Book getBookById(int id) {
for (Book book : books){
    if(book.getId() == id) return book;
}
return null;
    }


    @Override
    public MyList<Book> getBookByAuthor(String author) {
        MyList<Book> result = new MyArrayList<>();
        for (Book book : books){
            if(book.getAuthor().equals(author)){
                result.add(book);
            }
        }
        return result;
    }


    @Override
    public Book getBookByTitle(String title) {
        for (Book book : books){
            if(book.getTitle().equals(title)){
                return book;
            }
        }
        return null;
    }

    @Override
    public MyList<Book> getBookByGenre(String genre) {
        MyList<Book> result = new MyArrayList<>();
        for (Book book : books){
            if(book.getGenre().equals(genre)){
                result.add(book);
            }
        }
        return result;

    }

    @Override
    public void deleteBook(Book book) {
        // Ходим по кругу. Метод вызывает сам себя до новых веников
//        deleteBook(book);
        books.remove(book);
    }
}
