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
    private int maxX = 1400;
    private int maxY = 800;
    private static final Stack<String> historie = new Stack<>();

    // Så vi kan skifte scenen ved brug af On Mouse Clicked
    public void skiftSceneMouse(MouseEvent event, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, maxX, maxY));
        stage.show();
    }

    // Så vi kan skifte scenen ved brug af Action Event
    public void skiftSceneAction (ActionEvent event, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, maxX, maxY));
        stage.show();
    }

    // Skifter scene med historie til brug ad tilbageKnap
    public void skiftSceneTilbage (MouseEvent event, String denneFxml, String næsteFxml) throws IOException {
        // gemmer den scene vi er på og åbner den der klikkes på
        historie.push(denneFxml);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(næsteFxml));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, maxX, maxY));
        stage.show();
    }

    // Tilbage til tidligere FXML scene
    public void tilbage(MouseEvent event) throws IOException {
        if(!historie.isEmpty()){
            String tidligereFxml = historie.pop();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tidligereFxml));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, maxX, maxY));
            stage.show();
        }
    }
}