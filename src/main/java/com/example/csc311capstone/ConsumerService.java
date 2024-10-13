package com.example.csc311capstone;

import java.util.ArrayList;
import java.util.List;

/**
 * The ConsumerService class is inherited from the BookService class
 * and provides services for consumers to borrow and return books.
 */
public class ConsumerService extends BookService {
    private final List<Consumer> consumerList;
    private final List<Book> booklist = getBooklist();

    public ConsumerService() {
        consumerList = new ArrayList<>();
    }
    /**
     * Get a list of consumers.
     *
     * @return Returns a list of consumers
     */
    public List<Consumer> getConsumerList() {
        return consumerList;
    }
    /**
     * Handle consumer requests to borrow books.
     *
     * @param consumer Consumers who borrow books
     */
    public void borrowBook(Consumer consumer){
        String bookName = consumer.getBookName();
        Book book = findBookByBookName(bookName,booklist);
        if(book == null){
            System.out.println("This book not in Library");
            return;
        }
        if(book.getBookQuantity() > 0) { // quality mush greater then 0
            consumerList.add(consumer);
            book.setBookQuantity(book.getBookQuantity() - 1);
        }else {
            System.out.println("All books checked out");
        }
    }
    /**
     * Handle consumer requests for book returns.
     *
     * @param consumer Consumers who return books
     * @param returnTime time to return the book
     */

    public void returnBook(Consumer consumer,String returnTime){
        String bookName = consumer.getBookName();
        Book book = findBookByBookName(bookName,booklist);
        if(book == null){
            System.out.println("This book not in Library");
            return;
        }

        consumer.setReturnTime(returnTime);
        book.setBookQuantity(book.getBookQuantity() + 1);
    }


}
