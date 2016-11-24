package de.hdm_stuttgart.huber.itprojekt.server;

import java.sql.*;
//import java.util.ArrayList;

import de.hdm_stuttgart.huber.itprojekt.server.db.DBConnection;
import de.hdm_stuttgart.huber.itprojekt.server.db.DataMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteUser;


public class NoteUserMapper extends DataMapper {
	
	//Konstruktor
	protected NoteUserMapper() throws ClassNotFoundException, SQLException {
		super();
	}

private static NoteUserMapper noteUserMapper = null;
	
	public NoteUserMapper getNoteUserMapper(){
		return noteUserMapper;
	}
	
	//create Methode
	public NoteUser create(NoteUser noteUser) throws ClassNotFoundException, SQLException {
		
		try {
			
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO noteUser(firstName, userName, surName, email, googleId) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
					
			stmt.setString(1,  noteUser.getFirstName());
			stmt.setString(2,  noteUser.getUserName());
			stmt.setString(3,  noteUser.getSurName());
			stmt.setString(4,  noteUser.getEmail());
			stmt.setString(5,  noteUser.getGoogleId());
			
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				return findById(rs.getInt(1));
			} else {
				throw new SQLException("Sorry Bro");
			}
			
		} 	catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		return noteUser;
				
	}

		//findById Methode
		public NoteUser findById(int NoteUserId) throws ClassNotFoundException, SQLException{
					
		try {
			PreparedStatement stmt = connection.prepareStatement(" SELECT * FROM NoteUser where noteUserid = ?");
			stmt.setInt(1,  NoteUserId);
			
			//Ergebnis holen
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				return new NoteUser(NoteUserId, results.getString("firstName"), results.getString("userName"), results.getString("surName"), results.getString("email"), results.getString("googleId"));
			} else {
				throw new SQLException("Exception");
			} 
			
		}	catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		return null;
	}
	
		
		public NoteUser save(NoteUser noteUser) throws ClassNotFoundException, SQLException {
				
		try {
			PreparedStatement stmt = connection.prepareStatement("UPDATE NoteUser SET firstName=?, userName=?, surName=?, email=?, googleId=? WHERE noteUserId=?");
			
			stmt.setString(1,  noteUser.getFirstName());
			stmt.setString(2,  noteUser.getUserName());
			stmt.setString(3,  noteUser.getSurName());
			stmt.setString(4,  noteUser.getEmail());
			stmt.setString(5,  noteUser.getGoogleId());
			
			stmt.executeUpdate();
			
		} 	catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		//aktualisierte Person zurückgeben
		return noteUser;
		
	}
		
		
	public void delete(NoteUser noteUser) throws ClassNotFoundException, SQLException {
				
		try {
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM NoteUser WHERE NoteUserId = ?");
			stmt.setInt(1, noteUser.getNoteUserId());
			stmt.executeUpdate();
			
		} 	catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
	}
	
	//public ArrayList<NoteUser> getAllNoteUsers() {
		//vector <NoteUser> retVal = new ArrayList<>();
		
//	}
	
	protected static NoteUserMapper noteUserMapper() throws ClassNotFoundException, SQLException {
		if (noteUserMapper == null) {
			noteUserMapper = new NoteUserMapper();
		}
		return noteUserMapper;
	}
	
}
