package com.example.csc311capstone;

public class Book {
    private int id;
    private String bookName;
    private String author;
    private int bookQuantity; // number of books in library

    public Book() {
        id = 0;
        bookName = "";
        author = "";
        bookQuantity = 0;
    }

    public Book(String bookName, String author) {
        this.bookName = bookName;
        this.author = author;
    }

    public Book(int id, String bookName, String author, int bookQuantity) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.bookQuantity = bookQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    @Override
    public String toString(){

        return " ID:" + id+ ","
               + " Book name:" + bookName+ ","
                + " Author:" + author+ ","
                + " Books Quantity:" + bookQuantity;

    }
}
