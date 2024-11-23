package org.example.csc311capstone;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.example.csc311capstone.Module.Book;
import org.example.csc311capstone.Module.Patron;
import org.example.csc311capstone.db.BooksTable;
import org.example.csc311capstone.db.PatronsTable;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;


//This is for the Patron self-service view. -Andrew

public class PatronController implements Initializable {
    @FXML
    private TextField searchText;

    @FXML
    private TableView<Book> tv;
    @FXML
    private TableColumn<Book, String> tvTitle,tvAuthor,tvEdition;
    @FXML
    private TableColumn<Book, Integer> tvISBN,tvLeft;

    private final BooksTable booksTable = new BooksTable();
    private final PatronsTable patronsTable = new PatronsTable();
    private final ObservableList<Book> books = booksTable.listAllBooks();
    private final ObservableSet<Patron> patronSet = FXCollections.observableSet();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tvTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tvISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        tvEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));
        tvLeft.setCellValueFactory(new PropertyValueFactory<>("copiesLeft"));
        tv.setItems(books);

    }
    @FXML
    void searchBook(MouseEvent event) {
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

    @FXML
    void checkout(MouseEvent event) {
        System.out.println("checkout");
        Book b =  tv.getSelectionModel().getSelectedItem();

        if(b != null){
            showCheckout();
        }

    }

    //Todo: need a beautify the window in fxml
    private void showCheckout(){
        Dialog<Patron> dialog = new Dialog<>();
        dialog.setTitle("Checkout");
        Book b = tv.getSelectionModel().getSelectedItem();
        Label titleLabel =  new Label("Title: "+b.getName());
        Label ISBNLabel =  new Label("ISBN: " + b.getISBN());
        Label authorLabel =  new Label("Author: " +b.getAuthor());
        Label editionLabel =  new Label("Edition: "+b.getEdition());

        TextField nameTextField  = new TextField();
        nameTextField.setPromptText("Name");
        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Password");
        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Email");

        Integer[] borrowDays = {3,5,7,15,30};
        ObservableList<Integer> options = FXCollections.observableArrayList(borrowDays);
        ComboBox<Integer> daysComboBox = new ComboBox<>(options);
        daysComboBox.getSelectionModel().selectFirst();

        ButtonType confirm = new ButtonType("Confirm");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(confirm);
        VBox vBox1 = new VBox(8, titleLabel, ISBNLabel , authorLabel, editionLabel);
        VBox vBox2 = new VBox(8, nameTextField, passwordTextField , emailTextField, daysComboBox);
        dialogPane.setContent(new VBox(8, vBox1,vBox2));

        Platform.runLater(nameTextField::requestFocus);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == confirm) {
                Patron p =  new Patron();
                p.setName(nameTextField.getText());
                p.setEmail(emailTextField.getText());
                p.setPassword(passwordTextField.getText());
                p.setBorrowDays(daysComboBox.getValue());
                p.setBorrowDate(LocalDate.now().toString()); //YYYY-MM-DD
                p.setCurrBook(b.getId());
                System.out.println(p);

                addPartonInfo(p);
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void addPartonInfo(Patron p){
        //freely edit the value in patron table
        Map<String, Object> addPatronInfo = new HashMap<>();
        addPatronInfo.put(patronsColumns.NAME.name(), p.getName());
        addPatronInfo.put(patronsColumns.EMAIL.name(), p.getEmail());
        addPatronInfo.put(patronsColumns.PASSWORD.name(), p.getPassword());
        addPatronInfo.put(patronsColumns.BORROWDAYS.name(), p.getBorrowDays());
        addPatronInfo.put(patronsColumns.BORROWDATE.name(), p.getBorrowDate());
        addPatronInfo.put(patronsColumns.CURRBOOK.name(), p.getCurrBook());
        patronsTable.addPatron(addPatronInfo);// calling from PatronsTable
        patronSet.add(p);
    }

    @FXML
    void rentalDetails(MouseEvent event) {
        System.out.println("rentalDetails");
    }

    private void showRentalDetails(){
        Dialog<Patron> dialog = new Dialog<>();
        dialog.setTitle("Checkout");
        Book b = tv.getSelectionModel().getSelectedItem();
        Label titleLabel =  new Label("Title: "+b.getName());
        Label ISBNLabel =  new Label("ISBN: " + b.getISBN());
        Label authorLabel =  new Label("Author: " +b.getAuthor());
        Label editionLabel =  new Label("Edition: "+b.getEdition());
    }

    @FXML
    void returnBook(MouseEvent event) {
        System.out.println("returnBook");
    }
}