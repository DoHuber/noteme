package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * HashMap: Schlüssel und ein Wert (Schlüssel = id)
 * Mapper merken sich welche Objekte schon geladen haben, bei z.B. findById
 * geht es schneller durch HashMap zu schauen ob Objekt schon geladen haben
 * @author Lisa
 *
 */
public abstract class DataMapper {
	
	 protected Connection connection;

	    protected DataMapper() throws ClassNotFoundException, SQLException
	    {
	    	new DBConnection();
	    	connection = DBConnection.getConnection();


	    }

	   
	}

