/**
 * 
 */
package de.hdm_stuttgart.huber.itprojekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

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
	public Vector<NoteBook> getAllNoteBooksForCurrentUser();
	
	// Zusätzliche Methoden zu NoteBook
	public Vector<NoteBook> getAllNoteBooks() throws BullshitException;
	
	public Vector<Note> getAllNotes() throws BullshitException;
	
	public String getTitle(NoteBook nB) throws BullshitException; 
	
	// Zusätzliche Methoden zu UserInfo
	public Vector<UserInfo> getAllNoteUser() throws BullshitException;
	
	public Vector<UserInfo> create(UserInfo u) throws BullshitException;
	
}
