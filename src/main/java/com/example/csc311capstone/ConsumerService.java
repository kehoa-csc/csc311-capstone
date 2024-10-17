package com.example.csc311capstone;

import java.util.ArrayList;
import java.util.List;

/**
 * The ConsumerService class is inherited from the BookService class
 * and provides services for consumers to borrow and return books.
 */
public class ConsumerService extends BookService{
    private final List<Consumer> consumerList;

    public ConsumerService() {
        super();
        consumerList = new ArrayList<>();
    }

    public List<Consumer> getAllConsumers(){
        return consumerList;
    }
    /**
     * Handle consumer requests to borrow books.
     *
     * @param consumer Consumers who borrow books
     */
    public void borrowBook(Consumer consumer){
        String bookName = consumer.getBookName();
        Book book = findBookByBookName(bookName);
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
        Book book = findBookByBookName(bookName);
        if(book == null){
            System.out.println("This book not in Library");
            return;
        }

        consumer.setReturnTime(returnTime);
        book.setBookQuantity(book.getBookQuantity() + 1);
    }

    /**
     * Remove a consumer from the list.
     * @param consumer consumer objects to remove
     */
    public void removeConsumer(Consumer consumer){
        if(findConsumerByName(consumer) == null) {
            System.out.println("Not found this consumer");
            return;
        }
        consumerList.remove(consumer);
    }

    /**
     * Find consumer in the list based on their name.
     * @param consumer consumer to be found
     * @return If a consumer is found, return the consumer object; Otherwise, null is returned.
     */
    public Consumer findConsumerByName(Consumer consumer){
        return consumerList.stream()
                            .filter(c->c.getConsumerName().equals(consumer.getConsumerName()))
                            .findFirst()
                            .orElse(null);
    }


}
