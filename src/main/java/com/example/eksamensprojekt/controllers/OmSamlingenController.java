package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class OmSamlingenController {

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private ImageView billedeBund;

    @FXML
    private ImageView billedeTop;

    @FXML
    private Label omSadaoWatanabeTekst;

    @FXML
    public void initialize() {

        Image topBillede = hentBillede(
                "/com/example/eksamensprojekt/Billeder/watanabeTop.jpg"
        );

        Image bundBillede = hentBillede(
                "/com/example/eksamensprojekt/Billeder/watanabeBund.jpg"
        );

        if (topBillede != null) {
            billedeTop.setImage(topBillede);
        }

        if (bundBillede != null) {
            billedeBund.setImage(bundBillede);
        }

        omSadaoWatanabeTekst.setText(
                "Sadao Watanabe var en japansk kunstner kendt for sine "
                        + "farverige bibelske værker og traditionelle japanske "
                        + "trykmetoder. Hans kunst kombinerer kristne fortællinger "
                        + "med japansk kultur og design. "
                        + "\n\n"
                        + "Denne samling giver brugeren mulighed for hurtigt "
                        + "at få et overblik over hele udstillingen."
        );
    }

    private Image hentBillede(String sti) {

        var stream = getClass().getResourceAsStream(sti);

        if (stream == null) {
            System.out.println("Billede blev ikke fundet: " + sti);
            return null;
        }

        return new Image(stream);
    }

    // Skifter scenen til Admin Login
    @FXML
    void adminKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event,
                "/com/example/eksamensprojekt/gui/Login.fxml");
    }

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {

        try {
            Desktop.getDesktop().browse(
                    new URI("https://kunsthalholmen.dk/")
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Skifter scenen til Favoritter
    @FXML
    void favoritterKnap(MouseEvent event) throws IOException {

        sceneManeger.skiftSceneMouse(event,
                "/com/example/eksamensprojekt/gui/Favoritter.fxml");
    }

    // Skifter scenen til Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException {

        sceneManeger.skiftSceneMouse(event,
                "/com/example/eksamensprojekt/gui/Om-Os.fxml");
    }

    // Skifter scenen til Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException {

        sceneManeger.skiftSceneMouse(event,
                "/com/example/eksamensprojekt/gui/Forside.fxml");
    }

    // Skifter scenen til Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException {

        sceneManeger.skiftSceneMouse(event,
                "/com/example/eksamensprojekt/gui/Temaer.fxml");
    }

    // Skifter scenen til Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException {

        sceneManeger.skiftSceneMouse(event,
                "/com/example/eksamensprojekt/gui/Undervisning.fxml");
    }

    // Skifter scenen til Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException {

        sceneManeger.skiftSceneMouse(event,
                "/com/example/eksamensprojekt/gui/Watanabe-samlingen.fxml");
    }
}