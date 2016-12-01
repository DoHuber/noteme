package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.*;
import java.util.Vector;
import de.hdm_stuttgart.huber.itprojekt.server.db.DBConnection;
import de.hdm_stuttgart.huber.itprojekt.server.db.DataMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Source;

public class SourceMapper extends DataMapper {
	
	// Statisches Attribut, welches den Singleton-SourceMapper enthält
	private static SourceMapper sourceMapper = null;
	
	// Konstruktor (protected, um unauthorisiertes Instanziieren der Klasse zu verhindern)
	protected SourceMapper() throws ClassNotFoundException, SQLException {
		super();
	}

	// Öffentliche statische Methode, um den Singleton-SourceMapper zu erhalten
	public static SourceMapper getSourceMapper() throws ClassNotFoundException, SQLException {
		if (sourceMapper == null ) {
			sourceMapper = new SourceMapper();
		}
		return sourceMapper;
	}
	
	//create Methode
	public Source create (Source source) throws ClassNotFoundException, SQLException{
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO source(Url)" 
			+ "VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1,  source.getURL().toString());
			
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				return findById(rs.getInt(1));
			}	// else {
				// throw new SQLException("Sorry");
				// }
		}	catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		return source;
		
	}

		//findById Methode:
		public Source findById(long id) throws ClassNotFoundException, SQLException{
			Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT Source FROM Notebook.Note WHERE Id = ?");
			stmt.setLong(1, id);
			
			//Ergebnis holen
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				return new Source(results.getURL("URL"));
			}
		}	catch (SQLException sqlExp) {
				sqlExp.printStackTrace();
				return null;
		}
		return null;
	}
		
	
	//save-Methode
	public Source save(Source source) throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE Source SET url=? WHERE urlId=?");

			stmt.setString(1,  source.getURL().toString());
			stmt.executeUpdate();
		}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		//aktualisierte Berechtigung zurückgeben
		return source;
	}
	
	
	//delete-Methode
	public void delete(Source source) throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE FROM Source WHERE Id=?");
			stmt.setLong(1,  source.getSourceId());
			stmt.executeUpdate();
		}
			catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
	}
	
	public Vector<Source> getAllSource() throws Exception {
		Vector <Source> result = new Vector<Source>();
		
		Connection con = DBConnection.getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT Id FROM Source");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			result.add(this.findById(rs.getLong("Id")));
		}
	return result;
	}
	
	
}