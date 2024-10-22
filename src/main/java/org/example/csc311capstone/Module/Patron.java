package org.example.csc311capstone.Module;

/**
 * Module class for patrons
 * @author zuxin
 */
public class Patron {
    private int ID;
    private String name;
    private String bookName;
    private String email;
    private String borrowTime;
    private String returnTime;

    public Patron(int ID, String name, String bookName, String email, String borrowTime, String returnTime) {
        this.ID = ID;
        this.name = name;
        this.bookName = bookName;
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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
