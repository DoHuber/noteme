package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;

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
		    	
		      Statement stmt = con.createStatement();
		      
		      ResultSet rs = stmt.executeQuery("SELECT MAX(NoteId) AS maxid FROM Note ");

		      if (rs.next()) {
		      
		        note.setNoteId(rs.getInt("maxid") + 1);

		        stmt = con.createStatement();

		        stmt.executeUpdate("INSERT INTO Note(NoteId, Content, Title, Owner, NoteBook, DueDate, CreationDate, Subtitle, ModificationDate) " + "VALUES ("
		            + note.getNoteId() + "," + note.getContent() +","+ note.getTitle() +","+ note.getOwner() + "," + note.getNoteBook() + "," + note.getDueDate() +
		            ","+ note.getCreationDate() + ","+ note.getSubtitle() + ","+ note.getModificationDate()+ ")");
		      }
		    }
		    catch (SQLException sqlExp) {
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
	
	public Note findById(int id) throws Exception {
		
	
		Connection connection = DBConnection.getConnection();
		
		Statement stmt = connection.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM Note WHERE NoteId=" + id);
		
		
		if (rs.next()) {
	        
	        Note note  = new Note();
	        note.setNoteId(rs.getInt("NoteId"));
	        note.setContent(rs.getString("Content"));
	        note.setTitle(rs.getString("Title"));
	     // note.setOwner(rs.getNoteUser("Owner"));
	     // note.setNoteBook(rs.getNoteBook("Notebook"));
	        note.setDueDate(rs.getDate("DueDate"));
	        note.setCreationDate(rs.getDate("CreationDate"));
	        note.setSubtitle(rs.getString("Subtitle"));
	        note.setModificationDate(rs.getDate("ModificationDate"));

	        return note;
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
					result.add(this.findById(rs.getInt("NoteId")));
				} 
			
		
		 return result;
		
	 }
	 
}

