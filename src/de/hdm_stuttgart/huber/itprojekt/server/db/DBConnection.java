package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.appengine.api.utils.SystemProperty;

public class DBConnection {

	// 2 Datenbank Zugangsdaten anlegen
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/notizbuch";
	static final String USER = "root";
	static final String PASS = "";

	// Sicherstellen dass nur EINE! Datenbankverbindung existiert.
	// localhost-mySQL frisst 3-4 Verbindungen
	// Cloud SQL?
	private static Connection singleton;

	protected DBConnection() {

	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		if (singleton == null) {

			String url, user, pass;

			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {

				url = "jdbc:google:mysql://it-projekt-149914:noizbuch2/notizbuch";
				user = "root";
				pass = "nm9000!";
				Class.forName("com.mysql.jdbc.GoogleDriver");

			} else {

				url = DB_URL;
				user = USER;
				pass = PASS;
			}

			singleton = DriverManager.getConnection(url, user, pass);

		}

		return singleton;
	}

	@Override
	protected void finalize() throws Throwable {
		singleton.close();
		super.finalize();
	}

}
