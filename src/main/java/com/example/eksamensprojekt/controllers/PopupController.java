package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class PopupController {

    // Gemmer billedet fra det valgte kunstværk
    public static Image valgtBillede;

    // Gemmer titlen fra det valgte kunstværk
    public static String valgtTitel;

    // Gemmer årstallet fra det valgte kunstværk
    public static String valgtÅrstal;

    // Gemmer beskrivelsen fra det valgte kunstværk
    public static String valgtBeskrivelse;

    // Opretter et SceneManeger objekt - bruges til at skifte mellem FXML sider
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
    public void initialize() {

        // Viser det valgte billede i Pop-up vinduet
        popupBillede.setImage(valgtBillede);

        // Viser titlen på det valgte kunstværk
        titleLabel.setText(valgtTitel);

        // Viser årstallet på det valgte kunstværk
        årstalLabel.setText(valgtÅrstal);

        // Viser beskrivelsen på det valgte kunstværk
        besktivelseLabel.setText(valgtBeskrivelse);
    }

    @FXML
    void forrigeBilledeKnap(MouseEvent event) {

    }

    @FXML
    void næsteBilledeKnap(MouseEvent event) {

    }

    // Går tilbage til den tidligere side
    @FXML
    void tilbageKnap(MouseEvent event) throws IOException {
        sceneManeger.tilbage(event);
    }

    // Skifter scenen til Stor Pop-up
    @FXML
    void tilStorPopUp(MouseEvent event) throws IOException {

        sceneManeger.skiftSceneTilbage(
                event,
                "/com/example/eksamensprojekt/gui/Pop-Up.fxml",
                "/com/example/eksamensprojekt/gui/Stor-Pop-up.fxml"
        );
    }
}