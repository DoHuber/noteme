package de.hdm_stuttgart.huber.itprojekt.server;

import java.sql.*;
import com.google.gwt.dev.shell.JavaObject;
import de.hdm_stuttgart.huber.itprojekt.server.db.DBConnection;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;


public class PermissionMapper {
	
private static PermissionMapper permissionMapper = null;
	
	public PermissionMapper getPermissionMapper(){
		return permissionMapper;
	}
	
	public Permission create (Permission permission) throws ClassNotFoundException, SQLException{
		Connection con = DBConnection.getConnection();
		
		try {
			Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM Permission "); //fehlt da Perm.Id?
			
			if (rs.next()) {
				permission.setPermissionId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();
				
				stmt.executeUpdate("INSERT INTO Permission(PermissionId, content, user, level) " 
				+ "VALUES (" + permission.getPermissionId() + "," + permission.getContent() 
				+ "," + permission.getUser() + "," + permission.getLevel() + ")");
			}
		}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		return permission;
		
	}

		public Permission findById(int PermissionId) throws ClassNotFoundException, SQLException{
			Connection connection = DBConnection.getConnection();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Permission WHERE permissionId = " + PermissionId);
			
			if (rs.next()) {
				Permission permission = new Permission();
				permission.setPermissionId(rs.getInt("PermissionId"));
				permission.setContent((JavaObject) rs.getObject("Content")); //passt das??
				permission.setLevel(rs.getInt("Level"));
				
				return permission;
			}
		}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			return null;
		}
		return null;
	}
	public Permission save(Permission permission) throws ClassNotFoundException, SQLException {
		Connection con = DBConnection.getConnection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE Permission " + 
				      "SET Content=\""+ permission.getContent() + "\", " 
				      	+ "User=\"" + permission.getUser() + "\", "
				      	+ "Level=\"" + permission.getLevel() + "\", "
				      	+ "WHERE PermissionId=" + permission.getPermissionId());
		}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
		return permission;
	}
	public void delete(Permission permission) throws ClassNotFoundException, SQLException {
		Connection connection = DBConnection.getConnection();
		
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("DELETE FROM Permission WHERE PermissionId=" + permission.getPermissionId());
			}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
		}
	}
	protected static PermissionMapper permissionMapper() {
		if (permissionMapper == null) {
			permissionMapper = new PermissionMapper();
		}
		return permissionMapper;
	}
	
}