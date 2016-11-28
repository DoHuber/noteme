package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.*;
import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteUser;

public class NoteMapper extends DataMapper {
	
	// Statisches Attribut, welches den Singleton-NoteMapper enthält.
	private static NoteMapper noteMapper = null;
	
	// Nichtöffentlicher Konstruktor, um unauthorisiertes Instanziieren dieser Klasse zu verhindern.
	protected NoteMapper () throws ClassNotFoundException, SQLException {
		
	}
	
	// Öffentliche Methode um den Singleton-NoteMapper zu erhalten
	public static NoteMapper getNoteMapper() throws ClassNotFoundException, SQLException {
		
		if (noteMapper == null) {
		   noteMapper = new NoteMapper();
		}

		return noteMapper;
 	}	
	
	
	// NoteMapper nMapper = new NoteMapper();
	
	
		/**
	 * Neues Note-Objekt wird in Datenbank eingef�gt
	 * Hierbei wird der Prim�rschl�ssel des �bergebenen Obejktes gepr�ft und falls n�tig berichtigt
	 * @param note
	 * @return
		 * @throws SQLException 
		 * @throws ClassNotFoundException 
	 */
	
	public Note create(Note note) throws ClassNotFoundException, SQLException{
		   Connection con = DBConnection.getConnection();

		    try {
		    	
		      
		    	PreparedStatement stmt =  con.prepareStatement("INSERT INTO Note(Title, Content, CreationDate, DueDate, ModificationDate, Subtitle, Source, noteBookId, noteUserId, permissionId, dreierId) " 
		    	+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS );
		      
		    	stmt.setString(1, note.getTitle());
		    	stmt.setString(2, note.getContent());
		    	
		    	stmt.setDate(3, new Date(7777));
		    	stmt.setDate(4, new Date(7777));
		    	stmt.setDate(5, new Date(7777));
		    	
		    	stmt.setString(6, note.getSubtitle());
		    	stmt.setString(7, "Platzhalter");
		    	
		    	stmt.setInt(8, 1);
		    	stmt.setInt(9, 1);
		    	stmt.setInt(10, 1);
		    	stmt.setInt(11, 0);
		    
		    	stmt.executeUpdate();
		    	ResultSet rs = stmt.getGeneratedKeys();
		    	
		    	if(rs.next()){
		    		return findById(rs.getLong(1));
		    		
		    	}
		    	
		    } catch (SQLException sqlExp) {
		    sqlExp.printStackTrace();
		   
		    }
			return note;
	
	  }
	
	
	
	 /**
	  * Bestimmte Notiz wird anhand der eindeutigen ID gesucht und zur�ckgegeben
	  * 
	  * @param id
	  * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	  */
	
	public Note findById(Long id) throws ClassNotFoundException, SQLException{
		
		Connection connection = DBConnection.getConnection();
		Object note = new Note();
		
		if(this.isObjectLoaded(id)==true){
	     note = loadedObjects.get(id);
	     return (Note) note;
		}
		else{
			
		
			try {
		
				PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Note WHERE NoteId = ?");
				stmt.setLong(1, id);
		
				ResultSet rs=stmt.executeQuery();
				if (rs.next()) {
			
					note = new Note(rs.getInt("NoteId"),
							rs.getString("Content"),
							rs.getString("Title"),
							rs.getString("Subtitle"),
							new NoteUser(),
							new NoteBook(),
							new Date(77777),
							new Date(77777),
							new Date(77777));
	        
					this.addToHashMap(rs.getInt("NoteId"), note);    
				}
			}
		
			catch (SQLException sqlExp) {
				sqlExp.printStackTrace();
				return null;
			}

			return (Note) note;
	  }
	}
/**
 * 
 * @param  Note-Objekt wird wiederholt in die Datenbank geschrieben
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
	
	public Note save(Note note) throws ClassNotFoundException, SQLException{
	   
		Connection con = DBConnection.getConnection();

	    try {
	      
	      PreparedStatement stmt = con.prepareStatement("UPDATE Note SET Title=?, Content=?, CreationDate=?, DueDate=?, ModificationDate=?, Subtitle=?, Source=?, noteBookId=?, noteUserId=?, permissionId=?, dreierId=? WHERE id = ?");
	      
	      	stmt.setString(1, note.getTitle());
	    	stmt.setString(2, note.getContent());
	    	
	    	stmt.setDate(3, new Date(7777));
	    	stmt.setDate(4, new Date(7777));
	    	stmt.setDate(5, new Date(7777));
	    	
	    	stmt.setString(6, note.getSubtitle());
	    	stmt.setString(7, "Source");
	    	
	    	stmt.setInt(8, 1);
	    	stmt.setInt(9, 1);
	    	stmt.setInt(10, 1);          //noch �ndern
	    	stmt.setInt(11, 0);
	    	
	    	stmt.setLong(12, note.getNoteId());
	    	
	    	stmt.executeUpdate();

	    

	    }
	    catch (SQLException sqlExp) {
	    	sqlExp.printStackTrace();
	    	throw new IllegalArgumentException();
	    }

	    return findById((long)note.getNoteId());
	  }
		      
	
	/**
	 * Daten eines bestimmten Note-Objekts werden aus der Datenbank gel�scht 
	 * 
	 * @param note
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
	 public void delete(Note note) throws ClassNotFoundException, SQLException {
		
		 Connection connection = DBConnection.getConnection();

		 try {

	            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Note WHERE id = ?");
	            stmt.setLong(1, note.getNoteId());
	            stmt.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new IllegalArgumentException();
	        }

	    }
	 
	 
	 
	 public Vector<Note> getAllNotes() throws ClassNotFoundException, SQLException {
		 	
		 Connection connection = DBConnection.getConnection();
	        Vector<Note> v = new Vector<>();

	        try {

	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT * FROM Note");

	            while (rs.next()) {

	                Note note = new Note(rs.getInt("NoteId"),
	    	        		rs.getString("Content"),
	    	        		rs.getString("Title"),
	    	        		rs.getString("Subtitle"),
	    	        		new NoteUser(),
	    	        		new NoteBook(),
	    	        		new Date(77777),
	    	        		new Date(77777),
	    	        		new Date(77777));

	                v.add(note);

	            } 

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return v;

	    }
	 
}

