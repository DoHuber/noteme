package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * HashMap: Schlüssel und ein Wert (Schlüssel = id)
 * Mapper merken sich welche Objekte schon geladen haben, bei z.B. findById
 * geht es schneller durch HashMap zu schauen ob Objekt schon geladen haben
 * @author Lisa
 *
 */
public abstract class DataMapper {
	
	

	protected Map<Long, Object> loadedObjects =new HashMap<Long, Object>();

	protected void addToHashMap(long id,Object o){
		loadedObjects.put(id, o);
	}
	
	protected boolean isObjectLoaded(long id){
		boolean x =false;
		
		for(long idKey : loadedObjects.keySet()){
			if(idKey == id){
				x = true;
			}
			else{
				x= false;	
			}
		}
		return x;
	}
	
	
	 protected Connection connection;

	    protected DataMapper() throws ClassNotFoundException, SQLException
	    {
	    	new DBConnection();
	    	connection = DBConnection.getConnection();


	    }

	   
	}

