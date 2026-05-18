package com.example.eksamensprojekt.database;

import com.example.eksamensprojekt.objekter.*;
import javafx.collections.ObservableList;

import java.util.concurrent.ExecutionException;

// Interface klasse der beskriver hvilke database metoder DAOImplementation skal have
public interface DAO
{
    // Kunstværker
    public void hentAlleKunstværker(ObservableList<Kunstværk> kunstværker)  throws ExecutionException, InterruptedException;
    public void søgKunstværk(String søgeTekst, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException;
    public void filtrerKunstværkerEfterÅrstal(int årstal, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException;
    public void hentKunstværkerEfterTema(int temaId, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException;

    // Temaer
    public boolean gemTema(Tema tema) throws ExecutionException, InterruptedException;
    public void hentAlleTemaer(ObservableList<Tema> temaer) throws ExecutionException, InterruptedException;
    public boolean sletTema(Tema tema) throws ExecutionException, InterruptedException;
    public void opdaterTema(Tema tema) throws ExecutionException, InterruptedException;

    // Undervisningsmaterialer
    public void gemUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException;
    public void sletUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException;
    public void hentUndervisningsmateriale(ObservableList<Undervisningsmateriale> undervisningsmaterialer) throws ExecutionException, InterruptedException;

    // Brugere/Login
    public boolean login(String username, String password) throws ExecutionException, InterruptedException;
    public boolean opretBruger(AdminLogin adminLogin) throws ExecutionException, InterruptedException;

    // Favoritter
    public void tilføjFavorit(Kunstværk kunstværk) throws ExecutionException, InterruptedException;
    public void fjernFavorit(Kunstværk kunstværk) throws ExecutionException, InterruptedException;
    public void hentFavoritter(ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException;

    // Metoder til redigering af tekst og billeder på Samlingens oversigt side i Admin
    public boolean gemKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException;
    public boolean sletKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException;

    // Metoder til redigering af tekst og billeder i Om Samlingen Admin
    public void hentOmSamlingen(ObservableList<OmSamlingen> omSamlingen) throws ExecutionException, InterruptedException;
    public void opdaterOmSamlingen(OmSamlingen omSamlingen) throws ExecutionException, InterruptedException;

    // Metoder til redigering af tekst og billeder i Om Os Admin
    public void hentOmOs(ObservableList<OmOs> omOs) throws ExecutionException, InterruptedException;
    public void opdaterOmOs(OmOs omOs) throws ExecutionException, InterruptedException;

    // Metoder til redigering af tekst og billeder på Forsiden Admin
    // Skal laves database tabeller og metoder

    // Metoder til redigering af tekst og billeder på Pop-up siden Admin (det enkelte kunstværks nummer, titel osv.)
    // Skal laves database tabeller og metoder
    public void opdaterKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException;
}