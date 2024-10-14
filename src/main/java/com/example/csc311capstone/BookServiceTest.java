package com.example.csc311capstone;


import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;
/**
 * The test class, which is used to verify the functionality of the BookService class.
 */
public class BookServiceTest {
    private List<Book> bookslist;
    private BookService bookService;
    private Book book1,book2,book3;

    /**
     * Initialize the data before each test method is executed.
     */
    @Before
    public void setUp(){
        DataManager dataManager = new DataManager();
        bookService = new BookService(dataManager);
        bookslist = dataManager.getBooklist();

        book1 = new Book("A","a");
        book2 = new Book("B","b");
        book3 = new Book("C","c");

        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);
        bookService.addBook(book3);
    }

    /**
     * Test adding new books.
     */
    @Test
    public void test1_addBook(){
        Book book4 = new Book("D","d");
        bookService.addBook(book4); // add new book, size increase

        assertEquals(4,bookslist.size()); // size of book increase

    }

    /**
     * Test adding books that already exist.
     */
    @Test
    public void test2_addBook(){
        bookService.addBook(book2); // add an exist book, quantity increase but not book add

        assertEquals(3,bookslist.size()); // total of book not change
        assertEquals(2,book2.getBookQuantity()); // quantity increase

    }

    /**
     * Test to delete existing books, but multiple
     */
    @Test
    public void test1_deleteBook(){
        bookService.deleteBook(book3); // delete book3

        assertEquals(3,bookslist.size()); // size of list book not change
        assertEquals(1,book3.getBookQuantity()); // quantity decrease

    }

    /**
     * Test to delete existing books, but only one
     *
     */

    @Test
    public void test2_deleteBook(){
        bookService.deleteBook(book1); // delete book1

        assertEquals(2,bookslist.size()); // size of list change
    }

}
