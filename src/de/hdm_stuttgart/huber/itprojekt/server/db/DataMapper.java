package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * HashMap: Schlüssel und ein Wert (Schlüssel = id)
 * Mapper merken sich welche Objekte schon geladen haben, bei z.B. findById
 * geht es schneller durch HashMap zu schauen ob Objekt schon geladen haben
 *
 * @author Lisa
 */
public abstract class DataMapper {

    protected Map<Integer, Object> loadedObjects = new HashMap<>();
    protected Connection connection;

    protected DataMapper() throws ClassNotFoundException, SQLException {

        connection = DBConnection.getConnection();

    }

    protected <T> boolean isObjectLoaded(long id, Class<T> classToCheck) {

        Object o = loadedObjects.get(id);
        return o != null && classToCheck.isAssignableFrom(o.getClass());

    }

    @Override
    protected void finalize() throws Throwable {

        super.finalize();
        connection.close();

    }
}

