package org.example.csc311capstone;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.csc311capstone.Module.Book;
import org.example.csc311capstone.db.BooksTable;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

//Librarian Book View
public class SecondController {

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Label menuLabel;

    @FXML
    private Label menuBack;

    @FXML
    private Button reconbdButton;

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

    private final BooksTable booksTable = new BooksTable();
    private final ObservableList<Book> books = booksTable.listAllBooks();

    @FXML
    public void initialize() {
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
                menuBack.setVisible(false);
            } );
        });
        menuBack.setOnMouseClicked(event ->{
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)->{
                menuLabel.setVisible(true);
                menuBack.setVisible(false);
            });
        });
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
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

    @FXML
    protected void close(){
        System.exit(0);
    }


}





