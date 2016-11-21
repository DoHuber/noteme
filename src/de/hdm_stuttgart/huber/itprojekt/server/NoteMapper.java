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
	 */
	
	public Note create(Note note){
		   Connection con = DataMapper.getConnection();

		    try {
		      Statement stmt = con.createStatement();

		      
		      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
		          + "FROM notes ");

		      if (rs.next()) {
		      
		        note.setNoteId(rs.getInt("maxid") + 1);

		        stmt = con.createStatement();

		    
		        stmt.executeUpdate("INSERT INTO notes (id, content, title, owner, noteBook, dueDate, creationDate, subtitle, modificationDate) " + "VALUES ("
		            + note.getNoteId() + "," + note.getContent() +","+ note.getTitle() + note.getOwner() + "," + note.getNoteBook() + "," + note.getDueDate() +
		            ","+ note.getCreationDate() + ","+ note.getSubtitle() + ","+ note.getModificationDate()+ ")");
		      }
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }
	
	  }
	
	
	
	 /**
	  * Bestimmte Notiz wird anhand der eindeutigen ID gesucht und zurückgegeben
	  * 
	  * @param id
	  * @return
	  */
	
	public Note findById(int id){
		
		Connection connection = DataMapper.getConnection();
		
	try {
		Statement stmt = connection.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM Note WHERE id="+ id);
		
		
		if (rs.next()) {
	        
	        Note note  = new Note();
	        note.setNoteId(rs.getInt("NoteId"));
	        note.setContent(rs.getString("content"));
	        note.setTitle(rs.getString("title"));
	     //   note.setOwner(rs.getNoteUser("owner"));
	     //   note.setNoteBook(rs.getNoteBook("notebook"));
	        note.setDueDate(rs.getDate("dueDate"));
	        note.setCreationDate(rs.getDate("creationDate"));
	        note.setSubtitle(rs.getString("subtitle"));
	        note.setModificationDate(rs.getDate("modificationDate"));

	        return note;
		 }
	    }
	 catch (SQLException e2) {
	      e2.printStackTrace();
	      return null;
	    }

	    return null;
	  }
	

	
	public Note save(Note note){
	    Connection con = DataMapper.getConnection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("UPDATE notes " + 
	      "SET content=\""+ note.getContent() + "\", " 
	      	+ "title=\"" + note.getTitle() + "\", "
	      	+ "owner=\"" + note.getOwner() + "\", "
	      	+ "noteBook=\"" + note.getNoteBook() + "\", "
	      	+ "dueDate=\"" + note.getDueDate() + "\", "
	      	+ "creationdate=\"" + note.getCreationDate() + "\", "
	      	+ "subtitle=\"" + note.getSubtitle() + "\", "	             
	      	+ "modificationDate=\"" + note.getModificationDate() + "\", "
	      	+ "WHERE noteId=" + note.getNoteId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    // Um Analogie zu insert(Account a) zu wahren, geben wir a zurÃ¼ck
	    return note;
	  }
		      
	
	/**
	 * Daten eines bestimmten Note-Objekts werden aus der Datenbank gelöscht 
	 * 
	 * @param note
	 */
	
	 public void delete(Note note) {
		 Connection connection = DataMapper.getConnection();

		    try {
		      Statement stmt = connection.createStatement();

		      stmt.executeUpdate("DELETE FROM Note WHERE noteId=" + note.getNoteId());
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
		
	 }
	 
	
	
	 protected static NoteMapper noteMapper() {
			if (noteMapper == null) {
			   noteMapper = new NoteMapper();
			   }

			    return noteMapper;
			  }	
		
	 
	
	
}

