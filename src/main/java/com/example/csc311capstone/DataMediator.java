package com.example.csc311capstone;


/**
 * This is a temporary class that is used to manage the data,
 * and synchronous date to Database
 */
public class DataMediator {
    private final BookService bookService;
    private final ConsumerService consumerService;


    public DataMediator(BookService bookService,ConsumerService consumerService) {
        this.bookService = bookService;
        this.consumerService = consumerService;
    }

    public void syncData(){
        //synchronous books data to database
        //synchronous consumers data to database
    }

    public void addBook(Book book){
        bookService.addBook(book);
        // add book to database
    }

    public void removeBook(Book book){
        bookService.deleteBook(book);
        //remove book from database
    }

    public void updateBook(Book book){
        bookService.updateBooks(book);
        //update book in database
    }

    public void borrowBook(Consumer consumer){
        consumerService.borrowBook(consumer);
        // add consumer to database
    }

    public void returnBook(Consumer consumer,String returnTime){
        consumerService.returnBook(consumer,returnTime);
        //update date in database
    }

}
