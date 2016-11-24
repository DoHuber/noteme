package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteUser;

public class NoteMapper {
	
	// Statisches Attribut, welches den Singleton-NoteMapper enthält.
	private static NoteMapper noteMapper = null;
	
	// Nichtöffentlicher Konstruktor, um unauthorisiertes Instanziieren dieser Klasse zu verhindern.
	protected NoteMapper () {
		
	}
	
	// Öffentliche Methode um den Singleton-NoteMapper zu erhalten
	public static NoteMapper getNoteMapper() {
		
		if (noteMapper == null) {
		   noteMapper = new NoteMapper();
		}

		return noteMapper;
 	}	
	
	
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
		    	
		      
		    	PreparedStatement stmt =  con.prepareStatement("INSERT INTO Note( Content, Title, Owner, NoteBook, DueDate, CreationDate, Subtitle, ModificationDate) " 
		    	+ "VALUES (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS );
		      
		    	stmt.setString(1, note.getContent());
		    	stmt.setString(2, note.getTitle());
		    	stmt.setLong(3, note.getOwner().getNoteUserId());
		    	stmt.setLong(4, note.getNoteId());
		    	stmt.setDate(5, (Date) note.getDueDate());
		    	stmt.setDate(6, (Date) note.getCreationDate());
		    	stmt.setString(7, note.getTitle());
		    	stmt.setDate(8, (Date) note.getModificationDate());
		    	
		    	
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
		
	try {
		
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Note WHERE id = ?");
         stmt.setLong(1, id);
		
		ResultSet rs=stmt.executeQuery();
		if (rs.next()) {
	        return new Note(
	        rs.getInt("NoteId"),
	        rs.getString("Content"),
	        rs.getString("Title"),
	        (new NoteUser()),
	        (new NoteBook()),
	        rs.getDate("DueDate"),
	        rs.getDate("CreationDate"),
	        rs.getString("Subtitle"),
	        rs.getDate("ModificationDate"));
	        
	        

	    }
	}
		
	 catch (SQLException sqlExp) {
		 sqlExp.printStackTrace();
	      return null;
	    }

	    return null;
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
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("UPDATE Note " + 
	      "SET Content=\""+ note.getContent() + "\", " 
	      	+ "Title=\"" + note.getTitle() + "\", "
	      	+ "Owner=\"" + note.getOwner() + "\", "
	      	+ "NoteBook=\"" + note.getNoteBook() + "\", "
	      	+ "DueDate=\"" + note.getDueDate() + "\", "
	      	+ "Creationdate=\"" + note.getCreationDate() + "\", "
	      	+ "Subtitle=\"" + note.getSubtitle() + "\", "	             
	      	+ "ModificationDate=\"" + note.getModificationDate() + "\", "
	      	+ "WHERE NoteId=" + note.getNoteId());

	    }
	    catch (SQLException sqlExp) {
	    	sqlExp.printStackTrace();
	    }

	    
	    return note;
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
		      Statement stmt = connection.createStatement();

		      stmt.executeUpdate("DELETE FROM Note WHERE NoteId=" + note.getNoteId());
		    }
		    catch (SQLException sqlExp) {
		    sqlExp.printStackTrace();
		    }
		
	 }
	 
	 public Vector<Note> getAllNotes() throws Exception{
		 
		 Vector<Note> result = new Vector<Note>();
		 
			
				Connection connection = DBConnection.getConnection();
				Statement stmt = connection.createStatement();
				
				// Das Verhalten wird sich erst später mit den HashMaps auszahlen!
				ResultSet rs = stmt.executeQuery("SELECT NoteId FROM Note");
				while (rs.next()) {
					result.add(this.findById(rs.getLong("NoteId")));
				} 
			
		
		 return result;
		
	 }
	 
}

