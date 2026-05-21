package com.example.eksamensprojekt;

import javafx.application.Platform;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AsyncTask {

    // Kører en opgave i baggrunden og returnerer resultatet til JavaFX-tråden
    public static <T> void run(
            Supplier<T> arbejde,            // Det arbejde der skal køres i baggrunden
            Consumer<T> påSucces,           // Hvad der skal ske når det lykkes
            Consumer<Exception> påFejl      // Hvad der skal ske hvis der opstår en fejl
    ) {
        // Opretter en ny tråd så UI ikke fryser
        Thread thread = new Thread(() -> {
            try {
                // Udfører det tunge arbejde
                T result = arbejde.get();

                // Sender resultatet tilbage
                Platform.runLater(() -> {
                    påSucces.accept(result);
                });
            } catch (Exception e) {
                // Håndter fejlen
                Platform.runLater(() -> {
                    if (påFejl != null) {
                        påFejl.accept(e);
                    } else {
                        // Hvis ingen fejl-håndtering er angivet, printes stack trace
                        e.printStackTrace();
                    }
                });
            }
        });

        // Sørger for at tråden ikke blokerer programmet ved luk
        thread.setDaemon(true);

        // Starter baggrundsarbejdet
        thread.start();
    }
}