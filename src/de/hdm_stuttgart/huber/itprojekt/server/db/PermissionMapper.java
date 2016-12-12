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
		if (permissionMapper == null) {
			permissionMapper = new PermissionMapper();
		}
		return permissionMapper;
	}

	//create Methode
	public Permission create(Permission permission) throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();

		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO permission(Content, User, Level, noteUserId, noteId, notebookId)"
					+ "VALUES ( ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			stmt.setObject(1, permission);
			stmt.setInt(2, permission.getUser().getId());
			stmt.setInt(3, permission.getLevel());
			stmt.setInt(4, 1);
			stmt.setInt(5, 1);
			stmt.setInt(6, 1);

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				return findById(rs.getInt(1));
			}    // else {
			// throw new SQLException("Sorry");
			// }
		} catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		return permission;

	}

	//findById Methode:
	public Permission findById(long id) throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Permission WHERE Id = ?");
			stmt.setLong(1, id);

			//Ergebnis holen
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				return new Permission(results.getObject("Content"),
						results.getInt("Level"),
						new NoteUser(),
						new NoteBook(),
						new Note());
			}
		} catch (SQLException sqlExp) {
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

			stmt.setObject(1, permission);
			// stmt.setNoteUser(2, permission.getUser());
			stmt.setInt(3, permission.getLevel());
			stmt.setInt(4, 1);
			stmt.setInt(5, 1);
			stmt.setInt(6, 1);

			stmt.executeUpdate();
		} catch (SQLException sqlExp) {
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
			stmt.setLong(1, permission.getPermissionId());
			stmt.executeUpdate();
		} catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
	}

	public Vector<Permission> getAllPermission() throws Exception {
		Vector<Permission> result = new Vector<Permission>();

		Connection con = DBConnection.getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT Id FROM Permission");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			result.add(this.findById(rs.getLong("NoteUserId")));
		}
		return result;
	}
}