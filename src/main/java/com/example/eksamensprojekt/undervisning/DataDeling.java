package com.example.eksamensprojekt.undervisning;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Klasse der bruges til at dele lister, tekst og billeder, mellem admin siden og bruger siden

public class DataDeling
{
    // AdminUndervisningsController og UndervisningsController
    public static ObservableList<PdfItem> indskolingList = FXCollections.observableArrayList();
    public static ObservableList<PdfItem> mellemtrinList = FXCollections.observableArrayList();
    public static ObservableList<PdfItem> udskolingList = FXCollections.observableArrayList();
    public static ObservableList<PdfItem> konfirmationList = FXCollections.observableArrayList();

    // AdminOmOsController og OmOsController


    //AdminOmSamlingenController og OmSamlingenController


    // AdminForsideController og ForsideController


    // AdminPopUpController og PopupController

}
