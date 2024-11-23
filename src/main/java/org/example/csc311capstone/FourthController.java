package org.example.csc311capstone;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FourthController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
