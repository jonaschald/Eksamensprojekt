package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.database.DAO;
import com.example.eksamensprojekt.database.DAOImplementation;
import com.example.eksamensprojekt.objekter.Kunstværk;
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
import javafx.scene.layout.ColumnConstraints;
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

public class WatanabeSamlingenController {

    // Opretter et SceneManeger objekt - bruges til at skrifte mellem FXML sider
    SceneManeger sceneManeger = new SceneManeger();

    // Opretter et DAO objekt - bruges til kommunikation med databasen
    DAO dao = new DAOImplementation();

    // ObservableList der kan indeholde alle kunstværkerne fra Databasen
    ObservableList<Kunstværk> kunstværker = FXCollections.observableArrayList();

    @FXML private TextField searchField;

    @FXML
    private GridPane billedeContainer;

    @FXML
    public void initialize()
    {
        // Henter kunstværkerne fra Databasen og viser kunstværkerne
        try {
            dao.hentAlleKunstværker(kunstværker);
            visKunstværk();
        } catch (Exception e) {
            // Udskriver fejlen i konsollen
            System.out.println("Fejl i Initialize i WatanabeSamlingenController: " +
                    "Kunne ikke hente Watanabe-samlingen fra databasen");
            e.printStackTrace(); // Printer hele fejlen i konsollen

            // Giver brugeren besked om fejlen
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunstværkerne kunne ikke hentes fra Databasen");
            alert.show();
        }
    }

    // Metode til at vise alle kunstværkerne fra databasen i et GridPane
    public void visKunstværk()
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
            vBox.setSpacing(5); // 5 pixels afstand mellem alle elementerne i hver VBox
            vBox.setPrefWidth(290); // Bredden på VBoxen
            vBox.setAlignment(Pos.TOP_LEFT); // Indholdet placeres øverst til venstre i VBoxen

            // Henter billedet/kunstværket fra Databasen
            // byteArrayInputStream kan omdanne binær data (byte[]) til et billede
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(kunstværk.getBilledeData());

            // Opretter et JavaFX billede ud fra billeddataene
            Image image = new Image(byteArrayInputStream);

            // Opretter et ImageView der kan vise billedet på skærmen
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(290);
            imageView.setFitHeight(390);
            imageView.setPreserveRatio(true); // Bevarer billedet ratio, så det ikke strækkes/trækkes i

            // Opretter labels med nummer, titel og årstal
            Label nummer = new Label(kunstværk.getId());
            Label titel = new Label(kunstværk.getTitel() + " - " + kunstværk.getÅrstal());
            titel.setWrapText(true); // Hvis titlen er for lang, går teksten automatisk ned på næste linje

            // Tilføjer billedet og labels i VBoxen
            vBox.getChildren().addAll(imageView, nummer, titel);

            // Tilføjer VBoxen i GridPane med placering fra "Kolonne" og "Række"
            billedeContainer.add(vBox, kolonne, række);

            // Hopper til næste kolonne, så næste kunstværk bliver placeret ved siden af
            kolonne = kolonne + 1;

            // Når der er 4 kunstværker på en række, nulstilles kolonnen til 0 igen og vi hopper en række ned
            if (kolonne == 4) {
                kolonne = 0;
                række = række + 1;
            }
        }
    }

    @FXML
    void filterAarstalOp(ActionEvent event) {
    }

    @FXML
    void filterAarstalNed(ActionEvent event) {
    }

    @FXML
    void downloadHeleSamlingen(ActionEvent event) {
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

            // Blev download annulleret, eller er downloadstien ikke en mappe?
            if (downloadSti == null || !downloadSti.isDirectory()) {
                return;
            }

            // Skab en ny zip fil og check om den allerede eksistere i mappen
            fil = new File(downloadSti, zipNavn + ".zip");
            if (fil.exists()) {
                // Spørg brugeren om de vil erstatte den eksisterende zip fil med den nye
                int svar = JOptionPane.showConfirmDialog(
                        null,
                        "Filen findes allerede. Overskriv?",
                        "Bekræft overskrivning",
                        JOptionPane.YES_NO_OPTION
                );

                // Brugeren har trykket nej, download ikke
                if (svar != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Prøv at downloade zip filen til downloadstien
            try {
                downloadTilZip(malerier.toPath(), fil.toPath());
            } catch (IOException | UncheckedIOException e) {
                throw new RuntimeException(e.getMessage());
            }
        } catch (URISyntaxException | RuntimeException e) {
            System.out.println("Fejl med download: " + e.getMessage());
        }
    }

    // Skab et vindue der giver brugeren muligheden for at angive hvor Watanabe Samlingen skal downloades
    private File spørgEfterDownloadSti() {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Brugeren skal kun vælge en mappe
        jfc.setAcceptAllFileFilterUsed(false);

        int result = jfc.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile();
        }

        return null; // Brugeren har annulleret download
    }

    // Download alle filer i en mappe til en zip fil
    private void downloadTilZip(Path kilde, Path zipFil) throws IOException {
        // Skab en FileOutputStream of ZipOutputStream så vi kan skrive billederne til zip filen
        // FileOutputStream skriver direkte til filen på disk
        // ZipOutputStream sørger for at formatet bliver en gyldig zip fil
        try (
                FileOutputStream fos = new FileOutputStream(zipFil.toFile());
                ZipOutputStream zipOut = new ZipOutputStream(fos)
        ) {
            // Gå gennem mappen rekursivt og finder kun normale filer (filer der ikke er mapper)
            Files.walk(kilde)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        // Find filens relative sti ifølge kildemappen, så den kan bevare mappestrukturen i zip filen
                        Path relativ = kilde.relativize(path);

                        try (InputStream in = Files.newInputStream(path)) {
                            // Lav et ZipEntry til filen og sikrer at stier bruger et "/" i stedet for "\"
                            ZipEntry entry = new ZipEntry(relativ.toString().replace("\\", "/"));
                            zipOut.putNextEntry(entry);

                            // Skriver filen ind i zip filen
                            byte[] buffer = new byte[8192];
                            int len;

                            while ((len = in.read(buffer)) > 0) {
                                zipOut.write(buffer, 0, len);
                            }

                            // Lukker den nuværende fil i zip filen
                            zipOut.closeEntry();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });

            // Sluk strømmen, så zip filen gøres gyldig
            zipOut.finish();
            Desktop.getDesktop().open(zipFil.toFile());
        }
    }

    @FXML
    void adminKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Login.fxml");
    }

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://kunsthalholmen.dk/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void favoritterKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Favoritter.fxml");
    }

    @FXML
    void omOsKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Om-Os.fxml");
    }

    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Om-Samlingen.fxml");
    }

    @FXML
    void temaerKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Temaer.fxml");
    }

    @FXML
    void undervisningKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Undervisning.fxml");
    }

    @FXML
    void tilStartSide(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}