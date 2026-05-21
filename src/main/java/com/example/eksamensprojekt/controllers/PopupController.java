package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Kunstværk;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PopupController
{
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

    // Gemmer det valgte kunstværk, så informationerne kan bruges i Pop-up siden
    public static Kunstværk valgtKunstværk;

    // Opretter et SceneManeger objekt - bruges til at skifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // ObservableList der kan indeholde alle kunstværker fra Databasen
    private static ObservableList<Kunstværk> alleKunstværker = FXCollections.observableArrayList();

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        // Viser det valgte kunstværk og dets info i Pop-upp'en
        visKunstværk(valgtKunstværk);
    }

    // Når brugeren klikker på næste pil vises næste kunstværk i PopUp'en
    @FXML
    void næsteBilledeKnap(MouseEvent event)
    {
        // Henter index på det kunstværk som brugeren står på nu (i listen med alle kunstværker)
        int index = alleKunstværker.indexOf(valgtKunstværk);

        // Tjekker at der findes et næste billede efter det billede vi står på nu
        // Hvis vi er på sidste index (alleKunstværker.size() - 1) så findes der ikke et næste billede
        if (index < alleKunstværker.size() - 1)
        {
            // Henter det næste billede ved at gå et index op
            valgtKunstværk = alleKunstværker.get(index + 1);
            // Viser det næste billede i Pop-up'en
            visKunstværk(valgtKunstværk);
        } else {
            // Hvis brugeren står på det sidste billede, vises det første billede i listen
            valgtKunstværk = alleKunstværker.get(0);
            visKunstværk(valgtKunstværk);
        }
    }

    // Når brugeren klikker på forrige pil vises det forrige kunstværk i PopUp'en
    @FXML
    void forrigeBilledeKnap(MouseEvent event)
    {
        // Henter index på det kunstværk som brugeren står på nu (i listen med alle kunstværker)
        int index = alleKunstværker.indexOf(valgtKunstværk);

        // Tjekker at der findes et billede før det billede vi står på nu
        // Hvis 0, så er vi på det sidste index og så er der ikke et forrige billede
        if (index > 0)
        {
            // Henter det forrige billede ved at gå en index ned
            valgtKunstværk = alleKunstværker.get(index - 1);
            // Viser det forrige billede i Pop-up'en
            visKunstværk(valgtKunstværk);
        } else {
            // Hvis brugeren står på det første billede, vises det sidste billede i listen
            valgtKunstværk = alleKunstværker.get(alleKunstværker.size() - 1);
            visKunstværk(valgtKunstværk);
        }
    }

    @FXML
    void downloadKnap(ActionEvent event) {

    }

    // Metode til at brugeren kan tilføje eller fjerne et kunstværk som favorit via. Favorit knappen
    @FXML
    void tilføjTilFavoritterKnap(ActionEvent event)
    {
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

    // Metode til at vise det valgte kunstværk og dets info i Pop-upp'en
    public void visKunstværk(Kunstværk kunstværk)
    {
        // Viser det valgte billede i Pop-up vinduet
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(valgtKunstværk.getBilledeData());
        Image image = new Image(byteArrayInputStream);
        popupBillede.setImage(image);

        // Viser titlen på det valgte kunstværk
        titleLabel.setText(valgtKunstværk.getTitel());

        // Viser årstallet på det valgte kunstværk
        årstalLabel.setText(String.valueOf(valgtKunstværk.getÅrstal()));

        // Viser beskrivelsen på det valgte kunstværk
        besktivelseLabel.setText(valgtKunstværk.getBeskrivelse());

        // Viser kunstværkets nummer og størrelse
        InfoLabel.setText("Nr: " + valgtKunstværk.getId() +
                "\nStr. m/ramme: " + valgtKunstværk.getStørrelseMedRamme() +
                "\nStr. u/ramme: " + valgtKunstværk.getStørrelseUdenRamme());

        // Hvis et billede er sat som favorit, så ændres knappens udseende til "- fjern fra favoritter"
        if(valgtKunstværk.isFavorit()) {
            favoritKnap.setText("- fjern fra favoritter");
        } else {
            // Og omvendt
            favoritKnap.setText("+ tilføj til favoritter");
        }
    }

    // Metode til sende listen med kunstværker til pop-up'en - de værker der skal navigeres i
    // Listen afhænger af om brugeren er inde på hele samlingen, favoritter eller temaer
    public static void setKunstværker(ObservableList<Kunstværk> kunstværker) {
        alleKunstværker = kunstværker;
    }
}