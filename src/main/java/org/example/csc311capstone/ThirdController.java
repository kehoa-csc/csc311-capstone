package org.example.csc311capstone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.csc311capstone.Module.Patron;
import org.example.csc311capstone.db.PatronsTable;


//Librarian Patron view
public class ThirdController {

    @FXML
    private TableView<Patron> tableView;

    @FXML
    private TableColumn<Patron, Integer> colID;

    @FXML
    private TableColumn<Patron, String> colName, colEmail, colBorrowDate, colReturnDate;

    @FXML
    private TextField searchField;

    @FXML
    private Button btnAdd, btnEdit, btnDelete;

    private final PatronsTable patronsTable = new PatronsTable();
    private ObservableList<Patron> patrons = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
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
    }

    @FXML
    public void deletePatron() {
        Patron selectedPatron = tableView.getSelectionModel().getSelectedItem();
        if (selectedPatron != null) {
            patronsTable.removePatron(selectedPatron.getID());
            loadPatronData();
            showAlert("Success", "Patron deleted successfully.");
        } else {
            showAlert("Error", "No patron selected for deletion.");
        }
    }

    @FXML
    public void addPatron() {
        // Logic to open an Add Patron dialog (to be implemented)
        showAlert("Info", "Add Patron functionality is pending implementation.");
    }

    @FXML
    public void editPatron() {
        Patron selectedPatron = tableView.getSelectionModel().getSelectedItem();
        if (selectedPatron != null) {
            // Logic to open an Edit Patron dialog (to be implemented)
            showAlert("Info", "Edit Patron functionality is pending implementation.");
        } else {
            showAlert("Error", "No patron selected for editing.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
