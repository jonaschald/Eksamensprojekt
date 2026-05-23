package com.example.eksamensprojekt.database;

import com.example.eksamensprojekt.objekter.*;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

// Klasse der håndtere alt kommunikation med databasen
public class DAOImplementation implements DAO {
    // DataSource der bruges til at oprette forbindelse til databasen
    private SQLServerDataSource kilde;

    // Sætter den op til at bruge 10 tråde som kan lave database opgaver i baggrunden når programmet kører
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    // Konstruktør der opretter forbindelse til SQL databasen
    public DAOImplementation() {
        kilde = new SQLServerDataSource();
        kilde.setDatabaseName("EJMM_2SEM_EKSAMEN_2026");
        kilde.setUser("CS2025a_s_2");
        kilde.setPassword("CS2025aS2#23");
        kilde.setPortNumber(1433);
        kilde.setServerName("10.176.111.34");
        kilde.setTrustServerCertificate(true);
    }

    // Metode der henter alle kunstværker fra databasen og gemmer dem i listen kunstværker
    @Override
    public void hentAlleKunstværker(ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException
    {
        // Opretter en Runnable, så denne metode kører i en tråd
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                // Opretter forbindelse til databasen
                try (Connection forbindelse = kilde.getConnection())
                {
                    // Laver PreparedStatement for at beskytte koden mod SQL-injektion
                    PreparedStatement preparedStatement;

                    // SQL-statement der henter alle kunstværker fra databasen
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM Art_pieces");

                    // Kører SQL-statementet
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Går rækkerne fra databasen igennem og henter data fra hver række
                    while (resultSet.next())
                    {
                        String id = resultSet.getString("ID");
                        String serieNummer = resultSet.getString("Serial_Number");
                        String titel = resultSet.getString("Title");
                        int årstal = resultSet.getInt("Year");
                        String kunstner = resultSet.getString("Artist");
                        String størrelseMedRamme = resultSet.getString("Size_With_Frame");
                        String størrelseUdenRamme = resultSet.getString("Size_Without_Frame");
                        String beskrivelse = resultSet.getString("Description");
                        byte[] billedeData = resultSet.getBytes("Image_Data");
                        int temaId = resultSet.getInt("ThemeID");
                        boolean favorit = resultSet.getBoolean("Favorite");

                        // Opretter et Kunstværk objekt med data fra databasen
                        Kunstværk kunstværk = new Kunstværk(id, serieNummer, titel, kunstner, årstal,
                                størrelseMedRamme, størrelseUdenRamme, beskrivelse, billedeData, temaId, favorit);

                        // Tilføjer kunstværket til listen kunstværker
                        kunstværker.add(kunstværk);
                    }

                } catch (SQLException e) {
                    // Hvis noget går galt, så udskrives fejlen i konsollen
                    System.out.println("Kunne ikke hente kunstværker fra databasen");
                    e.printStackTrace();
                }
            }
        };

        // Starter Runnable i executor-tråden
        Future future = executor.submit(runnable);

