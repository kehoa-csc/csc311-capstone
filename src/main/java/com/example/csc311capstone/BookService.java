package com.example.csc311capstone;

import java.util.List;

/**
 * The BookService class is used to manage the list of books.
 */
public class BookService {
    private final List<Book> booklist;
    private int id; // A counter used to assign a unique ID to a book

    public BookService(DataManager dataManager) {
        booklist = dataManager.getBooklist();
        id = 1;
    }
    /**
     * Add books to the list.
     * If the book is not in the list, add the book and set its ID;
     * The number of books will be increased regardless of whether they already exist or not.
     *
     * @param book The book object to add
     */
    public void addBook(Book book){
        if(notFindBook(book)) {//if not in the list,just add
            booklist.add(book);
            book.setId(id++);
        }
        // increase the quantity of book , it starts with 0
        book.setBookQuantity(book.getBookQuantity() + 1);

    }
    /**
     * Remove a book from the list.
     * If the book is not in the list, print the message and return;
     * If the book is in the list and the number is greater than 1, the number is reduced;
     * Otherwise, remove the book from the list.
     *
     * @param book book objects to delete
     */
    public void deleteBook(Book book) {
        if(notFindBook(book)){ // not find the book in list
            System.out.println("Unable to delete, Not found the book");
            return;
        }

        // if it in the list and quantity is greater than 1, just decrease the quantity of book
        // otherwise remove the book from list
        if(book.getBookQuantity() > 1){
            book.setBookQuantity(book.getBookQuantity() - 1);
        }else {
            booklist.remove(book);
        }
    }
    /**
     * Check if the book is not on the list.
     *
     * @param book object to be checked
     * @return Returns true if the book is not in the list; Otherwise, it returns false.
     */
    public boolean notFindBook(Book book){

        return findBookByBookName(book.getBookName()) == null;

    }
    /**
     * Find books in the list based on their name.
     *
     * @param bookName The name of the book to be found
     * @return If a book is found, return the book object; Otherwise, null is returned.
     */
    public Book findBookByBookName(String bookName){

        return booklist.stream()
                .filter(book->book.getBookName().equals(bookName))
                .findFirst()
                .orElse(null);
    }




}
