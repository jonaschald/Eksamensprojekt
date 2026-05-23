package com.example.eksamensprojekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class SceneManeger
{

    // Højen og bredden på vinduet
    private int maxY = 800;
    private int maxX = 1400;

    // Stack der gemmer tidligere FXML-sider, så brugeren kan gå tilbage til dem senere
    private static final Stack<String> historik = new Stack<>();

    // Metode til at skifte til en ny FXML-side ved brug af MouseEvent
    public void skiftSceneMouse(MouseEvent event, String fxml) throws IOException
    {
        // Finder det FXML som der skal åbnes
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        // Loader den nye FXML-side og gemmer den i et Parent objekt
        Parent root = loader.load();

        // Finder det vindue (Stage) programmet kører i
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Skifter det nuværende FXML-vindue til den nye FXML
        stage.setScene(new Scene(root, maxX, maxY));

        // Viser vinduet på skærmen
        stage.show();
    }

    // Metode til at skifte til en ny FXML-side ved brug af ActionEvent
    public void skiftSceneAction (ActionEvent event, String fxml) throws IOException
    {
        // Finder det FXML som der skal åbnes
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        // Loader den nye FXML-side og gemmer den i et Parent objekt
        Parent root = loader.load();

        // Finder det vindue (Stage) programmet kører i
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Skifter det nuværende FXML-vindue til den nye FXML
        stage.setScene(new Scene(root, maxX, maxY));

        // Viser vinduet på skærmen
        stage.show();
    }

    // Metode til at skifte til en ny FXML-side og gemme den tidligere FXML-side i historik
    public void skiftSceneTilbage (MouseEvent event, String denneFxml, String næsteFxml) throws IOException
    {
        // Gemmer den scene vi er på, så brugeren kan gå tilbage senere
        historik.push(denneFxml);

        // Finder det næste FXML der skal åbnes
        FXMLLoader loader = new FXMLLoader(getClass().getResource(næsteFxml));

        // Loader den nye FXML-side og gemmer den i et Parent objekt
        Parent root = loader.load();

        // Finder det vindue (Stage) programmet kører i
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Skifter det nuværende vindue til den nye FXML
        stage.setScene(new Scene(root, maxX, maxY));

        // Viser vinduet på skærmen
        stage.show();
    }

    // Metode til at gå tilbage til den tidligere FXML-side ved hjælp af historik Stack
    public void tilbage(MouseEvent event) throws IOException
    {
        // Tjekker om der findes en tidligere FXML-side i historikken
        if(!historik.isEmpty())
        {
            // Henter den seneste FXML-side fra historikken
            String tidligereFxml = historik.pop();

            // Finder det tidligere FXML-side der skal åbnes
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tidligereFxml));

            // Loader den tidligere FXML-side
            Parent root = loader.load();

            // Finder det vindue (Stage) programmet kører i
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Skifter det nuværende vindue til den tidligere FXML-side
            stage.setScene(new Scene(root, maxX, maxY));

            // Viser vinduet på skærmen
            stage.show();
        }
    }
}