package com.example.eksamensprojekt.undervisning;

public class Mellemtrin {
    private String name;

    public Mellemtrin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
