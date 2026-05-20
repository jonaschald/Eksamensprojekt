package com.example.eksamensprojekt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException
    {
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