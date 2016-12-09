package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.*;
import java.util.Vector;
import de.hdm_stuttgart.huber.itprojekt.server.db.DBConnection;
import de.hdm_stuttgart.huber.itprojekt.server.db.DataMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteUser;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;


public class PermissionMapper extends DataMapper {
	
	// Statisches Attribut, welches den Singleton-PermissionMapper enthält
	private static PermissionMapper permissionMapper = null;
	
	// Konstruktor (protected, um unauthorisiertes Instanziieren der Klasse zu verhindern)
	protected PermissionMapper() throws ClassNotFoundException, SQLException {
		super();
	}

	// Öffentliche statische Methode, um den Singleton-PermissionMapper zu erhalten
	public static PermissionMapper getPermissionMapper() throws ClassNotFoundException, SQLException {
		if (permissionMapper == null ) {
			permissionMapper = new PermissionMapper();
		}
		return permissionMapper;
	}
	
	//create Methode
	public Permission create (Permission permission) throws ClassNotFoundException, SQLException{
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO permission(Content, User, Level, noteUserId, noteId, notebookId)" 
			+ "VALUES ( ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			stmt.setObject(1,  permission.getContent());
			stmt.setInt(2, permission.getUser());
			stmt.setInt(3,  permission.getLevel());
			stmt.setInt(4, 1);
			stmt.setInt(5, 1);
			stmt.setInt(6, 1);
			
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
		return permission;
		
	}

		//findById Methode:
		public Permission findById(long id) throws ClassNotFoundException, SQLException{
			Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Permission WHERE Id = ?");
			stmt.setLong(1, id);
			
			//Ergebnis holen
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				return new Permission	(results.getObject("Content"),
										results.getInt("Level"),
										new NoteUser(),
										new NoteBook(),
										new Note());
			}
		}	catch (SQLException sqlExp) {
				sqlExp.printStackTrace();
				return null;
		}
		return null;
	}
		
	
	//save-Methode
	public Permission save(Permission permission) throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE Permission SET content=?, user=?, level=?, noteUserId=?, noteBookId=?, noteId=? WHERE Id=?");

			stmt.setObject(1,  permission.getContent());
			// stmt.setNoteUser(2, permission.getUser());
			stmt.setInt(3,  permission.getLevel());
			stmt.setInt(4, 1);
			stmt.setInt(5, 1);
			stmt.setInt(6, 1);

			stmt.executeUpdate();
		}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		//aktualisierte Berechtigung zurückgeben
		return permission;
	}
	
	
	//delete-Methode
	public void delete(Permission permission) throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE FROM Permission WHERE Id=?");
			stmt.setLong(1,  permission.getPermissionId());
			stmt.executeUpdate();
		}
			catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
	}
	
	public Vector<Permission> getAllPermission() throws Exception {
		Vector <Permission> result = new Vector<Permission>();
		
		Connection con = DBConnection.getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT Id FROM Permission");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			result.add(this.findById(rs.getLong("NoteUserId")));
		}
	return result;
	}
	
	
package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.*;
import java.util.Vector;
import de.hdm_stuttgart.huber.itprojekt.server.db.DBConnection;
import de.hdm_stuttgart.huber.itprojekt.server.db.DataMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteUser;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;


public class PermissionMapper extends DataMapper {
	
	// Statisches Attribut, welches den Singleton-PermissionMapper enthÃ¤lt
	private static PermissionMapper permissionMapper = null;
	
	// Konstruktor (protected, um unauthorisiertes Instanziieren der Klasse zu verhindern)
	protected PermissionMapper() throws ClassNotFoundException, SQLException {
		
	}

	// Ã–ffentliche statische Methode, um den Singleton-PermissionMapper zu erhalten
	public static PermissionMapper getPermissionMapper() throws ClassNotFoundException, SQLException {
		if (permissionMapper == null ) {
			permissionMapper = new PermissionMapper();
		}
		return permissionMapper;
	}
	
	//create Methode
	public Permission create (Permission permission) throws ClassNotFoundException, SQLException{
		Connection con = DBConnection.getConnection();
		
		try {

			PreparedStatement stmt = con.prepareStatement("INSERT INTO permission(Content, User, Level, author_id, note_id, notebook_id)" 
			+ "VALUES ( ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1,  	permission.getContent());
			stmt.setString(2, 	permission.getNoteUser().getUser());
			stmt.setInt(3,  	permission.getLevel());
			stmt.setInt(4, 		permission.getNoteUser().getId());
			stmt.setInt(5, 		permission.getNote().getId());
			stmt.setInt(6, 		permission.getNotebook().getId());
			
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				return findById(rs.getInt(1));
			}	
		}	catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		return permission;
	}

		//findById Methode:
		public Permission findById(int id) throws ClassNotFoundException, SQLException{
			Connection con = DBConnection.getConnection();
			
			if (isObjectLoaded(id, Permission.class)) {
	            return (Permission) loadedObjects.get(id);
	        }
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM notizbuch.permission WHERE id = ?");
			stmt.setInt(1, id);
			
			//Ergebnis holen
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				
				Permission permission = new Permission(results.getInt("id"),
										results.getObject("Content"),
										results.getInt("Level"),
										NoteUserMapper.getNoteUserMapper().findById(results.getLong("author_id")),
										NoteMapper.getNotemapper().findById(results.getLong("note_id")),
										NoteBookMapper.getNoteBookMapper().findById(results.getLong("notebook_id")));
				loadedObjects.put(results.getInt("id"), permission);
				return permission;					
			}
		}	catch (SQLException sqlExp) {
				sqlExp.printStackTrace();
				return null;
		}
		return null;
	}
		
	
	//save-Methode
	public Permission save(Permission permission) throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE notizbuch.permission SET content=?, user=?, level=?, noteUserId=?, noteBookId=?, noteId=? WHERE id=?");

			stmt.setString(1,  	permission.getContent());
			stmt.setString(2, 	permission.getNoteUser().getUser());
			stmt.setInt(3,  	permission.getLevel());
			stmt.setInt(4, 		permission.getNoteUser().getId());
			stmt.setInt(5, 		permission.getNote().getId());
			stmt.setInt(6, 		permission.getNotebook().getId());

			stmt.executeUpdate();
		}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			throw new IllegalArgumentException();
		}
		return findById(permission.getId());
	}
	
	
	//delete-Methode
	public void delete(Permission permission) throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE FROM notizbuch.permission WHERE id=?");
			stmt.setInt(1,  permission.getId());
			stmt.executeUpdate();
		}
			catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	public Vector<Permission> getAllPermission() throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();
		Vector<Permission> result = new Vector<>();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM notizbuch.permission");
			NoteUserMapper noteUserMapper = NoteUserMapper.getNoteUserMapper();
			NoteMapper noteMapper = NoteMapper.getNoteMapper();
            NoteBookMapper noteBookMapper = NoteBookMapper.getNoteBookMapper();
            
            while (rs.next()) {
            	Permission permission = new Permission(rs.getInt("id"),
            								results.getObject("Content"),
            								results.getInt("Level"),
            								NoteUserMapper.getNoteUserMapper().findById(results.getLong("author_id")),
            								NoteMapper.getNotemapper().findById(results.getLong("note_id")),
            								NoteBookMapper.getNoteBookMapper().findById(results.getLong("notebook_id")));
            	result.add(permission);
            }
		}	catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
	return result;
	}
	
	
}