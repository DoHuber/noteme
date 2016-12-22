/**
 * 
 */
package de.hdm_stuttgart.huber.itprojekt.shared;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

/**
 * @author elcpt
 *
 */

@RemoteServiceRelativePath("testEditor")
public interface Editor extends RemoteService {
	
	public void init() throws IllegalArgumentException;
	
	public String getHelloWorld();
	
	// CRUD-Methoden für NoteBook
	public NoteBook createNoteBook(NoteBook notebook);
	public NoteBook saveNoteBook(NoteBook noteBook);
	public NoteBook getNoteBookById(NoteBook notebook)throws Exception;
	public void deleteNoteBook(NoteBook noteBook);
	
	// CRUD-Methoden für Note
	public Note createNote(Note note);
	public Note saveNote(Note note);
	public Note getNoteById(Note note) throws Exception;
	public void deleteNote(Note note);
	
	// CRUD-Methoden nach User, nur nach Login verwendbar!
	public Vector<Note> getAllNotesForCurrentUser();
	
	// Ohne Objekte zu verwenden, noch weniger Logik im Client
	public void createNote(String title, String subtitle, String content, String source, Date due_date, int notebook_id, int author_id);
	
	// Zusätzliche Methoden zu NoteBook
	public Vector<NoteBook> getAllNoteBooks() throws BullshitException;
	
	public Vector<Note> getAllNotes() throws BullshitException;
	
}
