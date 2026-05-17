package com.example.eksamensprojekt.undervisning;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Klasse der bruges til at dele lister med undervisningsmaterialer mellem
// AdminUndervisningsController og UndervisningsController
public class DataDeling
{
    public static ObservableList<PdfItem> indskolingList = FXCollections.observableArrayList();

    public static ObservableList<PdfItem> mellemtrinList = FXCollections.observableArrayList();

    public static ObservableList<PdfItem> udskolingList = FXCollections.observableArrayList();

    public static ObservableList<PdfItem> konfirmationList = FXCollections.observableArrayList();
}
