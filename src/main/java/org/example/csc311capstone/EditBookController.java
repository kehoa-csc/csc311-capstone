package org.example.csc311capstone;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.csc311capstone.Module.Book;

public class EditBookController {

    @FXML
    private TextField titleTF, authorTF, editionTF, isbnTF, leftTF, quantTF;

    private Book book;

    public void setBook(Book book) {
        this.book = book;
        titleTF.setText(book.getName());
        authorTF.setText(book.getAuthor());
        editionTF.setText(book.getEdition());
        isbnTF.setText(String.valueOf(book.getISBN()));
        leftTF.setText(String.valueOf(book.getCopiesLeft()));
        quantTF.setText(String.valueOf(book.getQuantity()));
    }

    public Book getBook() {
        book.setName(titleTF.getText());
        book.setAuthor(authorTF.getText());
        book.setEdition(editionTF.getText());
        book.setISBN(Integer.parseInt(isbnTF.getText()));
        book.setCopiesLeft(Integer.parseInt(leftTF.getText()));
        book.setQuantity(Integer.parseInt(quantTF.getText()));
        return book;
    }
}
