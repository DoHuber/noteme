package de.hdm_stuttgart.huber.itprojekt.server;

import java.sql.*;

import de.hdm_stuttgart.huber.itprojekt.server.db.DataMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;

public class NoteMapper {
	
	private static NoteMapper noteMapper = null;
	
	public NoteMapper getNoteMapper(){
		return noteMapper;
	}
	
	
		/**
	 * Neues Note-Objekt wird in Datenbank eingefügt
	 * 
	 * @param note
	 * @return
		 * @throws SQLException 
		 * @throws ClassNotFoundException 
	 */
	
	public Note create(Note note) throws ClassNotFoundException, SQLException{
		   Connection con = DataMapper.getConnection();

		    try {
		      Statement stmt = con.createStatement();

		      
		      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM Note ");

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
	  * Bestimmte Notiz wird anhand der eindeutigen ID gesucht und zurückgegeben
	  * 
	  * @param id
	  * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	  */
	
	public Note findById(int id) throws ClassNotFoundException, SQLException{
		
		Connection connection = DataMapper.getConnection();
		
	try {
		Statement stmt = connection.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM Note WHERE id=" + id);
		
		
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
	    }
	 catch (SQLException sqlExp) {
		 sqlExp.printStackTrace();
	      return null;
	    }

	    return null;
	  }
	

	
	public Note save(Note note) throws ClassNotFoundException, SQLException{
	    Connection con = DataMapper.getConnection();

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
	 * Daten eines bestimmten Note-Objekts werden aus der Datenbank gelöscht 
	 * 
	 * @param note
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
	 public void delete(Note note) throws ClassNotFoundException, SQLException {
		 Connection connection = DataMapper.getConnection();

		    try {
		      Statement stmt = connection.createStatement();

		      stmt.executeUpdate("DELETE FROM Note WHERE NoteId=" + note.getNoteId());
		    }
		    catch (SQLException sqlExp) {
		    sqlExp.printStackTrace();
		    }
		
	 }
	 
	
	
	 protected static NoteMapper noteMapper() {
			if (noteMapper == null) {
			   noteMapper = new NoteMapper();
			   }

			    return noteMapper;
			  }	
		
	 
	
	
}

