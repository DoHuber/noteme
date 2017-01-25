package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstrakte Superklasse aller Mapper, enthält Methoden zur Verwaltung
 * der Datenbankverbindung.
 *
 * @author Lisa
 */
public abstract class DataMapper {

    protected Connection connection;

    /**
     * Holt eine aktive Datenbankverbindung für die Verwendung durch den Mapper
     *
     * @throws ClassNotFoundException wenn der Treiber nicht gefunden werden kann
     * @throws SQLException wenn z.B. die Datenbank offline, oder das Passwort falsch ist.
     */
    protected DataMapper() throws ClassNotFoundException, SQLException {

        connection = DBConnection.getConnection();

    }

    /**
     * Bei der Zerstörung eines Objektes durch den Garbage Collector wird
     * noch einmal die DB-Verbindung geschlossen, da sonst schnell die maximale Anzahl überschritten wird.
     *
     * @throws Throwable eventuell auftretende Fehler, hier sehr allgemein
     */
    @Override
    protected void finalize() throws Throwable{

        super.finalize();
        connection.close();

    }
}
