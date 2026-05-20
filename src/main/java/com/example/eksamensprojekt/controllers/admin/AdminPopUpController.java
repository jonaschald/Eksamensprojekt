package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.SceneManeger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AdminPopUpController {

    @FXML
    private ImageView popupBillede;

    @FXML
    private TextField titleFelt;

    @FXML
    private TextField årstalFelt;

    @FXML
    private TextArea InfoFelt;

    @FXML
    private TextArea besktivelseFelt;


    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Kører automatisk når FXML siden åbnes
    public void initialize() {

    }

    @FXML
    void forrigeBilledeKnap(MouseEvent event) {

    }

    @FXML
    void næsteBilledeKnap(MouseEvent event) {

    }

    @FXML
    void redigerKunstværk(ActionEvent event) {

    }

    @FXML
    void tilføjTilTema(ActionEvent event) {

    }

    @FXML
    void tilbageTilOversigtKnap(MouseEvent event) throws IOException {
        sceneManeger.tilbage(event);
    }
}