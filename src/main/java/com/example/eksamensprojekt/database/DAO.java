package com.example.eksamensprojekt.database;

import com.example.eksamensprojekt.objekter.AdminLogin;
import com.example.eksamensprojekt.objekter.Kunstværk;
import com.example.eksamensprojekt.objekter.Tema;
import com.example.eksamensprojekt.objekter.Undervisningsmateriale;
import javafx.collections.ObservableList;

import java.util.concurrent.ExecutionException;

public interface DAO {
    // Kunstværker
    public boolean gemKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException;
    public void hentAlleKunstværker(ObservableList<Kunstværk> kunstværker)  throws ExecutionException, InterruptedException;
    public boolean sletKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException;
    public void opdaterKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException;
    public void søgKunstværk(String søgeTekst, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException;
    public void filtrerKunstværkerEfterÅrstal(int årstal, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException;
    public void hentKunstværkerEfterTema(int temaId, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException;

    // Temaer
    public boolean gemTema(Tema tema) throws ExecutionException, InterruptedException;
    public void hentAlleTemaer(ObservableList<Tema> temaer) throws ExecutionException, InterruptedException;
    public boolean sletTema(Tema tema) throws ExecutionException, InterruptedException;
    public void opdaterTema(Tema tema) throws ExecutionException, InterruptedException;

    // Undervisningsmaterialer
    public boolean gemUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException;
    public boolean sletUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException;
    public void hentUndervisningsmateriale(ObservableList<Undervisningsmateriale> undervisningsmaterialer) throws ExecutionException, InterruptedException;

    // Brugere/Login
    public boolean login(String username, String password) throws ExecutionException, InterruptedException;
    public boolean opretBruger(AdminLogin adminLogin) throws ExecutionException, InterruptedException;

    // Favoritter
    public void tilføjFavorit(Kunstværk kunstværk) throws ExecutionException, InterruptedException;
    public void fjernFavorit(Kunstværk kunstværk) throws ExecutionException, InterruptedException;
    public void hentFavoritter(ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException;
}