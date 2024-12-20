package org.example.csc311capstone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.csc311capstone.Module.Patron;
import org.example.csc311capstone.db.PatronsTable;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private ComboBox<String> userType;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label loginStatus;

    private final PatronsTable patronsTable = new PatronsTable();
    private final ObservableList<Patron> patrons = patronsTable.listAllPatrons();

    public static int patronID = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String[] users = {"Student","Staff"};
        ObservableList<String> options = FXCollections.observableArrayList(users);
        userType.setItems(options);
        userType.getSelectionModel().selectFirst();
    }

    @FXML
    void login(ActionEvent actionEvent) {
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                loginStatus.setText("                 Please fill all the fields.");
                return;
            }
            if(userType.getValue().equals("Staff")) {
               if(username.getText().equals("staff") && password.getText().equals("staff")) {
                   openWindow(actionEvent,"view/librarian_view.fxml");
               }
               else {
                   loginStatus.setText("                   Incorrect staff details.");
               }

            }else if(userType.getValue().equals("Student")) {
                Optional<Patron> user = patrons.stream()
                        .filter(p-> p.getName().equals(username.getText())
                                && p.getPassword().equals(password.getText()))
                        .findFirst();
                if(user.isPresent()) {
                    patronID = user.get().getID();
                    openWindow(actionEvent,"view/patron_self_service.fxml");
                } else {
                    loginStatus.setText("      User not found or login incorrect.");
                }
            }else {
                username.setText("");
                password.setText("");
            }
    }

    // open window by different fxml
    private void openWindow(ActionEvent actionEvent, String fxml){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

