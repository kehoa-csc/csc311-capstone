package org.example.csc311capstone;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.example.csc311capstone.Module.Patron;
import org.example.csc311capstone.db.PatronsTable;
import javafx.scene.control.TextField;
import  javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem staff,student;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private final PatronsTable patronsTable = new PatronsTable();
    private final ObservableList<Patron> patrons = patronsTable.listAllPatrons();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void login(ActionEvent actionEvent) {
        Optional<Patron> user  = patrons.stream()
                                          .filter(p-> p.getName().equals(username.getText())
                                                  && p.getPassword().equals(password.getText()))
                                          .findFirst();
        if(user.isPresent() && !menuButton.getItems().isEmpty()){

            if(menuButton.getItems().equals(staff)) {
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/librarian_view.fxml")));
                    Scene scene = new Scene(root, 900, 600);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(menuButton.getItems().equals(student)) {
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/patron_self_service.fxml")));
                    Scene scene = new Scene(root, 900, 600);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

