package com.example.csc311capstone;

public class Consumer {
    private String consumerName;
    private String bookName;
    private String browserTime;
    private String returnTime;

    public Consumer() {
        consumerName = "";
        bookName = "";
        browserTime = null;
        returnTime = null;
    }

    public Consumer(String consumerName, String bookName, String browserTime) {
        this.consumerName = consumerName;
        this.bookName = bookName;
        this.browserTime = browserTime;
    }

    public Consumer(String consumerName, String bookName, String browserTime, String returnTime) {
        this.consumerName = consumerName;
        this.bookName = bookName;
        this.browserTime = browserTime;
        this.returnTime = returnTime;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBrowserTime() {
        return browserTime;
    }

    public void setBrowserTime(String browserTime) {
        this.browserTime = browserTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    @Override
    public String toString(){
        return " Consumer:" + consumerName+ ","
                + " Book name:" + bookName+ ","
                + " Browser time:" + browserTime+ ","
                + " Return time:" + returnTime;

    }
}
