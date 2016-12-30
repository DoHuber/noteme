package de.hdm_stuttgart.huber.itprojekt.server.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public class NoteBookMapper extends DataMapper {
	
private static NoteBookMapper noteBookMapper = null;
	
	protected NoteBookMapper() throws ClassNotFoundException, SQLException {
		super();
	}

	 public static NoteBookMapper getNoteBookMapper() {
		if (noteBookMapper == null) {

			 try {
				 
				noteBookMapper = new NoteBookMapper();
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} 
		}
			return noteBookMapper;
	}	

	public NoteBook create(NoteBook notebook) {
	
		    try {
		    	
		    	PreparedStatement stmt =  connection.prepareStatement("INSERT INTO notizbuch.notebook(title, subtitle, creation_date, modification_date, author_id) "
		    	+ "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS );
		      
		    	stmt.setString(1, notebook.getTitle());
		    	stmt.setString(2, notebook.getSubtitle());
		    	
		    	stmt.setDate(3, notebook.getCreationDate());
		    	stmt.setDate(4, notebook.getModificationDate());
		    	
		    	stmt.setInt(5, notebook.getOwner().getId());
		    	
		    	stmt.executeUpdate();
		    	
		    	ResultSet rs = stmt.getGeneratedKeys();
		    	
		    	if(rs.next()){
		    		return findById(rs.getLong(1));
		    		
		    	}
		    	
		    } catch (SQLException | ClassNotFoundException sqlExp) {
		    	
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
	
	public NoteBook findById(long id) throws ClassNotFoundException, SQLException{
		
		Connection connection = DBConnection.getConnection();
		
		try {
			
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM notizbuch.notebook WHERE id = ?");
	         stmt.setLong(1, id);
			
			ResultSet rs=stmt.executeQuery();
			if (rs.next()) {
				
		        return new NoteBook(

                        rs.getInt("id"),
		                rs.getString("title"),
		        		rs.getString("subtitle"),
		        		UserInfoMapper.getUserInfoMapper().findById(rs.getInt("author_id")),
		        		rs.getDate("creation_date"),
		        		rs.getDate("modification_date"));

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
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public NoteBook save(NoteBook notebook) throws ClassNotFoundException, SQLException{
		Connection con = DBConnection.getConnection();

	    try {
	      
	      PreparedStatement stmt = con.prepareStatement("UPDATE notizbuch.notebook SET title=?, subtitle=?, creation_date=?, modification_date=?, author_id=? WHERE id = ?");
	      
	      	stmt.setString(1, notebook.getTitle());
	    	stmt.setString(2, notebook.getSubtitle());
	    	stmt.setDate(3, notebook.getCreationDate());
	    	stmt.setDate(4, new Date(System.currentTimeMillis()));
	    	stmt.setInt(5, notebook.getOwner().getId());
	    	
	    	stmt.setInt(6, notebook.getId());
	    	
	    	stmt.executeUpdate();

	    

	    }
	    catch (SQLException sqlExp) {
	    	sqlExp.printStackTrace();
	    	throw new IllegalArgumentException();
	    }

	    return findById(notebook.getId());
	  }
		  
	 public void delete(NoteBook notebook) {


		 try {

	            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Notebook WHERE id = ?");
	            stmt.setInt(1, notebook.getId());
	            stmt.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new IllegalArgumentException();
	        }

	    }
	
	



	 public Vector<NoteBook> getAllNoteBooks() throws ClassNotFoundException, SQLException {

	        try {

	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT * FROM notizbuch.notebook");

	            return makeNoteBooksFromResultSet(rs);

	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	            return null;
	        }

	    }
	 
	 	public Vector<NoteBook> getAllNoteBooksForUserId(int userId) {
		 	
	 		Vector<NoteBook> v = new Vector<>();

	        try {

	            PreparedStatement ps = connection.prepareStatement("SELECT * FROM notizbuch.notebook WHERE author_id = ?");
	            ps.setInt(1, userId);
	            
	            ResultSet rs = ps.executeQuery();
	            
	            return makeNoteBooksFromResultSet(rs);

	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
		        return null;
	        }

	    }

	 	public Vector<NoteBook> getAllNoteBooksSharedWith(UserInfo u) {

	 		try {

	    		String sql = "SELECT notebook.id AS id, title, subtitle, creation_date, modification_date, author_id FROM notebook "
	    				+ "JOIN permission ON notebook.id = permission.notebook_id WHERE beneficiary_id = ?";

	    		PreparedStatement ps = connection.prepareStatement(sql);
	    		ps.setInt(1, u.getId());
	    		ResultSet rs = ps.executeQuery();

	    		return makeNoteBooksFromResultSet(rs);

	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		return new Vector<>();
	    	}

	 	}


		private Vector<NoteBook> makeNoteBooksFromResultSet(ResultSet rs)
				throws SQLException, ClassNotFoundException {

			Vector<NoteBook> v = new Vector<>();

			while (rs.next()) {

			    NoteBook resultRow = new NoteBook(rs.getInt("id"));

			    resultRow.setTitle(rs.getString("title"));
			    resultRow.setSubtitle(rs.getString("subtitle"));
			    resultRow.setCreationDate(rs.getDate("creation_date"));
			    resultRow.setModificationDate(rs.getDate("modification_date"));
			    resultRow.setOwner(UserInfoMapper.getUserInfoMapper().findById(rs.getInt("author_id")));

			    v.add(resultRow);

			}

			return v;
		}

		public Vector<NoteBook> getAllNoteBooksSharedBy(UserInfo u) {

	    	String sql = "SELECT DISTINCT notebook.id AS id FROM notebook JOIN permission ON notebook.id = permission.notebook_id WHERE author_id = ?";
	    	Vector<NoteBook> v = new Vector<>();

	    	try {

	    	PreparedStatement ps = connection.prepareStatement(sql);
	    	ps.setInt(1, u.getId());

	    	ResultSet rs = ps.executeQuery();
	    	while (rs.next()) {
	    		v.add(findById(rs.getInt("id")));
	    	}

	    	return v;

	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		return v;
	    	}

	    }
	
	
	
	
	
	
	
}
