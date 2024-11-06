package model;

import utils.MyList;

import java.util.Objects;

public class Book {

    private final int id;
    private String title;
    private String author;
    private String genre;
    private boolean isBusy;

    public Book(int id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;

    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }


    public String getTitle(){
    return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    @Override
    public String toString() {
        return String.format("Книга { ID: %-3s | Название: %-35s | Автор: %-35s | Жанр: %-25s | Занята: %-5s }",
                id, title, author, genre, isBusy ? "Да" : "Нет");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return id == book.id;
    }


}
