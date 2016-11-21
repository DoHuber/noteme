package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataMapper {
	   //2 Datenbank Zugangsdaten anlegen
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://207.223.162.139:3306/Notizbuch";
	   static final String USER = "lisakuechler";
	   static final String PASS = "itprojekt22";
	   
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
	   Connection connection = null;
	   
	   //3. JDBC Treiber regestrieren
	   Class.forName("com.mysql.jdbc.Driver");
	   
	   //4. Verbindung aufbauen
	   connection = DriverManager.getConnection(DB_URL,USER,PASS);
	   return connection;   
	}

}