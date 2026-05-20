package com.example.eksamensprojekt.objekter;

public class Forside
{
    private int Id;

    private String titel_1;
    private String beskrivelse_1;
    private byte[] billede_1;

    private String titel_2;
    private String beskrivelse_2;
    private byte[] billede_2a;
    private byte[] billede_2b;

    public Forside (int id, String titel1, String beskrivelse1, byte[] billede1,
                    String titel2, String beskrivelse2, byte[] billede2a, byte[] billede2b) {
        Id = id;
        titel_1 = titel1;
        beskrivelse_1 = beskrivelse1;
        billede_1 = billede1;
        titel_2 = titel2;
        beskrivelse_2 = beskrivelse2;
        billede_2a = billede2a;
        billede_2b = billede2b;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitel_1() {
        return titel_1;
    }

    public void setTitel_1(String titel_1) {
        this.titel_1 = titel_1;
    }

    public String getBeskrivelse_1() {
        return beskrivelse_1;
    }

    public void setBeskrivelse_1(String beskrivelse_1) {
        this.beskrivelse_1 = beskrivelse_1;
    }

    public byte[] getBillede_1() {
        return billede_1;
    }

    public void setBillede_1(byte[] billede_1) {
        this.billede_1 = billede_1;
    }

    public String getTitel_2() {
        return titel_2;
    }

    public void setTitel_2(String titel_2) {
        this.titel_2 = titel_2;
    }

    public String getBeskrivelse_2() {
        return beskrivelse_2;
    }

    public void setBeskrivelse_2(String beskrivelse_2) {
        this.beskrivelse_2 = beskrivelse_2;
    }

    public byte[] getBillede_2a() {
        return billede_2a;
    }

    public void setBillede_2a(byte[] billede_2a) {
        this.billede_2a = billede_2a;
    }

    public byte[] getBillede_2b() {
        return billede_2b;
    }

    public void setBillede_2b(byte[] billede_2b) {
        this.billede_2b = billede_2b;
    }
}