package com.example.eksamensprojekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManeger
{

    private int maxX = 1400;
    private int maxY = 800;

    // Så vi kan skifte scenen ved brug af On Mouse Clicked
    public void skiftSceneMouse (MouseEvent event, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
}
