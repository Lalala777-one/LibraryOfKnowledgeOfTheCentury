package model;

import java.util.Objects;

public class Book {

    private final int id;
    private String title;
    private String author;
    private boolean isBusy;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;

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
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isBusy=" + isBusy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return id == book.id;
    }


}
