package org.example.csc311capstone;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.csc311capstone.Module.Book;
import org.example.csc311capstone.Module.Patron;
import org.example.csc311capstone.db.BooksTable;
import org.example.csc311capstone.db.PatronsTable;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;


//This is for the Patron self-service view. -Andrew & Zuxin

public class PatronController implements Initializable {
    @FXML
    private TextField searchText;

    @FXML
    private TableView<Book> tv;
    @FXML
    private TableColumn<Book, String> tvTitle,tvAuthor,tvEdition;
    @FXML
    private TableColumn<Book, Integer> tvISBN,tvLeft;

    //todo: Get ID from login.
    //todo: Disable checkout button if no book is selected or user already has checkout
    private int patronId = 1;

    private final int userId = LoginController.userId;

    private final BooksTable booksTable = new BooksTable();
    private final PatronsTable patronsTable = new PatronsTable();
    private final ObservableList<Book> books = booksTable.listAllBooks();

    //good
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tvISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        tvEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));
        tvLeft.setCellValueFactory(new PropertyValueFactory<>("copiesLeft"));
        tv.setItems(books);
    }
    //good
    @FXML
    void searchBook() {
           String search =  searchText.getText();
           if(!search.isEmpty()){
               //Fuzzy matching
               ObservableList<Book> bookList = FXCollections.observableList(books.stream()
                       .filter(book -> book.getName().toLowerCase().contains(search.toLowerCase())
                                  || book.getAuthor().toLowerCase().contains(search.toLowerCase())
                                  || book.getEdition().toLowerCase().contains(search.toLowerCase())
                                  || String.valueOf(book.getISBN()).contains(search.toLowerCase())
                       )
                       .distinct()
                       .toList());

               tv.setItems(bookList);
           }
    }

    //good
    @FXML
    void checkout() {
        System.out.println("checkout");
        Book b =  tv.getSelectionModel().getSelectedItem();

        if(b != null){
            Dialog<Patron> dialog = new Dialog<>();
            dialog.setTitle("Checkout");

            Integer[] borrowDays = {3,5,7,15,30};
            ObservableList<Integer> options = FXCollections.observableArrayList(borrowDays);
            ComboBox<Integer> daysComboBox = new ComboBox<>(options);
            daysComboBox.getSelectionModel().selectFirst();

            ButtonType confirm = new ButtonType("Confirm",ButtonBar.ButtonData.OK_DONE);
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(confirm);

            VBox vBox = new VBox(8,
                    new Label("Title: "+b.getName()),
                    new Label("ISBN: " + b.getISBN()) ,
                    new Label("Author: " +b.getAuthor()),
                    new Label("Edition: "+b.getEdition()));
            HBox hBox = new HBox(8, new Label("Borrow Days: ") , daysComboBox);
            dialogPane.setContent(new VBox(8, vBox,hBox));

            dialog.setResultConverter((ButtonType button) -> {
                if (button == confirm) {
                    patronsTable.borrowBook(patronId,b.getId(),daysComboBox.getValue());
                }
                return null;
            });

            dialog.showAndWait();
        }
    }




    @FXML
    void rentalDetails() {
        /*Dialog<Patron> dialog = new Dialog<>();
        dialog.setTitle("Rental Details");

        ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(closeButtonType);

        setupDialogContent(dialog);

        dialog.setResultConverter(button -> {
            if (button == closeButtonType) {
                dialog.close();
            }
            return null;
        });

        dialog.showAndWait();*/
    }


    private void setupDialogContent(Dialog<Patron> dialog){
        /*DialogPane dialogPane = dialog.getDialogPane();
        Patron p =  patrons.getFirst();
        Optional<Book> optionalBook = books.stream()
                .filter(book -> book.getId() == p.getCurrBook())
                .findFirst();
        if (optionalBook.isPresent()) {
            Book b = optionalBook.get();

            LocalDate borrowDate = LocalDate.parse(p.getBorrowDate());
            int borrowDays = p.getBorrowDays();
            LocalDate calculatedDueDate = borrowDate.plusDays(borrowDays);
            long leftDays = ChronoUnit.DAYS.between(LocalDate.now(), calculatedDueDate);

            VBox vBox = new VBox(8,
                    new Label("Title: " + b.getName()),
                    new Label("ISBN: " + b.getISBN()),
                    new Label("Author: " + b.getAuthor()),
                    new Label("Edition: " + b.getEdition()),
                    new Label("DueDate: " + calculatedDueDate),
                    new Label("Time Left: " + leftDays + " days"));
            dialogPane.setContent(vBox);
        }else {
            Label Label = new Label("Your are not borrow any book");
            dialogPane.setContent(Label);
        }*/
    }

    @FXML
    void returnBook() {
        System.out.println("returnBook");
        /*if(patrons.isEmpty() || patrons.getFirst() == null) {
            return;
        }

        Patron p = patrons.getFirst();
        if (returnBook(p)) {
            patrons.remove(p);
            showAlert("A book has been returned!");
        } else {
            showAlert("No book to return for the selected patron!");
        }*/


    }

    private boolean returnBook(Patron p){
        /*if (p.getCurrBook() == 0) return false;

        //update patron data
        p.setReturnDate(LocalDate.now().toString());
        Map<String, Object> editPatronInfo = new HashMap<>();
        editPatronInfo.put(patronsColumns.RETURNDATE.name(), p.getReturnDate());
        patronsTable.editPatron(editPatronInfo, p.getID());

        //update book data
        Optional<Book> optionalBook = books.stream()
                .filter(book -> book.getId() == p.getCurrBook())
                .findFirst();
        if (optionalBook.isPresent()) {
            Book b = optionalBook.get();

            int newCopiesLeft = b.getCopiesLeft()+1;
            b.setCopiesLeft(newCopiesLeft);

            Map<String, Object> editBookInfo = new HashMap<>();
            editBookInfo.put(booksColumns.COPIESLEFT.name(), newCopiesLeft);
            booksTable.editBook(editBookInfo,b.getId());

            return true;
        }*/
        return false;
    }

    //good
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}