package com.example.csc311capstone;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final List<Book> booklist = new ArrayList<>();
    private int id = 1;


    public void addBook(Book book){
        if(notFindBook(book)) {//if not in the list,just add
            booklist.add(book);
            book.setId(id++);
        }
        // increase the quantity of book , it starts with 0
        book.setBookQuantity(book.getBookQuantity() + 1);

    }

    public void deleteBook(Book book) {
        if(notFindBook(book)){ // not find the book in list
            System.out.println("Not found the book");
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

    public boolean notFindBook(Book book){
        for(Book inList:booklist){
            if(book == inList) return false;
        }
        return true;

    }

    public boolean findBookByBookName(String bookName){
        for(Book inList:booklist){
            if(bookName.equals(inList.getBookName()) ) {
                return true;
            }
        }
        return false;

    }

    public List<Book> getBooklist(){
        return booklist;
    }



}
