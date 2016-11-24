package de.hdm_stuttgart.huber.itprojekt.server;

import java.sql.*;
import de.hdm_stuttgart.huber.itprojekt.server.db.DBConnection;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteUser;


public class NoteUserMapper {
	
private static NoteUserMapper noteUserMapper = null;
	
	public NoteUserMapper getNoteUserMapper(){
		return noteUserMapper;
	}
	public NoteUser create (NoteUser noteUser) throws ClassNotFoundException, SQLException{
		Connection con = DBConnection.getConnection();
		
		try {
			Statement stmt = con.createStatement();
						
				ResultSet rs = stmt.executeUpdate("INSERT INTO NoteUser(FirstName, UserName, SurName, Email, GoogleId) "
				+ "VALUES (" + noteUser.getFirstName() + "," + noteUser.getUserName() + "," + noteUser.getSurName() 
				+ "," + noteUser.getEmail() + "," + noteUser.getGoogleId() + ")");
		}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		return noteUser;
		
	}

		public NoteUser findById(int NoteUserId) throws ClassNotFoundException, SQLException{
			Connection connection = DBConnection.getConnection();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM NoteUser WHERE noteUserId = " + NoteUserId);
			
			if (rs.next()) {
				NoteUser noteUser = new NoteUser();
				noteUser.setNoteUserId(rs.getInt("NoteUserId"));
				noteUser.setFirstName(rs.getString("FirstName"));
				noteUser.setUserName(rs.getString("UserName"));
				noteUser.setSurName(rs.getString("SurName"));
			    noteUser.setEmail(rs.getString("Email"));
				noteUser.setGoogleId(rs.getString("GoogleId"));
							
				return noteUser;
			}
		}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			return null;
		}
		return null;
	}
	public NoteUser save(NoteUser noteUser) throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE NoteUser " + 
				      "SET FirstName=\""+ noteUser.getFirstName() + "\", " 
				      	+ "UserName=\"" + noteUser.getUserName() + "\", "
				      	+ "SurName=\"" + noteUser.getSurName() + "\", "
				      	+ "Email=\"" + noteUser.getEmail() + "\", "
						+ "GoogleId=\"" + noteUser.getGoogleId() + "\", "
				      	+ "WHERE NoteUserId=" + noteUser.getNoteUserId());
		}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		return noteUser;
	}
	public void delete(NoteUser noteUser) throws ClassNotFoundException, SQLException {
		Connection connection = DBConnection.getConnection();
		
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("DELETE FROM NoteUser WHERE NoteUserId=" + noteUser.getNoteUserId());
			}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
	}
	protected static NoteUserMapper noteUserMapper() {
		if (noteUserMapper == null) {
			noteUserMapper = new NoteUserMapper();
		}
		return noteUserMapper;
	}
	
}
