/**
 * 
 */
package de.hdm_stuttgart.huber.itprojekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

/**
 * @author elcpt
 *
 */
public interface Editor extends RemoteService {
	
	public void init() throws IllegalArgumentException;
	
	public String getHelloWorld();
	
	// CRUD-Methoden für NoteBook
	public NoteBook createNoteBook(NoteBook noteBook);
	public NoteBook saveNoteBook(NoteBook noteBook);
	public NoteBook getNoteBookById(NoteBook noteBook);
	public void deleteNoteBook(NoteBook noteBook);
	
	// Zusätzliche Methoden zu NoteBook
	public Vector<NoteBook> getAllNoteBooks();
	

	
	

}
