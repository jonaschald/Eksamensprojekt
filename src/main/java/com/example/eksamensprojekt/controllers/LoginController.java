package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController
{
    @FXML
    private TextField emailField;

    @FXML
    private Label forgotPassword;

    @FXML
    private PasswordField passwordFiels;

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Skifter scenen til Admin Forside
    @FXML
    void login(ActionEvent event) throws IOException
    {
        sceneManeger.skiftSceneAction (event, "/com/example/eksamensprojekt/admin/AdminForside.fxml");
    }
}