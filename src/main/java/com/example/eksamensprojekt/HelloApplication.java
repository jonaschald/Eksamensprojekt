package com.example.eksamensprojekt;

import com.example.eksamensprojekt.database.DAOImplementation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        try {
            DAOImplementation.getInstance().initConnection();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunne ikke tilslutte til databasen.", ButtonType.CLOSE);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                Platform.exit();
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gui/Forside.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 800);
        stage.setTitle("Kunsthal Holmen");
        stage.setScene(scene);
        stage.show();

        // Lukker programmet pænt når man klikker på krydset
        stage.setOnCloseRequest((e) -> {
            Platform.exit();
            System.exit(0);
        });
    }
}