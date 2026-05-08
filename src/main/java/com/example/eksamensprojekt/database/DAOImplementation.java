package com.example.eksamensprojekt.database;

import com.example.eksamensprojekt.objektKlasser.Bruger;
import com.example.eksamensprojekt.objektKlasser.Kunstværk;
import com.example.eksamensprojekt.objektKlasser.Tema;
import com.example.eksamensprojekt.objektKlasser.Undervisningsmateriale;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.collections.ObservableList;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DAOImplementation implements DAO {

    private SQLServerDataSource kilde;
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public DAOImplementation() {
        kilde = new SQLServerDataSource();
        kilde.setDatabaseName("EJMM_2SEM_EKSAMEN_2026");
        kilde.setUser("CS2025a_s_2");
        kilde.setPassword("CS2025aS2#23");
        kilde.setPortNumber(1433);
        kilde.setServerName("10.176.111.34");
        kilde.setTrustServerCertificate(true);

        System.out.println("Database: " + kilde.getDatabaseName());
    }


    @Override
    public boolean gemKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void hentAlleKunstværker(ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException {

    }

    @Override
    public boolean sletKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void opdaterKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException {

    }

    @Override
    public void søgKunstværk(String søgeTekst, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException {

    }

    @Override
    public void filtrerKunstværkerEfterÅrstal(int årstal, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException {

    }

    @Override
    public void hentKunstværkerEfterTema(int temaId, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException {

    }

    @Override
    public boolean gemTema(Tema tema) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void hentAlleTemaer(ObservableList<Tema> temaer) throws ExecutionException, InterruptedException {

    }

    @Override
    public boolean sletTema(Tema tema) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void opdaterTema(Tema tema) throws ExecutionException, InterruptedException {

    }

    @Override
    public boolean gemUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void hentAlleUndervisningsmaterialer(ObservableList<Undervisningsmateriale> undervisningsmaterialer) throws ExecutionException, InterruptedException {

    }

    @Override
    public boolean sletUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void opdaterUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException {

    }

    @Override
    public boolean login(String username, String password) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public boolean opretBruger(Bruger bruger) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public void tilføjFavorit(int brugerID, int kunstværkID) throws ExecutionException, InterruptedException {

    }

    @Override
    public void fjernFavorit(int brugerID, int kunstværkID) throws ExecutionException, InterruptedException {

    }

    @Override
    public void hentFavoritter(int userID, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException {

    }
}
