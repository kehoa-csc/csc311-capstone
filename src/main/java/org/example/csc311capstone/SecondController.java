package org.example.csc311capstone;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondController {


    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Label menuLabel,menuBack;

    @FXML
    private Button reconbdButton;

    @FXML
    private AnchorPane slider;

   // @Override
    public void initialize(URL location, ResourceBundle resources) {

            slider.setTranslateX(-176);
            menuLabel.setOnMouseClicked( event ->{
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
}





