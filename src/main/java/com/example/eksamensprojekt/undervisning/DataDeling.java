package com.example.eksamensprojekt.undervisning;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

// Klasse der bruges til at dele lister, tekst og billeder, mellem admin siden og bruger siden

public class DataDeling
{
    // AdminUndervisningsController og UndervisningsController
    public static ObservableList<PdfItem> indskolingList = FXCollections.observableArrayList();
    public static ObservableList<PdfItem> mellemtrinList = FXCollections.observableArrayList();
    public static ObservableList<PdfItem> udskolingList = FXCollections.observableArrayList();
    public static ObservableList<PdfItem> konfirmationList = FXCollections.observableArrayList();

    // AdminOmOsController og OmOsController
    public static String omOsTekst;
    public static Image omOsTopBilled;
    public static Image omOsMidtBilled;
    public static Image omOsBundBilled;
    public static String omOsÅbningstider;

        // Kontaktoplysninger til at kunne vise dem to steder på Om Os siden
    private static StringProperty omOsAdresse2 = new SimpleStringProperty("");
    private static StringProperty omOsTelefon2 = new SimpleStringProperty("");
    private static StringProperty omOsEmail2 = new SimpleStringProperty("");
    public static StringProperty omOsAdresse2() { return omOsAdresse2; }
    public static StringProperty omOsTelefon2() { return omOsTelefon2; }
    public static  StringProperty omOsEmail2() { return omOsEmail2; }
    public static void setOmOsAdresse2(String value) {omOsAdresse2.set(value); }
    public static void setOmOsTelefon2(String value) {omOsTelefon2.set(value); }
    public static void setOmOsEmail2(String value) {omOsEmail2.set(value); }

    //AdminOmSamlingenController og OmSamlingenController


    // AdminForsideController og ForsideController


    // AdminPopUpController og PopupController

}
