package com.example.csc311capstone;


import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookServiceTest {
    private BookService bookService;
    private Book book1,book2,book3;


    @Before
    public void setUp(){
        bookService = new BookService();
        book1 = new Book("A","a");
        book2 = new Book("B","b");
        book3 = new Book("C","c");

        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);
        bookService.addBook(book3);
    }

    @Test
    public void test1_addBook(){
        bookService.addBook(book2); // add an exist book, quantity increase but not book add

        List<Book> bookslist = bookService.getBooklist();

        bookslist.forEach(System.out::println);

        assertEquals(3,bookslist.size()); // total of book not change
        assertEquals(2,book2.getBookQuantity()); // quantity increase

    }

    @Test
    public void test2_addBook(){
        Book book4 = new Book("D","d");
        bookService.addBook(book4); // add new book, size increase

        List<Book> bookslist = bookService.getBooklist();

        bookslist.forEach(System.out::println);

        assertEquals(4,bookslist.size()); // size of book increase

    }

    @Test
    public void test_deleteBook(){
        bookService.deleteBook(book3); // delete book3, quantity decrease but size of list not change


        List<Book> bookslist = bookService.getBooklist();

        bookslist.forEach(System.out::println);

        assertEquals(3,bookslist.size()); // total of book not change
        assertEquals(1,book3.getBookQuantity()); // quantity decrease

    }

}
