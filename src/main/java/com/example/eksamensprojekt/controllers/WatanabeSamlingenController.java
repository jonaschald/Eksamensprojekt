package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Kunstværk;
import com.example.eksamensprojekt.undervisning.DataDeling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class WatanabeSamlingenController
{
    @FXML
    private Label adresse;
    @FXML
    private Label telefon;
    @FXML
    private Label email;
    @FXML
    private Label åbningstider;

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // ObservableList der kan indeholde alle kunstværkerne fra Databasen
    ObservableList<Kunstværk> kunstværker = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @FXML
    private GridPane billedeContainer;

    @FXML
    public void initialize()
    {
        // Henter kunstværkerne fra Databasen og viser kunstværkerne
        try {
            dao.hentAlleKunstværker(kunstværker);
            visKunstværker(kunstværker);
        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Fejl i Initialize i WatanabeSamlingenController: " +
                            "Kunne ikke hente Watanabe-samlingen fra databasen");
            e.printStackTrace();

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunstværkerne kunne ikke hentes fra Databasen");
            alert.show();
        }

        // Lytter efter tekst i søgefeltet og filtrerer kunstværkerne automatisk
        searchField.textProperty().addListener(
                (observable, oldValue, newValue) -> soegKunstvaerk(newValue));

        // Så bundlinjen viser de informationer man som Admin sætter i Om Os
        adresse.textProperty().bindBidirectional(DataDeling.omOsAdresse2());
        telefon.textProperty().bindBidirectional(DataDeling.omOsTelefon2());
        email.textProperty().bindBidirectional(DataDeling.omOsEmail2());
        if (DataDeling.omOsÅbningstider != null) { åbningstider.setText(DataDeling.omOsÅbningstider); }
    }

    // Metode til at vise alle kunstværkerne fra databasen i et GridPane
    // Bruges både til at vise hele samlingen og søgeresultater
    private void visKunstværker(ObservableList<Kunstværk> kunstværker)
    {
        // Fjerner alle elementer i vores GridPane - for at undgå dubletter
        billedeContainer.getChildren().clear();

        // Afstand mellem felterne
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

            // Opretter et JavaFX billede ud fra billeddataene
            Image image = new Image(byteArrayInputStream);

            // Opretter et ImageView der kan vise billedet på skærmen
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(290);
            imageView.setFitHeight(390);
            imageView.setPreserveRatio(true);

            // Gør billedet klikbart for brugeren
            imageView.setStyle("-fx-cursor: hand;");

            // Når brugeren klikker på billedet, sendes informationerne videre til Pop-up siden
            imageView.setOnMouseClicked(event ->
            {
                // Gemmer det valgte kunstværk i PopUpController
                // så brugeren kan tilføje eller fjerne kunstværket som favorit
                PopupController.valgtKunstværk = kunstværk;

                try {
                    // Skifter til Pop-up siden, hvor kunstværket vises i større format
                    sceneManeger.skiftSceneTilbage(event,
                            "/com/example/eksamensprojekt/gui/Watanabe-samlingen.fxml",
                            "/com/example/eksamensprojekt/gui/Pop-Up.fxml"
                    );

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Opretter labels med nummer, titel og årstal
            Label nummer = new Label(kunstværk.getId());
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

    // Søger efter kunstværker ud fra ID, titel eller årstal
    private void soegKunstvaerk(String soegeTekst) {

        // Hvis søgefeltet er tomt, vises hele samlingen igen
        if (soegeTekst == null || soegeTekst.isBlank())
        {
            visKunstværker(kunstværker);
            return;
        }

        // Gør søgeteksten til små bogstaver, så søgningen ikke er følsom over for store/små bogstaver
        String tekst = soegeTekst.toLowerCase();

        // Opretter en ny liste til de kunstværker, der matcher søgningen
        ObservableList<Kunstværk> filtreretListe = FXCollections.observableArrayList();

        // Gennemgår alle kunstværker og tjekker om ID, titel eller årstal matcher søgeteksten
        for (Kunstværk kunstværk : kunstværker)
        {
            boolean matcher = kunstværk.getId().toLowerCase().contains(tekst)
                    || kunstværk.getTitel().toLowerCase().contains(tekst)
                    || String.valueOf(kunstværk.getÅrstal()).contains(tekst);

            // Hvis kunstværket matcher søgningen, tilføjes det til søgeresultatet
            if (matcher) {
                filtreretListe.add(kunstværk);
            }
        }

        // Viser kun de kunstværker, der matcher søgningen
        visKunstværker(filtreretListe);
    }

    // Sorterer kunstværker fra ældste til nyeste årstal
    @FXML
    void filterAarstalOp(ActionEvent event)
    {
        FXCollections.sort(kunstværker, (a, b) -> Integer.compare(a.getÅrstal(), b.getÅrstal()));

        // Rydder søgefeltet, så hele den sorterede liste vises
        searchField.clear();

        // Viser den sorterede liste
        visKunstværker(kunstværker);
    }

    // Sorterer kunstværker fra nyeste til ældste årstal
    @FXML
    void filterAarstalNed(ActionEvent event) {

        FXCollections.sort(kunstværker, (a, b) -> Integer.compare(b.getÅrstal(), a.getÅrstal()));

        // Rydder søgefeltet, så hele den sorterede liste vises
        searchField.clear();

        // Viser den sorterede liste
        visKunstværker(kunstværker);
    }

    // Downloader hele Watanabe-samlingen som en ZIP-fil
    @FXML
    void downloadHeleSamlingen(ActionEvent event)
    {
        System.out.println("Downloader en test fil i .zip format");

        String sti = "/com/example/eksamensprojekt/Billeder";
        String zipNavn = "Watanabe_Samling";
        File fil;

        try {
            // Finder mappen i resources
            File malerier = new File(getClass().getResource(sti).toURI());

            // Er mappen en mappe?
            if (!malerier.isDirectory()) {
                System.out.println("Malerier eksisterer ikke");
                return;
            }

            // Spørg brugeren efter en downloadsti
            File downloadSti = spørgEfterDownloadSti();

            // Blev download annulleret
            if (downloadSti == null || !downloadSti.isDirectory()) {
                return;
            }

            // Skab en ny zip fil
            fil = new File(downloadSti, zipNavn + ".zip");

            if (fil.exists())
            {
                int svar = JOptionPane.showConfirmDialog(
                        null,
                        "Filen findes allerede. Overskriv?",
                        "Bekræft overskrivning",
                        JOptionPane.YES_NO_OPTION
                );

                if (svar != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            try {
                downloadTilZip(malerier.toPath(), fil.toPath());
            } catch (IOException | UncheckedIOException e) {
                throw new RuntimeException(e.getMessage());
            }

        } catch (URISyntaxException | RuntimeException e) {
            System.out.println("Fejl med download: " + e.getMessage());
        }
    }

    // Skab et vindue der giver brugeren muligheden for at angive hvor
    // Watanabe Samlingen skal downloades
    private File spørgEfterDownloadSti()
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);
        int result = jfc.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION)
        {
            return jfc.getSelectedFile();
        }

        return null;
    }

    // Download alle filer i en mappe til en zip fil
    private void downloadTilZip(Path kilde, Path zipFil) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFil.toFile());
             ZipOutputStream zipOut = new ZipOutputStream(fos))
        {
            Files.walk(kilde).filter(Files::isRegularFile).forEach(path ->
            {
                        Path relativ = kilde.relativize(path);

                        try (InputStream in = Files.newInputStream(path))
                        {
                            ZipEntry entry = new ZipEntry(relativ.toString().replace("\\", "/"));
                            zipOut.putNextEntry(entry);

                            byte[] buffer = new byte[8192];

                            int len;

                            while ((len = in.read(buffer)) > 0)
                            {
                                zipOut.write(buffer, 0, len);
                            }

                            zipOut.closeEntry();

                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });

            zipOut.finish();
            Desktop.getDesktop().open(zipFil.toFile());
        }
    }

    @FXML
    void adminKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Login.fxml");
    }

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event)
    {
        try {
            Desktop.getDesktop().browse(new URI("https://kunsthalholmen.dk/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void favoritterKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Favoritter.fxml");
    }

    @FXML
    void omOsKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Om-Os.fxml");
    }

    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Om-Samlingen.fxml");
    }

    @FXML
    void temaerKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Temaer.fxml");
    }

    @FXML
    void undervisningKnap(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Undervisning.fxml");
    }

    @FXML
    void tilStartSide(MouseEvent event) throws IOException
    {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}