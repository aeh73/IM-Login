package com.example.imlogin.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
    @FXML
    private Button btnClose;
    @FXML
    private TextField tfHost;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private ImageView imgClose;

    @FXML
    private void handleClose() {
        Platform.exit();
    }
    @FXML
    private void imgRotate() {
        //System.out.println("imgRotate method called");
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), imgClose);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.playFromStart();
    }
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

    @FXML
    private void handleSignIn() {
        if (connectToDatabase()) {
            loadFXML(btnSignIn, "main.fxml");
        }
    }

    private boolean connectToDatabase() {
        String dbHost = tfHost.getText();
        String dbUsername = tfEmail.getText();
        String dbPassword = tfPassword.getText();

        String jdbcUrl = "jdbc:mysql://" + dbHost + ":3306/aeh73_SignUp_Login_Test";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            System.out.println("Connected to the database successfully!");

            //close the connection when done
            connection.close();
            return true; // Return true if connection was successful
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database. Please check your credentials and try again.");
            return false; // Return false if connection failed
        }
    }
}