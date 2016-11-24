package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

public class NotebookMapper {
	
private static NotebookMapper notebookMapper = null;
	
	public NotebookMapper getNotebookMapper(){
		return notebookMapper;
	}
	
	/**
	 * 
	 * Neues NoteBook-Obejkt wird in Datenbank eingef�gt
	 * 
	 * @param notebook
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public NoteBook create(NoteBook notebook) throws ClassNotFoundException, SQLException{
		   Connection con = DBConnection.getConnection();

		    try {
		      Statement stmt = con.createStatement();


		      ResultSet rs =  stmt.executeUpdate("INSERT INTO Note( Title, Subtitle, Owner, CreationDate, ModificationDate) " + "VALUES ("
		             +  notebook.getTitle() +","+  notebook.getSubtitle() + "," +  notebook.getOwner() + 
		            ","+  notebook.getCreationDate() + ","+  notebook.getModificationDate()+ ")");
		      
		    }
		    catch (SQLException sqlExp) {
		    sqlExp.printStackTrace();
		    }
		    return notebook;
	
	  }
	
	
	
	
	
	/**
	 * Bestimmtes NoteBook wird anhand der eindeutigen ID gesucht und zur�ckgegeben 
	 * 
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public NoteBook findById(int id) throws ClassNotFoundException, SQLException{
		
		Connection connection = DBConnection.getConnection();
		
	try {
		Statement stmt = connection.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM NoteBook WHERE id=" + id);
		
		
		if (rs.next()) {
	        
	        NoteBook notebook  = new NoteBook();
	        notebook.setNoteBookId(rs.getInt("NoteBookId"));
	        notebook.setTitle(rs.getString("Title"));
	        notebook.setSubtitle(rs.getString("Subtitle"));
	        notebook.setOwner(rs.getNoteUser("Owner")); 
	        notebook.setCreationDate(rs.getDate("CreationDate"));
	        notebook.setModificationDate(rs.getDate("ModificationDate"));

	        return notebook;
		 }
	    }
	 catch (SQLException sqlExp) {
		 sqlExp.printStackTrace();
	      return null;
	    }

	    return null;
	  }
	
	
	/**
	 * NoteBook-Objekt wird wiederholt in die Datenbank geschrieben
	 * 
	 * @param note
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	
	public Note save(Note notebook) throws ClassNotFoundException, SQLException{
	    Connection con = DBConnection.getConnection();

	    try {
	       Statement stmt = con.createStatement();

	      stmt.executeUpdate("UPDATE Notebook " + 
          "SET Title=\"" + notebook.getTitle() + "\", "
            + "Subtitle=\"" + notebook.getSubtitle() + "\", "
	      	+ "Owner=\"" + notebook.getOwner() + "\", "
	      	+ "Creationdate=\"" + notebook.getCreationDate() + "\", "	             
	      	+ "ModificationDate=\"" + notebook.getModificationDate() + "\", "
	      	+ "WHERE NoteId=" + notebook.getNoteId());

	    }
	    catch (SQLException sqlExp) {
	    	sqlExp.printStackTrace();
	    }

	    
	    return notebook;
	  }
	
	
	
	/**
	 * Daten eines bestimmten Notebook-Objekts werden aus der Datenbank gel�scht 
	 * 
	 * @param note
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	
	public void delete(NoteBook notebook) throws ClassNotFoundException, SQLException {
		 Connection connection = DBConnection.getConnection();

		    try {
		      Statement stmt = connection.createStatement();

		      stmt.executeUpdate("DELETE FROM Note WHERE NoteBookId=" + notebook.getNoteBookId());
		    }
		    catch (SQLException sqlExp) {
		    sqlExp.printStackTrace();
		    }
		
	 }
	
	
	/**
	 * 
	 * statische Methode, welche Singleton-Eigenschaft sicherstellt indem sie daf�r sorgt, dass nur eine Instanz von NotebookMapper existiert
	 * @return
	 */

	 protected static NotebookMapper notebookMapper() {
			if (notebookMapper == null) {
			   notebookMapper = new NotebookMapper();
			   }

			    return notebookMapper;
			  }	
		
	
	
	
	
	
	
	
	
}
