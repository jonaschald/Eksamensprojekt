package com.example.eksamensprojekt.controllers.admin;

import com.example.eksamensprojekt.SceneManeger;
import com.example.eksamensprojekt.undervisning.DataDeling;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AdminOmOsController {

    SceneManeger sceneManeger = new SceneManeger();

    public void initialize () {
        // Så man kan se det på bruger siden
        if (DataDeling.omOsTekst != null) { omOsTekst.setText(DataDeling.omOsTekst); }
        if (DataDeling.omOsTopBilled != null) { billedeTop.setImage(DataDeling.omOsTopBilled); }
        if (DataDeling.omOsMidtBilled != null) { billedeMidt.setImage(DataDeling.omOsMidtBilled); }
        if (DataDeling.omOsBundBilled != null) { billedeBund.setImage(DataDeling.omOsBundBilled); }
        adresseLabel.textProperty().bindBidirectional(DataDeling.omOsAdresse2());
        telefonLabel.textProperty().bindBidirectional(DataDeling.omOsTelefon2());
        emailLabel.textProperty().bindBidirectional(DataDeling.omOsEmail2());

        // Så man kan se det i tekstfelt på Admin siden
        adresseTextArea.textProperty().bindBidirectional(DataDeling.omOsAdresse2());
        telefonTextArea.textProperty().bindBidirectional(DataDeling.omOsTelefon2());
        emailTextArea.textProperty().bindBidirectional(DataDeling.omOsEmail2());
        if (DataDeling.omOsÅbningstider != null) { åbningstiderFelt.setText(DataDeling.omOsÅbningstider); }
    }

    @FXML
    private Label adresseLabel;
    @FXML
    private Label telefonLabel;
    @FXML
    private Label emailLabel;

    @FXML
    private TextArea adresseTextArea;
    @FXML
    private TextArea telefonTextArea;
    @FXML
    private TextArea emailTextArea;

    @FXML
    private ImageView billedeBund;
    @FXML
    private ImageView billedeMidt;
    @FXML
    private ImageView billedeTop;

    @FXML
    private TextArea omOsTekst;

    @FXML
    private TextArea åbningstiderFelt;

    @FXML
    void gemOmOsTekst(MouseEvent event) {
        DataDeling.omOsTekst = omOsTekst.getText();
    }

    @FXML
    void gemKontaktOplysninger(MouseEvent event) {
        DataDeling.setOmOsAdresse2(adresseTextArea.getText());
        DataDeling.setOmOsTelefon2(telefonTextArea.getText());
        DataDeling.setOmOsEmail2(emailTextArea.getText());
    }

    @FXML
    void redigerTopBillede(MouseEvent event) {
        DataDeling.omOsTopBilled = redigerBillede(billedeTop);
    }

    @FXML
    void redigerMidtBillede(MouseEvent event) {
        DataDeling.omOsMidtBilled = redigerBillede(billedeMidt);
    }

    @FXML
    void redigerBundBillede(MouseEvent event) {
        DataDeling.omOsBundBilled = redigerBillede(billedeBund);
    }

    @FXML
    void gemÅbningstider(MouseEvent event) {
        DataDeling.omOsÅbningstider = åbningstiderFelt.getText();
    }

    @FXML
    void besøgKunsthallensHjemmesideKnap(MouseEvent event) {

    }

    // Skifter scene til Admin Om samlingen
    @FXML
    void omSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Om-Samlingen.fxml");
    }

    // Skifter scenen til Admin Temaer
    @FXML
    void temaerKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Temaer.fxml");
    }

    // Skifter scenen til Admin Undervisning
    @FXML
    void undervisningKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/AdminUndervisning.fxml");
    }

    // Skifter scenen til Admin Samlingen
    @FXML
    void watanabeSamlingenKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/Admin-Watanabe-samlingen.fxml");
    }

    // Skifter scenen til Admin Startsiden
    @FXML
    void tilStartSide(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse (event, "/com/example/eksamensprojekt/admin/AdminForside.fxml");
    }

    // Skifter scene til Forsiden
    @FXML
    void logudKnap(MouseEvent event) throws IOException {
        sceneManeger.skiftSceneMouse(event, "/com/example/eksamensprojekt/gui/Forside.fxml");
    }

    private Image redigerBillede (ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Image Files",
                        "*.png", "*.jpg", "*.jpeg", "*.gif"));

        Stage stage = (Stage) imageView.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image (file.toURI().toString(),
                    500, 600, true, true);
            imageView.setImage(image);

            return image;
        }
        return null;
    }
}
