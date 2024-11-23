package org.example.csc311capstone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.csc311capstone.Module.Book;
import org.example.csc311capstone.db.BooksTable;

import java.net.URL;
import java.util.ResourceBundle;

//This is for the Patron self-service view. -Andrew

public class MainController implements Initializable {
    BooksTable bt = new BooksTable();
    ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    TableView tv;
    @FXML
    TableColumn tvTitle, tvAuthor, tvISBN, tvEdition, tvQuantity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    @FXML
    public void refresh() {
        tv.getItems().clear();
        bookList = bt.listAllBooks();

        tvTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tvISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        tvEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));
        tvQuantity.setCellValueFactory(new PropertyValueFactory<>("copiesLeft"));

        tv.setItems(bookList);

    }
}