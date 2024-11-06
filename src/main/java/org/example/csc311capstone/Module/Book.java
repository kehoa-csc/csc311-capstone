package org.example.csc311capstone.Module;

/**
 * Module class for a book
 * @author zuxin
 */

/*

    NOTE - THIS IS CURRENTLY OUT OF DATE.
    CHANGES HAVE BEEN MADE TO THE DATABASE SINCE THIS WAS WRITTEN, AND IT NEEDS TO BE UPDATED LATER.
    DO NOT USE FOR THE TIME BEING.

 */
public class Book {
    private int id;
    private int ISBN;
    private String name;
    private String author;
    private String edition;
    private int quantity;
    private int copiesLeft;

    public Book() {
        this.id = 0;
        this.ISBN = 0;
        this.name = "";
        this.author = "";
        this.edition = "";
        this.quantity = 0;
        this.copiesLeft = 0;
    }

    public Book(int id, int ISBN, String name, String author, String edition, int quantity, int copiesLeft) {
        this.id = id;
        this.ISBN = ISBN;
        this.name = name;
        this.author = author;
        this.edition = edition;
        this.quantity = quantity;
        this.copiesLeft = copiesLeft;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getCopiesLeft() {return copiesLeft;}

    public void setCopiesLeft(int copiesLeft) {this.copiesLeft = copiesLeft;}

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", ISBN=" + ISBN +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", edition='" + edition + '\'' +
                ", quantity=" + quantity +
                ", copiesLeft=" + copiesLeft +
                '}';
    }
}
