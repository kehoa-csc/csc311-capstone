package org.example.csc311capstone;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.csc311capstone.Module.Book;
import org.example.csc311capstone.db.BooksTable;
import javafx.scene.control.Dialog;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

//Librarian Book View
public class SecondController {



    @FXML
    private Label menuLabel;

    @FXML
    private Button editButton;
    @FXML
    private TextField searchText;

    @FXML
    private TableView<Book> tv;

    @FXML
    private TableColumn<Book, String> tvAuthor,tvEdition,tvTitle;

    @FXML
    private TableColumn<Book, Integer> tvId,tvCopiesLeft,tvISBN, tvQuantity;

    @FXML
    private AnchorPane slider;

    @FXML
    private TextField messageBox;

    private final BooksTable booksTable = new BooksTable();
    private  ObservableList<Book> books = booksTable.listAllBooks();

    @FXML
    public void initialize() {
        tvTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tvISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        tvEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));
        tvCopiesLeft.setCellValueFactory(new PropertyValueFactory<>("copiesLeft"));
        tvId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tvQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tv.setItems(books);
        slider.setTranslateX(-176);

        menuLabel.setOnMouseClicked(event ->{
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)-> {
                menuLabel.setVisible(true);
            } );
        });
    }
    @FXML
    void searchBook() {
        String search =  searchText.getText();
        if(!search.isEmpty()){

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
        messageBox.setText("Results of search query " + search);
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        messageBox.setText("Logging out...");
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/login_view.fxml")));
            Scene scene = new Scene(root);
            Stage window = Application.primaryStage;
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void helpDoc() throws IOException {
        File htmlFile = new File("docs/librarian-book.html");
        Desktop.getDesktop().browse(htmlFile.toURI());
        messageBox.setText("Opening help guide in your browser...");
    }

    @FXML
    protected void showAbout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/about.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void switchToPatronView(){
        messageBox.setText("Switching to Patron View...");
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/librarian_view.fxml")));
            Scene scene = new Scene(root);
            Stage window = Application.primaryStage;
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    protected void close(){
        System.exit(0);
    }

    private void loadBookData(){
        books = booksTable.listAllBooks();
        tv.setItems(books);
    }

    @FXML
    protected void deleteBook(){
        Book selectedBook = tv.getSelectionModel().getSelectedItem();
        if(selectedBook != null) {
            booksTable.removeBook(selectedBook.getId());
            loadBookData();
            showAlert("Success", "Book has been deleted");
            messageBox.setText("Book deleted successfully");
        }else{
            showAlert("Error", "No books have been selected for deletion");
            messageBox.setText("Book deletion failed");
        }

        }
    @FXML
    private void  reconnectDb(){
        tv.setItems(books);
        showAlert("Success", "Database had been reconnected  ");
        messageBox.setText("Database successfully reconnected ");
    }


    @FXML
    protected void editBook(ActionEvent event) {
        Book selectedBook = tv.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert("Error", "Please select a book to edit.");
            return;
        }

        try {
            URL fxmlLocation = getClass().getResource("view/editBook.fxml");
            if (fxmlLocation == null) {
                System.out.println("editBook.fxml not found!");
                showAlert("Error", "EditBook FXML file not found!");
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            DialogPane editBookDialogPane = fxmlLoader.load();
            EditBookController editBookController = fxmlLoader.getController();
            editBookController.setBook(selectedBook);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(editBookDialogPane);
            dialog.setTitle("Edit Book");
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.APPLY) {
                Book updatedBook = editBookController.getBook();
                Map<String, Object> updatedBookMap = Map.of(
                        "name", updatedBook.getName(),
                        "author", updatedBook.getAuthor(),
                        "edition", updatedBook.getEdition(),
                        "ISBN", updatedBook.getISBN(),
                        "copiesLeft", updatedBook.getCopiesLeft(),
                        "quantity", updatedBook.getQuantity()
                );

                booksTable.editBook(updatedBookMap, updatedBook.getId());
                int index = books.indexOf(selectedBook);
                books.set(index, updatedBook);
                tv.refresh();

                showAlert("Success", "Book details updated successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}








