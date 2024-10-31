package org.example.csc311capstone.Module;

/**
 * Module class for patrons
 * @author zuxin
 */

/*

    NOTE- THIS IS CURRENTLY OUT OF DATE.
    CHANGES HAVE BEEN MADE TO THE DATABASE SINCE THIS WAS WRITTEN, AND IT NEEDS TO BE UPDATED LATER.
    DO NOT USE FOR THE TIME BEING.

 */
public class Patron {
    private int ID;
    private String name;
    private String currBook;
    private String email;
    private String borrowTime;
    private String returnTime;

    public Patron(int ID, String name, String currBook, String email, String borrowTime, String returnTime) {
        this.ID = ID;
        this.name = name;
        this.currBook = currBook;
        this.email = email;
        this.borrowTime = borrowTime;
        this.returnTime = returnTime;
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

    public String getcurrBook() {
        return currBook;
    }

    public void setcurrBook(String currBook) {
        this.currBook = currBook;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }
}
