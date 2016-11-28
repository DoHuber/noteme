package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.*;
import java.util.Vector;
import de.hdm_stuttgart.huber.itprojekt.server.db.DBConnection;
import de.hdm_stuttgart.huber.itprojekt.server.db.DataMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteUser;


public class NoteUserMapper extends DataMapper {
	
	//Statisches Attribut, welches den Singleton-NoteUserMapper enthält
	private static NoteUserMapper noteUserMapper = null;
		
	//Konstruktor (protected, um unauthorisiertes Instanziieren der Klasse zu verhindern)
	protected NoteUserMapper() throws ClassNotFoundException, SQLException {
		super();
	}

	//Öffentliche statische Methode, um den Singleton-NoteUserMapper zu erhalten
	public static NoteUserMapper getNoteUserMapper() throws ClassNotFoundException, SQLException{
		if (noteUserMapper == null) {
			noteUserMapper = new NoteUserMapper();
		}
		return noteUserMapper;
	}
	
	//create Methode
	public NoteUser create(NoteUser noteUser) throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO noteUser(FirstName, UserName, SurName, Email, GoogleId) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
					
			stmt.setString(1,  noteUser.getFirstName());
			stmt.setString(2,  noteUser.getUserName());
			stmt.setString(3,  noteUser.getSurName());
			stmt.setString(4,  noteUser.getEmail());
			stmt.setString(5,  noteUser.getGoogleId());
			
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				return findById(rs.getInt(1));
			} 	// else {
				// throw new SQLException("Sorry Bro");
				// }
		} 	catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		return noteUser;
				
	}

		//findById Methode:
		// bestimmte Notiz wird anhand der eindeutigen ID gesucht und zurückgegeben
		// long wegen DomainObject
		public NoteUser findById(long id) throws ClassNotFoundException, SQLException{
			Connection con = DBConnection.getConnection();
			
		try {
			PreparedStatement stmt = con.prepareStatement(" SELECT * FROM NoteUser WHERE NoteUserId = ?");
			stmt.setLong(1,  id);
			
			//Ergebnis holen
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				return new NoteUser(results.getString("firstName"), 
									results.getString("userName"), 
									results.getString("surName"), 
									results.getString("email"), 
									results.getString("googleId"));
			} 	// else {
				// throw new SQLException("Exception");
				// } 
			
		}	catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		return null;
	}
	
		
		//save-Methode: NoteUser-Objekt wird wiederholt in die DB geschrieben
		public NoteUser save(NoteUser noteUser) throws ClassNotFoundException, SQLException {
			Connection 	con = DBConnection.getConnection();
			
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE NoteUser SET firstName=?, userName=?, surName=?, email=?, googleId=? WHERE noteUserId=?");
			
			stmt.setString(1,  noteUser.getFirstName());
			stmt.setString(2,  noteUser.getUserName());
			stmt.setString(3,  noteUser.getSurName());
			stmt.setString(4,  noteUser.getEmail());
			stmt.setString(5,  noteUser.getGoogleId());
			
			stmt.executeUpdate();
			
		} 	catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		//aktualisierten NoteUser zurückgeben
		return noteUser;
		
	}
		
		
	public void delete(NoteUser noteUser) throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();		
		
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE FROM NoteUser WHERE NoteUserId = ?");
			stmt.setLong(1, noteUser.getNoteUserId());
			stmt.executeUpdate();
			
		} 	catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
	}
	
	public Vector<NoteUser> getAllNoteUser() throws Exception {
		Vector <NoteUser> result = new Vector<NoteUser>();
		
		Connection con = DBConnection.getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT NoteUserId FROM NoteUser");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			result.add(this.findById(rs.getLong("NoteUserId")));
		}
	return result;
	}
	
	
}
