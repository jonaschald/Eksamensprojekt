package com.example.eksamensprojekt.objekter;

public class Målgruppe
{
    private int id;
    private String type;

    public Målgruppe(int id, String type)
    {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    // Metode til at konvertere Id mellem Java og databasen
    // Finder målgruppe-ID (database) ud fra checkboxens ID (Java)
    public static int convertToId(String checkboxId) {
        switch (checkboxId) {
            case "indskolingCheck":
                return 1;

            case "mellemtrinCheck":
                return 2;

            case "udskolingCheck":
                return 3;

            case "konfirmationCheck":
                return 4;

            default:
                return 0;
        }
    }
}
