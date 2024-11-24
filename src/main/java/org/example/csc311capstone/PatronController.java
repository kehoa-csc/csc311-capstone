package org.example.csc311capstone;

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
    private final int patronId = LoginController.patronID;

    private final BooksTable booksTable = new BooksTable();
    private final PatronsTable patronsTable = new PatronsTable();
    private final ObservableList<Book> books = booksTable.listAllBooks();
    private final Patron currPatron = patronsTable.findPatronById(patronId);

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
           }else {
               tv.setItems(books);
           }
    }

    @FXML
    void checkout() {
        Book b =  tv.getSelectionModel().getSelectedItem();
        if(b == null) {
            showAlert("Not select book");
            return;
        }
        if(b.getCopiesLeft() == 0){
            showAlert("This book has all been borrowed, please choose another book");
            return;
        }
        if(currPatron.getCurrBook() != 0){
            showAlert("Only one book is allowed per person, so please return the book first");
            return;
        }

        showCheckOut(b);

    }
    // show check out in dialog
    private void showCheckOut(Book b){
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
                //update current patron
                currPatron.setCurrBook(b.getId());
                currPatron.setBorrowDate(LocalDate.now().toString());
                currPatron.setBorrowDays(daysComboBox.getValue());
                //update patron in database
                patronsTable.borrowBook(patronId,b.getId(),daysComboBox.getValue());

                ////update data in table view as copies left -1
                int newCopiesLeft = b.getCopiesLeft()-1;
                b.setCopiesLeft(newCopiesLeft);
                int i = books.indexOf(b);
                books.set(i,b);

                // update book in database
                Map<String, Object> editBookInfo = new HashMap<>();
                editBookInfo.put(booksColumns.COPIESLEFT.name(), newCopiesLeft);
                booksTable.editBook(editBookInfo,b.getId());


            }
            return null;
        });

        dialog.showAndWait();
    }



    //good
    @FXML
    void rentalDetails() {
        if(currPatron.getCurrBook() == 0){
            showAlert("You haven't borrowed any books yet");
            return;
        }

        showRentalDetails(currPatron);

    }

    //show rental details in dialog
    private void showRentalDetails(Patron p){

        Dialog<Patron> dialog = new Dialog<>();
        dialog.setTitle("Rental Details");

        ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(closeButtonType);

        DialogPane dialogPane = dialog.getDialogPane();
        Optional<Book> optionalBook = books.stream()
                .filter(book -> book.getId() == p.getCurrBook())
                .findFirst();
        if (optionalBook.isPresent()) {
            Book b = optionalBook.get();

            LocalDate borrowDate = LocalDate.parse(p.getBorrowDate());
            int borrowDays = p.getBorrowDays();
            LocalDate calculatedDueDate = borrowDate.plusDays(borrowDays);
            long leftDays = ChronoUnit.DAYS.between(LocalDate.now(), calculatedDueDate);

            if(p.getCurrBook() != 0) {
                VBox vBox = new VBox(8,
                        new Label("Title: " + b.getName()),
                        new Label("ISBN: " + b.getISBN()),
                        new Label("Author: " + b.getAuthor()),
                        new Label("Edition: " + b.getEdition()),
                        new Label("DueDate: " + calculatedDueDate),
                        new Label("Time Left: " + leftDays + " days"));
                dialogPane.setContent(vBox);
            }
        } else {
            showAlert("This book not exist.");
        }

        dialog.setResultConverter(button -> {
            if (button == closeButtonType) {
                dialog.close();
            }
            return null;
        });

        dialog.showAndWait();

    }

    @FXML
    void returnBook() {

        if (isReturnBook(currPatron)) {
            showAlert("A book has been returned!");
        } else {
            showAlert("No book to return for the selected patron!");
        }
    }

    //return book possessing
    private boolean isReturnBook(Patron p){
        if (p.getCurrBook() == 0) {
            return false;
        }

        //get the book
        Optional<Book> optionalBook = books.stream()
                .filter(book -> book.getId() == p.getCurrBook())
                .findFirst();
        if (optionalBook.isPresent()) {
            //update book data
            Book b = optionalBook.get();
            int newCopiesLeft = b.getCopiesLeft()+1;
            b.setCopiesLeft(newCopiesLeft);


            //update data in table view
            int i = books.indexOf(b);
            books.set(i,b);

            //update book data in database
            Map<String, Object> editBookInfo = new HashMap<>();
            editBookInfo.put(booksColumns.COPIESLEFT.name(), newCopiesLeft);
            booksTable.editBook(editBookInfo,b.getId());


            //update patron data
            p.setReturnDate(LocalDate.now().toString());
            p.setCurrBook(0);
            //update patron data in database
            Map<String, Object> editPatronInfo = new HashMap<>();
            editPatronInfo.put(patronsColumns.RETURNDATE.name(), p.getReturnDate());
            editPatronInfo.put(patronsColumns.CURRBOOK.name(), 0);
            patronsTable.editPatron(editPatronInfo, p.getID());

            return true;

        }else {
            return false;
        }
    }

    //show alert message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}