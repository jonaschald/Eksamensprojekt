package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class PopupController {

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private Label InfoLabel;

    @FXML
    private Label besktivelseLabel;

    @FXML
    private ImageView popupBillede;

    @FXML
    private Label titleLabel;

    @FXML
    private Label årstalLabel;

    @FXML
    void forrigeBilledeKnap(MouseEvent event) {

    }

    @FXML
    void næsteBilledeKnap(MouseEvent event) {

    }

    @FXML
    void tilbageKnap(MouseEvent event) throws IOException {
        sceneManeger.tilbage(event);
    }

    // Skifter scenen til Stor Pop-up
    @FXML
    void tilStorPopUp(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneTilbage (event,
                "/com/example/eksamensprojekt/gui/Pop-Up.fxml",
                "/com/example/eksamensprojekt/gui/Stor-Pop-up.fxml");
    }
}
