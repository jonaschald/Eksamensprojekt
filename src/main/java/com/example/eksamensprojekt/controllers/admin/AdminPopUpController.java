package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.SceneManeger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AdminPopUpController {

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

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {

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
    void tilbageTilOversigtKnap(MouseEvent event) {

    }

    @FXML
    void tilStorPopUp(MouseEvent event) {

    }
}