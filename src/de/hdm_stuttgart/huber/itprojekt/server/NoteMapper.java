package de.hdm_stuttgart.huber.itprojekt.server;

import java.sql.*;
import java.util.Vector;
import de.hdm_stuttgart.huber.itprojekt.server.db.DBConnection;

public class NoteMapper {
	
	
	private static NoteMapper noteMapper = null;


	
	
	
	
	public NoteMapper getNoteMapper(){
		
	}
	
	
	
	
	public Note create(Note note){
		
	
	  }
	
	
	
	
	public Note findById(Note note){
		
	}
	
	
	
	public Note save(Note note){
		
	}
	

	
	 public void delete(Note note) {
		
	 }
	 
	
	
	 protected static NoteMapper NoteMapper() {
			if (noteMapper == null) {
			   noteMapper = new NoteMapper();
			    }

			    return noteMapper;
			  }	
		
	 
	
	
}

