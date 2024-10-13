package com.example.csc311capstone;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

/**
 * The test class is used to test the functionality of the ConsumerService class.
 */

public class ConsumerServiceTest {
    private BookService bookService;
    private ConsumerService consumerService;
    private Consumer consumer1,consumer2,consumer3;

    /**
     * Initialize the data before each test method is executed.
     */
    @Before
    public void setUp(){

        bookService =new ConsumerService();
        consumerService = (ConsumerService)bookService;

        Book book1 = new Book("A","a");
        Book book2 = new Book("B","b");
        Book book3 = new Book("C","c");

        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);
        bookService.addBook(book3);

        consumer1 = new Consumer("BBB","B","10/12/2024");
        consumer2 = new Consumer("CCC","C","9/12/2024");
        consumer3 = new Consumer("DDD","D","8/12/2024");

        consumerService.borrowBook(consumer1);
    }

    /**
     * Test the consumer borrowing feature.
     */
    @Test
    public void test1_borrowBook(){
        consumerService.borrowBook(consumer2);

        List<Book> bookslist = bookService.getBooklist();
        List<Consumer> consumerList = consumerService.getConsumerList();

        assertEquals(3,bookslist.size()); // not change the size of book list
        assertEquals(2,consumerList.size()); // increase the size of consumer list

        String bookName = consumer2.getBookName();
        Book book1 = bookService.findBookByBookName(bookName,bookslist);

        assertEquals(0,book1.getBookQuantity()); //when it is check out, quantity decrease
    }

    /**
     * Test borrowing books that don't exist.
     */
    @Test
    public void test2_borrowBook(){
        consumerService.borrowBook(consumer3); //borrowing not exist book

        List<Consumer> consumerList = consumerService.getConsumerList();

        assertEquals(1,consumerList.size()); // not be increase,books not exist in library

    }

    /**
     * Test repeat borrowing.
     */
    @Test
    public void test3_borrowBook(){
        Consumer consumer4 = new Consumer("BBB","B","10/12/2024");
        consumerService.borrowBook(consumer4); // borrow book2 again

        List<Consumer> consumerList = consumerService.getConsumerList();

        assertEquals(1,consumerList.size()); // not be increase,all books checked out

    }
    /**
     * Test the book return feature.
     */
    @Test
    public void test1_returnBook(){
        consumerService.returnBook(consumer1,"11/12/2024");

        List<Book> bookslist = bookService.getBooklist();
        List<Consumer> consumerList = consumerService.getConsumerList();

        assertEquals(3,bookslist.size());// not change the size of book list
        assertEquals(1,consumerList.size());// not change the size of consumer list

        String bookName = consumer1.getBookName();
        Book book1 = bookService.findBookByBookName(bookName,bookslist);

        assertEquals(1,book1.getBookQuantity()); //when it is return, quantity increase
    }

}
