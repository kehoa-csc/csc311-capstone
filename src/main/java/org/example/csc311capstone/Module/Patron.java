package org.example.csc311capstone.Module;
import javafx.beans.property.*;
/**
 * Module class for patrons
 * @author zuxin
 */

public class Patron {
    private int ID;
    private String name;
    private int currBook;
    private String email;
    private String returnDate;
    private String borrowDate;
    private String password;
    private int borrowDays;

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
        this.password = "";
        this.borrowDays = 0;
    }

    public Patron(int ID, String name, int currBook, String email, String returnDate, String borrowDate, String password, int borrowDays) {
        this.ID = ID;
        this.name = name;
        this.currBook = currBook;
        this.email = email;
        this.returnDate = returnDate;
        this.borrowDate = borrowDate;
        this.password = password;
        this.borrowDays = borrowDays;
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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getBorrowDays() {
        return borrowDays;
    }
    public void setBorrowDays(int borrowDays) {
        this.borrowDays = borrowDays;
    }

    @Override
    public String toString() {
        return "Patron{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", currBook=" + currBook +
                ", email='" + email + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", borrowDate='" + borrowDate + '\'' +
                ", password='" + password + '\'' +
                ", borrowDays=" + borrowDays +
                '}';
    }
}
