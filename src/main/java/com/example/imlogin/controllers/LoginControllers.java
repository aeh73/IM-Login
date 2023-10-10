package com.example.imlogin.controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class LoginControllers {
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnLoadSignUp;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnLoadSignIn;

    private void fadeTransition(Node node, double fromValue, double toValue, Duration duration, Runnable onFinish) {
        FadeTransition fade = new FadeTransition(duration, node);
        fade.setFromValue(fromValue);
        fade.setToValue(toValue);
        fade.setOnFinished(event -> {
            if (onFinish != null) {
                onFinish.run();
            }
        });
        fade.play();
    }

    @FXML
    private void loadSignUp(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        loadFXML(clickedButton, "sign-Up.fxml");
    }

    @FXML
    private void loadSignIn(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        loadFXML(clickedButton, "sign-In.fxml");
    }

    private void loadFXML(Button clickedButton, String fxmlFile) {
        Node currentRoot = clickedButton.getScene().getRoot();

        fadeTransition(currentRoot, 1.0, 0.0, Duration.millis(500), () -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/imlogin/" + fxmlFile));
                Parent root = loader.load();

                Scene newScene = new Scene(root, 980, 680);
                newScene.setFill(Color.TRANSPARENT);

                Stage stage = (Stage) currentRoot.getScene().getWindow();
                stage.setScene(newScene);

                fadeTransition(root, 0.0, 1.0, Duration.millis(500), null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}