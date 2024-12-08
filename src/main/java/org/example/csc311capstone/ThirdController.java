package org.example.csc311capstone;

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
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.csc311capstone.Module.Patron;
import org.example.csc311capstone.db.ConnDbOps;
import org.example.csc311capstone.db.PatronsTable;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


//Librarian Patron view
public class ThirdController {

    @FXML
    private TableView<Patron> tableView;
    @FXML
    private Label menuLabel;

    @FXML
    private AnchorPane slider;

    @FXML
    private TableColumn<Patron, Integer> colID;

    @FXML
    private TableColumn<Patron, String> colName, colEmail, colBorrowDate, colReturnDate;

    @FXML
    private TextField searchField, messageBox;

    @FXML
    private Button btnAdd, btnEdit, btnDelete;

    private final PatronsTable patronsTable = new PatronsTable();
    private ObservableList<Patron> patrons = FXCollections.observableArrayList();
    private final ConnDbOps connDbOps = new ConnDbOps();

    @FXML
    public void initialize() {
        slider.setTranslateX(-200);

        menuLabel.setOnMouseClicked(event ->{
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-200);

            slide.setOnFinished((ActionEvent e)-> {
                menuLabel.setVisible(true);
            } );
        });
        setupTableColumns();
        loadPatronData();
    }

    private void setupTableColumns() {
        colID.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getID()).asObject());
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        colEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
        colBorrowDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBorrowDate()));
        colReturnDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getReturnDate()));
    }

    private void loadPatronData() {
        patrons = patronsTable.listAllPatrons(); // Already implemented in PatronsTable.java
        tableView.setItems(patrons);
    }

    @FXML
    public void searchPatron() {
        String searchText = searchField.getText().toLowerCase().trim();
        if (searchText.isEmpty()) {
            loadPatronData();
        } else {
            ObservableList<Patron> filteredPatrons = FXCollections.observableArrayList(
                    patrons.stream()
                            .filter(patron -> patron.getName().toLowerCase().contains(searchText) ||
                                    patron.getEmail().toLowerCase().contains(searchText))
                            .toList()
            );
            tableView.setItems(filteredPatrons);
        }
        messageBox.setText("Results of search query " + searchText);
    }

    @FXML
    public void deletePatron() {
        Patron selectedPatron = tableView.getSelectionModel().getSelectedItem();
        if (selectedPatron != null) {
            patronsTable.removePatron(selectedPatron.getID());
            loadPatronData();
            showAlert("Success", "Patron deleted successfully.");
            messageBox.setText("Patron deleted successfully.");
        } else {
            showAlert("Error", "No patron selected for deletion.");
            messageBox.setText("No patron selected for deletion.");
        }
    }

    @FXML
    public void addPatron() {
        // Logic to open an Add Patron dialog (to be implemented)
        //showAlert("Info", "Add Patron functionality is pending implementation.");
        System.out.println("Adding patron");

        Dialog<Patron> dialog = new Dialog<>();
        dialog.setTitle("Add Patron");

        ButtonType confirm = new ButtonType("Confirm",ButtonBar.ButtonData.OK_DONE);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(confirm);

        TextField nameField = new TextField();
        TextField emailField = new TextField();

        VBox vBox = new VBox(8,
                new Label("Name"),
                nameField,
                new Label("Email"),
                emailField);
        HBox hBox = new HBox(8);
        dialogPane.setContent(new VBox(8, vBox,hBox));

        dialog.setResultConverter((ButtonType button) -> {
            if (button == confirm) {

                System.out.println("Confirm button");
                connDbOps.addPatron(nameField.getText(), emailField.getText());
                messageBox.setText("Added patron successfully.");
            }
            loadPatronData();
            return null;
        });

        dialog.showAndWait();
        //connDbOps.addPatron();
    }
    

    @FXML
    public void editPatron() {
        Patron selectedPatron = tableView.getSelectionModel().getSelectedItem();
        if (selectedPatron != null) {
            Dialog<Patron> dialog = new Dialog<>();
            dialog.setTitle("Edit Patron");

            ButtonType confirmButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

            TextField nameField = new TextField(selectedPatron.getName());
            TextField emailField = new TextField(selectedPatron.getEmail());

            VBox vBox = new VBox(8,
                    new Label("Name"),
                    nameField,
                    new Label("Email"),
                    emailField);
            dialog.getDialogPane().setContent(vBox);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButtonType) {
                    selectedPatron.setName(nameField.getText());
                    selectedPatron.setEmail(emailField.getText());
                    return selectedPatron;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(updatedPatron -> {
                patronsTable.updatePatron(updatedPatron.getID(), updatedPatron.getName(), updatedPatron.getEmail());
                loadPatronData();
                showAlert("Success", "Patron details updated successfully.");
                messageBox.setText("Patron updated successfully.");
            });
        } else {
            showAlert("Error", "No patron selected for editing.");
            messageBox.setText("No patron selected for editing.");
        }
    }






    @FXML
    public void switchToBooksView() throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/second_view.fxml")));
            Scene scene = new Scene(root);
            Stage window = Application.primaryStage;
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        messageBox.setText("Switching to Books View...");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void close(){
        System.exit(0);
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
        File htmlFile = new File("docs/librarian-patron.html");
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
}
