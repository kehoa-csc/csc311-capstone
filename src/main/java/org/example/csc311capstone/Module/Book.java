package org.example.csc311capstone.Module;

/**
 * Module class for book
 * @author zuxin
 */
public class Book {
    private int id;
    private int ISBN;
    private String bookName;
    private String author;
    private String edition;
    private int quantity;

    public Book(int id, int ISBN, String bookName, String author, String edition, int quantity) {
        this.id = id;
        this.ISBN = ISBN;
        this.bookName = bookName;
        this.author = author;
        this.edition = edition;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
