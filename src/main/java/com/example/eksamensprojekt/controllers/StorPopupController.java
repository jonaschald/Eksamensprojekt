package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class StorPopupController
{
    @FXML
    private ImageView storPopupBillede;

    @FXML
    private Label title;

    // Opretter et SceneManeger objekt - bruges til at skifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    void forrigeBilledeKnap(MouseEvent event) {

    }

    @FXML
    void næsteBilledeKnap(MouseEvent event) {

    }

    @FXML
    void tilbageKnap(MouseEvent event) throws IOException
    {
        sceneManeger.tilbage(event);
    }
}