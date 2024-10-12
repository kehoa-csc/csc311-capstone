package com.example.csc311capstone;

import java.util.ArrayList;

public class ConsumerService {
    private final ArrayList<Consumer> consumerList = new ArrayList<>();
    private BookService bookService = new BookService();

    public void borrowBook(Consumer consumer){
        String bookName = consumer.getBookName();
        boolean haveBook = bookService.findBookByBookName(bookName);
        if(haveBook) {
            consumerList.add(consumer);
        }else {
            System.out.println("This book on in Library, please choose another book");
        }
    }

    public void returnBook(Consumer consumer,String returnTime){
        consumer.setReturnTime(returnTime);
    }
}
