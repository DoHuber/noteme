package de.hdm_stuttgart.huber.itprojekt.server.db;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteUser;

import java.sql.*;
import java.util.Vector;


public class NoteUserMapper extends DataMapper {

    //Statisches Attribut, welches den Singleton-NoteUserMapper enthält
    private static NoteUserMapper noteUserMapper = null;

    //Konstruktor (protected, um unauthorisiertes Instanziieren der Klasse zu verhindern)
    protected NoteUserMapper() throws ClassNotFoundException, SQLException {
     
    }

    //Öffentliche statische Methode, um den Singleton-NoteUserMapper zu erhalten
    public static NoteUserMapper getNoteUserMapper() throws ClassNotFoundException, SQLException {
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

            stmt.setString(1, noteUser.getFirstName());
            stmt.setString(2, noteUser.getUserName());
            stmt.setString(3, noteUser.getSurName());
            stmt.setString(4, noteUser.getEmail());
            stmt.setString(5, noteUser.getGoogleId());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return findById(rs.getInt(1));
            }   
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
        }
        return noteUser;
    }

    //findById Methode:
    // bestimmte Notiz wird anhand der eindeutigen ID gesucht und zurückgegeben
    // long wegen DomainObject
    public NoteUser findById(int id) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

      //Was macht die isObjectLoaded Verzweigung hier?
       if (isObjectLoaded(id, NoteUser.class)) {
    	   return (NoteUser) loadedObjects.get(id);
       }
       
       try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM notizbuch.noteuser WHERE id = ?");
            stmt.setInt(1, id);

            //Ergebnis holen
            ResultSet results = stmt.executeQuery();
            if (results.next()) {

                NoteUser noteUser = new NoteUser(results.getInt("id"),
            	        results.getString("firstname"),
                        results.getString("username"),
                        results.getString("lastname"),
                        results.getString("email"),
                        results.getString("google_id"));
                
                loadedObjects.put(results.getInt("id"), noteUser);
                return noteUser;
            }

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            return null;
        }

        return null;
    }


    //save-Methode: NoteUser-Objekt wird wiederholt in die DB geschrieben
    public NoteUser save(NoteUser noteUser) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE NoteUser SET firstName=?, userName=?, surName=?, email=?, googleId=? WHERE id=?");

            stmt.setString(1, noteUser.getFirstName());
            stmt.setString(2, noteUser.getUserName());
            stmt.setString(3, noteUser.getSurName());
            stmt.setString(4, noteUser.getEmail());
            stmt.setString(5, noteUser.getGoogleId());

            stmt.executeUpdate();

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            throw new IllegalArgumentException();
        }
        return findById(noteUser.getId());
    }


    public void delete(NoteUser noteUser) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM notizbuch.noteUser WHERE id = ?");
            stmt.setLong(1, noteUser.getId());
            stmt.executeUpdate();

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public Vector<NoteUser> getAllNoteUser() throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        Vector<NoteUser> result = new Vector<>();
        
        try {
        // wieso kein prepared Statement mehr?
        // PreparedStatement stmt = con.prepareStatement("SELECT NoteUserId FROM NoteUser");
        Statement stmt = con.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM notizbuch.noteUser");
        	
       while (results.next()){
    	   NoteUser noteUser = new NoteUser(results.getInt("id"),
    			   results.getString("firstname"),
                   results.getString("username"),
                   results.getString("lastname"),
                   results.getString("email"),
                   results.getString("google_id"));
    	   result.add(noteUser);
       	   }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return result;
    }
    
}
