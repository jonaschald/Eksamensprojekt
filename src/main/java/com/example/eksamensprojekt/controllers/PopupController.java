package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Kunstværk;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class PopupController
{
    public static Kunstværk valgtKunstværk;

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
    private Button favoritKnap;

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

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

        // Hvis et billede er sat som favorit, så ændres knappens udseende til "- fjern fra favoritter"
        if(valgtKunstværk.isFavorit()) {
            favoritKnap.setText("- fjern fra favoritter");
        } else {
            // Og omvendt
            favoritKnap.setText("+ tilføj til favoritter");
        }
    }

    @FXML
    void forrigeBilledeKnap(MouseEvent event) {

    }

    @FXML
    void næsteBilledeKnap(MouseEvent event) {

    }

    @FXML
    void downloadKnap(ActionEvent event) {

    }

    // Metode til at brugeren kan tilføje eller fjerne et kunstværk som favorit via. Favorit knappen
    @FXML
    void tilføjTilFavoritterKnap(ActionEvent event) {
        try {
            // Hvis kunstværket er markeret som favorit og brugeren klikker på knappen
            if(valgtKunstværk.isFavorit()) {
                dao.fjernFavorit(valgtKunstværk); // Fjernes kunstværket som favorit i databasen
                valgtKunstværk.setFavorit(false); // Opdatere kunstværkets favoritstatus til false
                favoritKnap.setText("+ tilføj til favoritter"); // Ændre knappens tekst
            } else {
                dao.tilføjFavorit(valgtKunstværk); // Tilføjes kunstværket som favorit i databasen
                valgtKunstværk.setFavorit(true); // Opdatere kunstværkets favoritstatus til true
                favoritKnap.setText("- fjern fra favoritter"); // Ændre knappens tekst
            }
        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke ændre favorit-status på kunstværket");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke ændre favorit-status på kunstværket");
            alert.show();
        }
    }

    // Går tilbage til den tidligere side
    @FXML
    void tilbageKnap(MouseEvent event) throws IOException
    {
        sceneManeger.tilbage(event);
    }

    // Skifter scenen til Stor Pop-up
    @FXML
    void tilStorPopUp(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneTilbage(event,
                "/com/example/eksamensprojekt/gui/Pop-Up.fxml",
                "/com/example/eksamensprojekt/gui/Stor-Pop-up.fxml");
    }
}