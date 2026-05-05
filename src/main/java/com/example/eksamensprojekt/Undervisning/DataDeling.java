package com.example.eksamensprojekt.Undervisning;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class DataDeling {

    public static ObservableList<Indskoling> indskolingList = FXCollections.observableArrayList(
            new Indskoling("At læse og tale billeder","/com/example/eksamensprojekt/undervisning/Undervisningsforløb - At læse og tale billeder.pdf"));

    public static ObservableList<Mellemtrin> mellemtrinList = FXCollections.observableArrayList();

    public static ObservableList<Udskoling> udskolingList = FXCollections.observableArrayList();

    public static ObservableList<Konfirmation> konfirmationList = FXCollections.observableArrayList();
}
