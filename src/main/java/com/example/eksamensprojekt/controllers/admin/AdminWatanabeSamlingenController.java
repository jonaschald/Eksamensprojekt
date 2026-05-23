package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Kunstværk;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Optional;

public class AdminWatanabeSamlingenController
{
    @FXML
    private GridPane billedeContainer;

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // Liste der kan indeholde alle kunstværker fra Databasen
    private ObservableList<Kunstværk> kunstværker = FXCollections.observableArrayList();

    // Gemmer det kunstværk som Admin har klikket på, så det kan redigeres eller slettes
    private Kunstværk valgtKunstværk;

    // Variabel der indeholder billedets binære data (til når Admin vil tilføje et nyt kunstværk)
    private byte[] billedeData;

    // Kører automatisk når FXML siden åbnes
    public void initialize()
    {
        // Henter kunstværkerne fra databasen og gemmer dem i ObservableList kunstværker
        try {
            dao.hentAlleKunstværker(kunstværker);
            visKunstværker(kunstværker);
        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke hente kunstværker fra databasen");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke hente kunstværker fra databasen");
            alert.show();
        }
    }

    // Metode til at vise alle kunstværkerne fra databasen i et GridPane
    public void visKunstværker(ObservableList<Kunstværk> kunstværker)
    {
        // Fjerner alle elementer i vores GridPane - for at undgå dubletter
        billedeContainer.getChildren().clear();

        // Afstand mellem elementerne i GridPane
        billedeContainer.setHgap(40);
        billedeContainer.setVgap(40);

        // Bredden på vores GridPane
        billedeContainer.setPrefWidth(1400);

        // Opretter 2 variabler der holder styr på hvor kunstværkerne placeres i GridPane
        int kolonne = 0;
        int række = 0;

        // Går alle kunstværkerne igennem og sætter hvert kunstværk op i GridPane
        for (Kunstværk kunstværk : kunstværker)
        {
            // Opretter en VBox til at indeholde billedet og informationstekst
            VBox vBox = new VBox();
            vBox.setSpacing(5);
            vBox.setPrefWidth(290);
            vBox.setAlignment(Pos.TOP_LEFT);

            // Henter billedet/kunstværket fra Databasen
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(kunstværk.getBilledeData());

            // Opretter et JavaFX billede ud fra den binær billede data
            Image image = new Image(byteArrayInputStream);

            // Opretter et ImageView der kan vise billedet på skærmen
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(290);
            imageView.setFitHeight(390);
            imageView.setPreserveRatio(true);

            // Gør billedet klikbart for brugeren
            imageView.setStyle("-fx-cursor: hand;");

            // Når Admin klikker på kunstværket, åbnes Pop-up siden, hvor Admin kan redigere i det enkelte kunstværk
            imageView.setOnMouseClicked(event -> {

                // Gemmer det valgte kunstværk, så Pop-up controlleren kan bruge det
                AdminPopUpController.valgtKunstværk = kunstværk;

                // Sender listen med kunstværker til AdminPopUpController
                // så brugeren kan navigere mellem billederne
                AdminPopUpController.setKunstværker(kunstværker);

                try {
                    // Åbner Pop-up siden
                    sceneManeger.skiftSceneTilbage(event,
                            "/com/example/eksamensprojekt/admin/Admin-Watanabe-samlingen.fxml",
                            "/com/example/eksamensprojekt/admin/Admin-Pop-Up.fxml");

                } catch (Exception e) {
                    // Udskriver fejlen i konsollen
                    System.out.println("Kunne ikke åbne Admin Pop-up siden");
                    e.printStackTrace();

                    // Giver brugeren besked om fejlen
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke åbne Pop-up siden");
                    alert.show();
                }
            });

            // Opretter labels med nummer, titel og årstal
            Label nummer = new javafx.scene.control.Label(kunstværk.getId());
            Label titel = new Label(kunstværk.getTitel() + " - " + kunstværk.getÅrstal());
            titel.setWrapText(true);

            // Tilføjer billedet og labels i VBoxen
            vBox.getChildren().addAll(imageView, nummer, titel);

            // Tilføjer VBoxen i GridPane
            billedeContainer.add(vBox, kolonne, række);

            // Hopper til næste kolonne
            kolonne = kolonne + 1;

            // Når der er 4 kunstværker på en række
            if (kolonne == 4) {
                kolonne = 0;
                række = række + 1;
            }
        }
    }

    // Metode til at lave et pop-up vindue, hvor Admin kan vælge hvilke kunstværker der skal slettes
    @FXML
    void sletKunstværk(ActionEvent event)
    {
        // Opretter et pop-up vindue
        Dialog<ButtonType> dialogVindue = new Dialog<>();
        dialogVindue.setTitle("Slet Kunstværker");
        dialogVindue.setHeaderText("Vælg de kunstværker der skal slettes");
        dialogVindue.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Opretter en VBox der skal indeholde Checkboxes til kunstværkerne
        VBox vBox = new VBox(10); // 10 pixels mellemrum mellem elementerne

        // Liste der indeholder alle Checkboxes til kunstværkerne
        ArrayList<CheckBox> checkboxes = new ArrayList<>();

        try {
            // Tømmer listen med kunstværker - for at undgå dubletter
            kunstværker.clear();

            // Henter alle kunstværker fra databasen og gemmer dem i listen kunstværker
            dao.hentAlleKunstværker(kunstværker);

            // Går alle kunstværker igennem og opretter en checkbox til hver
            for (Kunstværk kunstværk : kunstværker)
            {
                // Opretter en Checkbox med kunstværkets titel
                CheckBox checkbox = new CheckBox(kunstværk.getTitel());

                // Tilføjer CheckBoxen til listen
                checkboxes.add(checkbox);

                // Tilføjer CheckBoxen til VBoxen
                vBox.getChildren().add(checkbox);
            }

            // Opretter en ScrollPane til VBoxen med alle checkboxes
            ScrollPane scrollPane = new ScrollPane(vBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(400);

            // Sætter ScrollPane ind i Pop-up vinduet
            dialogVindue.getDialogPane().setContent(scrollPane);

            // Viser pop-up, og stopper og venter på at Admin klikker ok eller cancel
            Optional<ButtonType> resultat = dialogVindue.showAndWait();

            // Hvis Admin klikker ok, så slettes de markerede kunstværker
            if (resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                // Går alle kunstværkerne igennem
                for (int i = 0; i < kunstværker.size(); i++)
                {
                    // Hvis CheckBoxen er markeret med et flueben
                    if (checkboxes.get(i).isSelected())
                    {
                        // Sletter kunstværket fra databasen
                        dao.sletKunstværk(kunstværker.get(i));
                    }
                }

                // Henter kunstværkerne fra databasen igen og opdatere visningen
                kunstværker.clear();
                dao.hentAlleKunstværker(kunstværker);
                visKunstværker(kunstværker);
            }
        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Kunne ikke slette kunstværker");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke slette kunstværker");
            alert.show();
        }

    }

    // Metode til at lave et pop-up vindue, hvor Admin kan tilføje et nyt kunstværk
    @FXML
    void tilføjKunstværker(ActionEvent event)
    {
        // Nulstiller billedData når pop-up vinduet åbnes
        billedeData = null;

        // Opretter et pop-up vindue
        Dialog<ButtonType> dialogVindue = new Dialog<>();
        dialogVindue.setTitle("Tilføj kunstværk");
        dialogVindue.setHeaderText("Indtast oplysninger om kunstværket");
        dialogVindue.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Opretter tekstfelter til kunstværkets oplysninger
        TextField idFelt = new TextField();
        idFelt.setPromptText("Nummer f.eks. MH 1991/1365. 1");
        TextField serienummerFelt = new TextField();
        serienummerFelt.setPromptText("Serienummer f.eks. Tryk 21 ud af 100");
        TextField titelFelt = new TextField();
        titelFelt.setPromptText("Titel");
        TextField årstalFelt = new TextField();
        årstalFelt.setPromptText("Årstal");
        TextField kunstnerFelt = new TextField();
        kunstnerFelt.setPromptText("Kunstner");
        TextField størrelseMedRammeFelt = new TextField();
        størrelseMedRammeFelt.setPromptText("Str. med ramme f.eks. 85x75 cm");
        TextField størrelseUdenRammeFelt = new TextField();
        størrelseUdenRammeFelt.setPromptText("Str. uden ramme f.eks. 80x70 cm");
        TextArea beskrivelsesFelt = new TextArea();
        beskrivelsesFelt.setPromptText("Beskrivelse");
        beskrivelsesFelt.setWrapText(true);

        // Opretter et tekstfelt der viser navnet på billedet
        TextField filFelt = new TextField();
        filFelt.setEditable(false); // Man skal ikke kunne skrive i dette felt
        filFelt.setPrefWidth(415);

        // Knap til at vælge et billede fra computeren
        Button vælgBilledeKnap = new Button("Vælg billede");

        // Opretter en HBox til vælg billede tekstfelt + knap
        HBox vælgBilledeBox = new HBox(10, filFelt, vælgBilledeKnap);

        // Når Admin klikker på knappen "Vælg billede" åbnes computerens filer
        vælgBilledeKnap.setOnAction(e -> {
            // Opretter en fileChooser
            FileChooser fileChooser = new FileChooser();

            // Gør at brugeren kun kan vælge billede filer
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Billedfiler", "*.jpg", "*.png", "*.jpeg"));

            // Åbner filvælgeren
            File valgtFil = fileChooser.showOpenDialog(null);

            // Hvis Admin har valgt en fil
            if (valgtFil != null) {
                try {
                    // Viser filens navn i tekstfeltet
                    filFelt.setText(valgtFil.getName());

                    // Konverterer billedet til binær data
                    billedeData = Files.readAllBytes(valgtFil.toPath());

                } catch (Exception exception) {
                    // Udskriver fejlen i konsollen
                    System.out.println("Kunne ikke vælge billedet");
                    exception.printStackTrace();

                    // Giver brugeren besked om fejlen
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke vælge billedet");
                    alert.show();
                }
            }
        });

        // Opretter en VBox der kan indhold alle felterne
        VBox vBox = new VBox(10, idFelt, serienummerFelt, titelFelt, årstalFelt, kunstnerFelt,
                størrelseMedRammeFelt, størrelseUdenRammeFelt, beskrivelsesFelt, vælgBilledeBox);

        // Sæter VBoxen ind i Pop-up vinduet
        dialogVindue.getDialogPane().setContent(vBox);

        // Viser pop-up, og stopper og venter på at Admin klikker Ok eller cancel
        Optional<ButtonType> resultat = dialogVindue.showAndWait();

        // Hvis Admin klikker ok, oprettes kunstværket og gemmes i databasen
        if (resultat.isPresent() && resultat.get() == ButtonType.OK)
        {
            try {
                // Hvis Id-feltet er tomt får brugeren besked om at den skal udfyldes
                if (idFelt.getText().isEmpty())
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Du skal indtaste et kunstværk nummer f.eks. MH 1991/1365. 1");
                    alert.show();
                    return; // Metoden Stoppes her
                }

                // Hvis billede-feltet er tomt får brugeren besked om at den skal udfyldes
                if (billedeData == null)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Du skal vælge et billede");
                    alert.show();
                    return; // Metoden Stoppes her
                }

                // Hvis årstal-feltet er tomt sættes årstal til 0 - ellers crasher programmet pga. String ""
                int årstal = 0;

                // Hvis årstal-feltet er udfyldt, så hentes årstallet som Admin har tastet ind
                if (!årstalFelt.getText().isEmpty()) {
                    årstal = Integer.parseInt(årstalFelt.getText());
                }

                // Opretter et nyt kunstværk objekt med de indtastede oplysninger
                Kunstværk kunstværk = new Kunstværk(idFelt.getText(), serienummerFelt.getText(), titelFelt.getText(),
                        kunstnerFelt.getText(), årstal, størrelseMedRammeFelt.getText(),
                        størrelseUdenRammeFelt.getText(), beskrivelsesFelt.getText(), billedeData, 4, false);

                // Gemmer kunstværket i databasen
                dao.gemKunstværk(kunstværk);

                // Henter kunstværkerne fra databasen igen og opdaterer visningen
                kunstværker.clear();
                dao.hentAlleKunstværker(kunstværker);
                visKunstværker(kunstværker);
            } catch (Exception e) {
                // Udskriver fejlen i konsollen
                System.out.println("Kunne ikke gemme kunstværket");
                e.printStackTrace();

                // Giver brugeren besked om fejlen
                Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke gemme kunstværket");
                alert.show();
            }
        }
    }

    // Skifter scenen til Admin Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Os.fxml");
    }

    // Skifter scenen til Admin Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Samlingen.fxml");
    }

    // Skifter scenen til Admin Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Temaer.fxml");
    }

    // Skifter scenen til Admin Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/AdminForside.fxml");
    }

    // Skifter scenen til Admin Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/AdminUndervisning.fxml");
    }

    // Skifter scene til Forsiden
    @FXML
    void logudKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}