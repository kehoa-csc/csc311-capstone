package com.example.csc311capstone;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a temporary class that is used to manage the data,
 * and then Database needs to replace it
 */
public class DataManager {
    private final List<Book> booklist;
    private final List<Consumer> consumerList;

    public DataManager() {
        this.booklist = new ArrayList<>();
        this.consumerList = new ArrayList<>();
    }

    public List<Book> getBooklist() {
        return booklist;
    }

    public List<Consumer> getConsumerList() {
        return consumerList;
    }
}
