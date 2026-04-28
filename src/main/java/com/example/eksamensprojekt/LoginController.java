package com.example.eksamensprojekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private TextField emailField;

    @FXML
    private Label forgotPassword;

    @FXML
    private PasswordField passwordFiels;

    @FXML
    void login(ActionEvent event) throws IOException {
        sceneManeger.skiftSceneAction (event, "/com/example/eksamensprojekt/AdminForside.fxml");

    }

}
