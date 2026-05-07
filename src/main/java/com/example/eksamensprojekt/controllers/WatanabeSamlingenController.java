package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.SceneManeger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class WatanabeSamlingenController {

    SceneManeger sceneManeger = new SceneManeger();

    @FXML
    private ImageView kunstværk1;

    @FXML
    private ImageView kunstværk10;

    @FXML
    private ImageView kunstværk11;

    @FXML
    private ImageView kunstværk12;

    @FXML
    private ImageView kunstværk13;

    @FXML
    private ImageView kunstværk14;

    @FXML
    private ImageView kunstværk15;

    @FXML
    private ImageView kunstværk16;

    @FXML
    private ImageView kunstværk17;

    @FXML
    private ImageView kunstværk18;

    @FXML
    private ImageView kunstværk19;

    @FXML
    private ImageView kunstværk2;

    @FXML
    private ImageView kunstværk20;

    @FXML
    private ImageView kunstværk21;

    @FXML
    private ImageView kunstværk22;

    @FXML
    private ImageView kunstværk23;

    @FXML
    private ImageView kunstværk24;

    @FXML
    private ImageView kunstværk25;

    @FXML
    private ImageView kunstværk26;

    @FXML
    private ImageView kunstværk27;

    @FXML
    private ImageView kunstværk28;

    @FXML
    private ImageView kunstværk29;

    @FXML
    private ImageView kunstværk3;

    @FXML
    private ImageView kunstværk30;

    @FXML
    private ImageView kunstværk31;

    @FXML
    private ImageView kunstværk32;

    @FXML
    private ImageView kunstværk33;

    @FXML
    private ImageView kunstværk34;

    @FXML
    private ImageView kunstværk35;

    @FXML
    private ImageView kunstværk36;

    @FXML
    private ImageView kunstværk4;

    @FXML
    private ImageView kunstværk5;

    @FXML
    private ImageView kunstværk6;

    @FXML
    private ImageView kunstværk7;

    @FXML
    private ImageView kunstværk8;

    @FXML
    private ImageView kunstværk9;

    @FXML
    private Label kunstværkBeskrivelse1;

    @FXML
    private Label kunstværkBeskrivelse10;

    @FXML
    private Label kunstværkBeskrivelse11;

    @FXML
    private Label kunstværkBeskrivelse12;

    @FXML
    private Label kunstværkBeskrivelse13;

    @FXML
    private Label kunstværkBeskrivelse14;

    @FXML
    private Label kunstværkBeskrivelse15;

    @FXML
    private Label kunstværkBeskrivelse16;

    @FXML
    private Label kunstværkBeskrivelse17;

    @FXML
    private Label kunstværkBeskrivelse18;

    @FXML
    private Label kunstværkBeskrivelse19;

    @FXML
    private Label kunstværkBeskrivelse2;

    @FXML
    private Label kunstværkBeskrivelse20;

    @FXML
    private Label kunstværkBeskrivelse21;

    @FXML
    private Label kunstværkBeskrivelse22;

    @FXML
    private Label kunstværkBeskrivelse23;

    @FXML
    private Label kunstværkBeskrivelse24;

    @FXML
    private Label kunstværkBeskrivelse25;

    @FXML
    private Label kunstværkBeskrivelse26;

    @FXML
    private Label kunstværkBeskrivelse27;

    @FXML
    private Label kunstværkBeskrivelse28;

    @FXML
    private Label kunstværkBeskrivelse29;

    @FXML
    private Label kunstværkBeskrivelse3;

    @FXML
    private Label kunstværkBeskrivelse30;

    @FXML
    private Label kunstværkBeskrivelse31;

    @FXML
    private Label kunstværkBeskrivelse32;

    @FXML
    private Label kunstværkBeskrivelse33;

    @FXML
    private Label kunstværkBeskrivelse34;

    @FXML
    private Label kunstværkBeskrivelse35;

    @FXML
    private Label kunstværkBeskrivelse36;

    @FXML
    private Label kunstværkBeskrivelse4;

    @FXML
    private Label kunstværkBeskrivelse5;

    @FXML
    private Label kunstværkBeskrivelse6;

    @FXML
    private Label kunstværkBeskrivelse7;

    @FXML
    private Label kunstværkBeskrivelse8;

    @FXML
    private Label kunstværkBeskrivelse9;

    @FXML
    private TextField searchField;

    // Skifter scenen til Admin Login
    @FXML
    void adminKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Login.fxml");
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
        }
    }

    // Skifter scenent il Farvoritter
    @FXML
    void favoritterKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Favoritter.fxml");
    }

    @FXML
    void filterAarstalNed(ActionEvent event) {

    }

    @FXML
    void filterAarstalOp(ActionEvent event) {

    }

    // Skifter scenen til Om Os
    @FXML
    void omOsKnap(MouseEvent event) throws  IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Om-Os.fxml");
    }

    // Skifter scenen til Om Samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Om-Samlingen.fxml");
    }

    // Skifter scenen til Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Temaer.fxml");
    }

    // Skifter scenen til Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Undervisning.fxml");
    }

    // Skifter scenen til Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }
}