        // Venter på at tråden er færdig
        future.get();
    }

    @Override
    public boolean gemKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException {
        AtomicBoolean resultat = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;

                    preparedStatement = forbindelse.prepareStatement("INSERT INTO Art_Pieces " +
                            "(ID, Serial_Number, Title, Year, Artist, Size_With_Frame, Size_Without_Frame, Description, Image_Data, ThemeID, Favorite) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                    preparedStatement.setString(1, kunstværk.getId());
                    preparedStatement.setString(2, kunstværk.getSerieNummer());
                    preparedStatement.setString(3, kunstværk.getTitel());
                    preparedStatement.setInt(4, kunstværk.getÅrstal());
                    preparedStatement.setString(5, kunstværk.getKunstner());
                    preparedStatement.setString(6, kunstværk.getStørrelseMedRamme());
                    preparedStatement.setString(7, kunstværk.getStørrelseUdenRamme());
                    preparedStatement.setString(8, kunstværk.getBeskrivelse());
                    preparedStatement.setBytes(9, kunstværk.getBilledeData());
                    preparedStatement.setInt(10, kunstværk.getTemaId());
                    preparedStatement.setBoolean(11, kunstværk.isFavorit());

                    preparedStatement.executeUpdate();

                    resultat.set(true);

                } catch (SQLException e) {
                    System.out.println("Kunne ikke gemme kunstværk i databasen");
                    resultat.set(false);
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();

        return resultat.get();
    }

    @Override
    public void opdaterKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("UPDATE Art_Pieces SET " + "Serial_Number = ?, " +
                            "Title = ?, " + "Year = ?, " + "Artist = ?, " + "Size_With_Frame = ?, " + "Size_Without_Frame = ?, " +
                            "Description = ?, " + "Image_Data = ?, " + "ThemeID = ?, " + "Favorite = ? " + "WHERE ID = ?");

                    preparedStatement.setString(1, kunstværk.getSerieNummer());
                    preparedStatement.setString(2, kunstværk.getTitel());
                    preparedStatement.setInt(3, kunstværk.getÅrstal());
                    preparedStatement.setString(4, kunstværk.getKunstner());
                    preparedStatement.setString(5, kunstværk.getStørrelseMedRamme());
                    preparedStatement.setString(6, kunstværk.getStørrelseUdenRamme());
                    preparedStatement.setString(7, kunstværk.getBeskrivelse());
                    preparedStatement.setBytes(8, kunstværk.getBilledeData());
                    preparedStatement.setInt(9, kunstværk.getTemaId());
                    preparedStatement.setBoolean(10, kunstværk.isFavorit());
                    preparedStatement.setString(11, kunstværk.getId());

                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Kunne ikke opdatere kunstværket i databasen");
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    @Override
    public boolean sletKunstværk(Kunstværk kunstværk) throws ExecutionException, InterruptedException {
        AtomicBoolean resultat = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("DELETE FROM Art_Pieces WHERE ID = ?");
                    preparedStatement.setString(1, kunstværk.getId());
                    preparedStatement.executeUpdate();

                    resultat.set(true);

                } catch (SQLException e) {
                    System.out.println("Kunne ikke slette kunstværket i databasen");
                    e.printStackTrace();
                    resultat.set(false);
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();

        return resultat.get();
    }

    @Override
    public boolean gemTema(Tema tema) throws ExecutionException, InterruptedException {
        AtomicBoolean resultat = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("INSERT INTO Themes (Theme_Name) VALUES (?)");
                    preparedStatement.setString(1, tema.getNavn());
                    preparedStatement.executeUpdate();

                    resultat.set(true);

                } catch (SQLException e) {
                    System.out.println("Kunne ikke gamme temaet i databasen");
                    resultat.set(false);
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();

        return resultat.get();
    }

    // Metode der henter alle temaer fra databasen og gemmer dem i listen temaer
    @Override
    public void hentAlleTemaer(ObservableList<Tema> temaer) throws ExecutionException, InterruptedException
    {
        // Opretter en Runnable, så denne metode kører i en tråd
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                // Opretter forbindelse til databasen
                try (Connection forbindelse = kilde.getConnection())
                {
                    // Laver PreparedStatement for at beskytte koden mod SQL-injektion
                    PreparedStatement preparedStatement;

                    // SQL-statement der henter alle Temaer fra databasen
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM Themes");

                    // Kører SQL-statementet
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Går rækkerne fra databasen igennem og henter data fra hver række
                    while (resultSet.next())
                    {
                        int id = resultSet.getInt("ID");
                        String temaNavn = resultSet.getString("Theme_Name");

                        // Opretter et Tema objekt med data fra databasen
                        Tema tema = new Tema(id, temaNavn);

                        // Tilføjer temaet til listen temaer
                        temaer.add(tema);
                    }
                } catch (SQLException e) {
                    // Hvis noget går galt, så udskrives fejlen i konsollen
                    System.out.println("Kunne ikke hente temaer fra databasen");
                    e.printStackTrace();
                }
            }
        };

        // Starter Runnable i executor-tråden
        Future future = executor.submit(runnable);

        // Venter på at tråden er færdig
        future.get();
    }

    @Override
    public boolean sletTema(Tema tema) throws ExecutionException, InterruptedException {

        AtomicBoolean resultat = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("DELETE FROM Themes WHERE ID = ?");
                    preparedStatement.setInt(1, tema.getId());
                    preparedStatement.executeUpdate();

                    resultat.set(true);

                } catch (SQLException e) {
                    System.out.println("Kunne ikke slette temaet i databasen");
                    resultat.set(false);
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();

        return resultat.get();
    }

    @Override
    public void opdaterTema(Tema tema) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("UPDATE Themes SET " + "Theme_Name = ? " + "WHERE ID = ?");
                    preparedStatement.setString(1, tema.getNavn());
                    preparedStatement.setInt(2, tema.getId());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Opdatering af tema lykkes ikke i databasen");
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    // Metode der henter alle kunstværker der tilhører et tema fra databasen og gemmer dem i listen kunstværker
    @Override
    public void hentKunstværkerEfterTema(int temaId, ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException
    {
        // Opretter en Runnable, så denne metode kører i en tråd
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                // Opretter forbindelse til databasen
                try (Connection forbindelse = kilde.getConnection())
                {
                    // Laver PreparedStatement for at beskytte koden mod SQL-injektion
                    PreparedStatement preparedStatement;

                    // SQL-statement der henter alle kunstværker med et bestemt temaID fra databasen
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM Art_Pieces WHERE ThemeID = ?");
                    preparedStatement.setInt(1, temaId);

                    // Kører SQL-statementet
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Går rækkerne fra databasen igennem og henter data fra hver række
                    while (resultSet.next())
                    {
                        String id = resultSet.getString("ID");
                        String serieNummer = resultSet.getString("Serial_Number");
                        String titel = resultSet.getString("Title");
                        int årstal = resultSet.getInt("Year");
                        String kunstner = resultSet.getString("Artist");
                        String størrelseMedRamme = resultSet.getString("Size_With_Frame");
                        String størrelseUdenRamme = resultSet.getString("Size_Without_Frame");
                        String beskrivelse = resultSet.getString("Description");
                        byte[] billedeData = resultSet.getBytes("Image_Data");
                        int temaID = resultSet.getInt("ThemeID");
                        boolean favorit = resultSet.getBoolean("Favorite");

                        // Opretter et Kunstværk objekt med data fra databasen
                        Kunstværk kunstværk = new Kunstværk(id, serieNummer, titel, kunstner, årstal,
                                størrelseMedRamme, størrelseUdenRamme, beskrivelse, billedeData, temaID, favorit);

                        // Tilføjer kunstværket til listen kunstværker
                        kunstværker.add(kunstværk);
                    }
                } catch (SQLException e) {
                    // Hvis noget går galt, så udskrives fejlen i konsollen
                    System.out.println("Kunne ikke hente temaer fra databasen");
                    e.printStackTrace();
                }
            }
        };

        // Starter Runnable i executor-tråden
        Future future = executor.submit(runnable);

        // Venter på at tråden er færdig
        future.get();
    }

    @Override
    public void tilføjFavorit(Kunstværk kunstværk) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("UPDATE Art_Pieces SET Favorite = ? WHERE ID = ?");
                    preparedStatement.setBoolean(1, true);
                    preparedStatement.setString(2, kunstværk.getId());
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Kunne ikke tilføje kunstværket som favorit i databasen");
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    @Override
    public void fjernFavorit(Kunstværk kunstværk) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("UPDATE Art_Pieces SET Favorite = ? WHERE ID = ?");
                    preparedStatement.setBoolean(1, false);
                    preparedStatement.setString(2, kunstværk.getId());
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Kunne ikke fjerne kunstværket som favorit i databasen");
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    // Metode der henter alle favorit-kunstværker fra databasen og gemmer dem i listen kunstværker
    @Override
    public void hentFavoritter(ObservableList<Kunstværk> kunstværker) throws ExecutionException, InterruptedException
    {
        // Opretter en Runnable, så denne metode kører i en tråd
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                // Opretter forbindelse til databasen
                try (Connection forbindelse = kilde.getConnection())
                {
                    // Laver PreparedStatement for at beskytte koden mod SQL-injektion
                    PreparedStatement preparedStatement;

                    // SQL-statement der henter alle favorit-kunstværker fra databasen
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM Art_Pieces WHERE Favorite = 1");

                    // Kører SQL-statementet
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Går rækkerne fra databasen igennem og henter data fra hver række
                    while (resultSet.next())
                    {
                        String id = resultSet.getString("ID");
                        String serieNummer = resultSet.getString("Serial_Number");
                        String titel = resultSet.getString("Title");
                        int årstal = resultSet.getInt("Year");
                        String kunstner = resultSet.getString("Artist");
                        String størrelseMedRamme = resultSet.getString("Size_With_Frame");
                        String størrelseUdenRamme = resultSet.getString("Size_Without_Frame");
                        String beskrivelse = resultSet.getString("Description");
                        byte[] billedeData = resultSet.getBytes("Image_Data");
                        int temaId = resultSet.getInt("ThemeID");
                        boolean favorit = resultSet.getBoolean("Favorite");

                        // Opretter et Kunstværk objekt med data fra databasen
                        Kunstværk kunstværk = new Kunstværk(id, serieNummer, titel, kunstner, årstal,
                                størrelseMedRamme, størrelseUdenRamme, beskrivelse, billedeData, temaId, favorit);

                        // Tilføjer kunstværket til listen kunstværker
                        kunstværker.add(kunstværk);
                    }

                } catch (SQLException e) {
                    // Hvis noget går galt, så udskrives fejlen i konsollen
                    System.out.println("Kunne ikke hente favoritter fra databasen");
                    e.printStackTrace();
                }
            }
        };

        // Starter Runnable i executor-tråden
        Future future = executor.submit(runnable);

        // Venter på at tråden er færdig
        future.get();
    }

    @Override
    public void gemUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException {
        AtomicBoolean resultat = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("INSERT INTO Teaching_Materials " +
                            "(Title, PDF_Data, Target_Group_ID) " + "VALUES (?, ?, ?)");

                    FileInputStream inputStream = new FileInputStream(undervisningsmateriale.getPdf());


                    preparedStatement.setString(1, undervisningsmateriale.getTitle());
                    preparedStatement.setBinaryStream(2, inputStream, undervisningsmateriale.getPdf().length());
                    preparedStatement.setInt(3, undervisningsmateriale.getMålgruppeId());
                    preparedStatement.executeUpdate();

                    resultat.set(true);

                } catch (SQLException e) {
                    System.out.println("Kunne ikke gemme undervisningsmaterialet i databasen");
                    resultat.set(false);
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();

        resultat.get();
    }

    @Override
    public void sletUndervisningsmateriale(Undervisningsmateriale undervisningsmateriale) throws ExecutionException, InterruptedException {
        AtomicBoolean resultat = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("DELETE FROM Teaching_Materials WHERE ID = ?");
                    preparedStatement.setInt(1, undervisningsmateriale.getId());
                    preparedStatement.executeUpdate();

                    resultat.set(true);

                } catch (SQLException e) {
                    System.out.println("Kunne ikke slette undervisningsmaterialet i databasen");
                    resultat.set(false);
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();

        resultat.get();
    }

    // Metode der henter alle undervisningsmaterialer fra databasen og gemmer dem i listen undervisningsmaterialer
    @Override
    public void hentUndervisningsmateriale(ObservableList<Undervisningsmateriale> undervisningsmaterialer) throws ExecutionException, InterruptedException
    {
        // Opretter en Runnable, så denne metode kører i en tråd
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                // Opretter forbindelse til databasen
                try (Connection forbindelse = kilde.getConnection())
                {
                    // Laver PreparedStatement for at beskytte koden mod SQL-injektion
                    PreparedStatement preparedStatement;

                    // SQL-statement der henter alle undervisningsmaterialer fra databasen
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM Teaching_Materials");

                    // Kører SQL-statementet
                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Går rækkerne fra databasen igennem og henter data fra hver række
                    while (resultSet.next())
                    {
                        int id = resultSet.getInt("ID");
                        String title = resultSet.getString("Title");
                        byte[] pdfData = resultSet.getBytes("PDF_Data");
                        int målgruppeId = resultSet.getInt("Target_Group_ID");

                        File pdfFile = File.createTempFile("pdf_", ".pdf");
                        FileOutputStream outputStream = new FileOutputStream(pdfFile);
                        outputStream.write(pdfData);
                        outputStream.close();

                        // Opretter et Undervisningsmateriale objekt med data fra databasen
                        Undervisningsmateriale undervisningsmateriale = new Undervisningsmateriale(id, title, pdfFile, målgruppeId);

                        // Tilføjer undervisningsmaterialet til listen undervisningsmaterialer
                        undervisningsmaterialer.add(undervisningsmateriale);
                    }
                } catch (SQLException e |) {
                    System.out.println("Kunne ikke hente undervisningsmaterialer fra databasen");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }


    @Override
    public void hentForside(ObservableList<Forside> forside) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM Front_Page");

                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt("ID");
                        String titel1 = resultSet.getString("Title1");
                        String beskrivelse1 = resultSet.getString("Description1");
                        byte[] billede1 = resultSet.getBytes("Image1");
                        String titel2 = resultSet.getString("Title2");
                        String beskrivelse2 = resultSet.getString("Description2");
                        byte[] billede2a = resultSet.getBytes("Image2a");
                        byte[] billede2b = resultSet.getBytes("Image2b");

                        Forside forsideObjekt = new Forside(id, titel1, beskrivelse1, billede1, titel2, beskrivelse2, billede2a, billede2b);

                        forside.add(forsideObjekt);
                    }
                } catch (SQLException e) {
                    System.out.println("Kunne ikke hente Forsiden fra Databasen");
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    @Override
    public void opdaterForside(Forside forside) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("UPDATE Front_Page SET Title1 = ?, " +
                            "Description1 = ?, Image1 = ?, Title2 = ?, Description2 = ?, Image2a = ?, Image2b = ? WHERE ID = ?");

                    preparedStatement.setString(1, forside.getTitel_1());
                    preparedStatement.setString(2, forside.getBeskrivelse_1());
                    preparedStatement.setBytes(3, forside.getBillede_1());
                    preparedStatement.setString(4, forside.getTitel_2());
                    preparedStatement.setString(5, forside.getBeskrivelse_2());
                    preparedStatement.setBytes(6, forside.getBillede_2a());
                    preparedStatement.setBytes(7, forside.getBillede_2b());
                    preparedStatement.setInt(8, forside.getId());
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Kunne ikke opdatere Forsiden i Databasen");
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();

    }

    @Override
    public void hentOmSamlingen(ObservableList<OmSamlingen> omSamlingen) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM About_Collection");

                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt("ID");
                        String titel = resultSet.getString("Title");
                        String beskrivelse = resultSet.getString("Description");
                        byte[] billede1 = resultSet.getBytes("Image1");
                        byte[] billede2 = resultSet.getBytes("Image2");

                        OmSamlingen omSamlingenObjekt = new OmSamlingen(id, titel, beskrivelse, billede1, billede2);

                        omSamlingen.add(omSamlingenObjekt);
                    }
                } catch (SQLException e) {
                    System.out.println("Kunne ikke hente Om Samlingen fra Databasen");
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    @Override
    public void opdaterOmSamlingen(OmSamlingen omSamlingen) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("UPDATE About_Collection SET Title = ?, " +
                            "Description = ?, Image1 = ?, Image2 = ? WHERE ID = ?");

                    preparedStatement.setString(1, omSamlingen.getTitle());
                    preparedStatement.setString(2, omSamlingen.getBeskrivelse());
                    preparedStatement.setBytes(3, omSamlingen.getImage1());
                    preparedStatement.setBytes(4, omSamlingen.getImage2());
                    preparedStatement.setInt(5, omSamlingen.getId());
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Kunne ikke opdatere Om Samlingen i Databasen");
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    @Override
    public void hentOmOs(ObservableList<OmOs> omOs) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("SELECT * FROM About_Us");

                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt("ID");
                        String titel = resultSet.getString("Title");
                        String beskrivelse = resultSet.getString("Description");
                        String adresse = resultSet.getString("Address");
                        String telefonnummer = resultSet.getString("Phone");
                        String email = resultSet.getString("Email");
                        String åbningstider = resultSet.getString("OpeningHours");
                        byte[] billede1 = resultSet.getBytes("Image1");
                        byte[] billede2 = resultSet.getBytes("Image2");
                        byte[] billede3 = resultSet.getBytes("Image3");

                        OmOs omOsObjekt = new OmOs(id, titel, beskrivelse, adresse,
                                telefonnummer, email, åbningstider, billede1, billede2, billede3);

                        omOs.add(omOsObjekt);
                    }
                } catch (SQLException e) {
                    System.out.println("Kunne ikke hente Om Os fra Databasen");
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    @Override
    public void opdaterOmOs(OmOs omOs) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (Connection forbindelse = kilde.getConnection()) {
                    PreparedStatement preparedStatement;
                    preparedStatement = forbindelse.prepareStatement("UPDATE About_Us SET Title = ?, " +
                            "Description = ?, Address = ?, Phone = ?, Email = ?, Image1 = ?, Image2 = ?, " +
                            "Image3 = ?, OpeningHours = ? WHERE ID = ?");

                    preparedStatement.setString(1, omOs.getTitel());
                    preparedStatement.setString(2, omOs.getBeskrivelse());
                    preparedStatement.setString(3, omOs.getAdresse());
                    preparedStatement.setString(4, omOs.getTelefonnummer());
                    preparedStatement.setString(5, omOs.getEmail());
                    preparedStatement.setBytes(6, omOs.getImage1());
                    preparedStatement.setBytes(7, omOs.getImage2());
                    preparedStatement.setBytes(8, omOs.getImage3());
                    preparedStatement.setString(9, omOs.getÅbningstider());
                    preparedStatement.setInt(10, omOs.getId());
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Kunne ikke opdatere Om Os i Databasen");
                    e.printStackTrace();
                }
            }
        };
        Future future = executor.submit(runnable);
        future.get();
    }

    @Override
    public boolean login(String username, String password) throws ExecutionException, InterruptedException {
        return false;
    }

    @Override
    public boolean opretBruger(AdminLogin adminLogin) throws ExecutionException, InterruptedException {
        return false;
    }
}