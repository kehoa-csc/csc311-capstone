package org.example.csc311capstone.Module;

/**
 * Module class for patrons
 * @author zuxin
 */

/*

    NOTE-THIS IS CURRENTLY OUT OF DATE.
    CHANGES HAVE BEEN MADE TO THE DATABASE SINCE THIS WAS WRITTEN, AND IT NEEDS TO BE UPDATED LATER.
    DO NOT USE FOR THE TIME BEING.

 */
public class Patron {
    private int ID;
    private String name;
    private int currBook;
    private String email;
    private String returnDate;
    private String borrowDate;

    /**
     * default constructor, set ID is 0 and other variables are null
     */
    public Patron() {
        this.ID = 0;
        this.name = "";
        this.currBook = 0;
        this.email = "";
        this.returnDate = "";
        this.borrowDate = "";
    }

    public Patron(int ID, String name, int currBook, String email, String returnDate, String borrowDate) {
        this.ID = ID;
        this.name = name;
        this.currBook = currBook;
        this.email = email;
        this.returnDate = returnDate;
        this.borrowDate = borrowDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrBook() {
        return currBook;
    }

    public void setCurrBook(int currBook) {
        this.currBook = currBook;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }
}
