/**
 * 
 */
package de.hdm_stuttgart.huber.itprojekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Notebook;
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
	public Notebook createNoteBook(Notebook notebook);

	public Notebook saveNoteBook(Notebook noteBook);

	public Notebook getNoteBookById(Notebook notebook) throws Exception;

	public void deleteNoteBook(Notebook noteBook);

	// CRUD-Methoden für Note
	public Note createNote(Note note);

	public Note saveNote(Note note);

	public Note getNoteById(Note note) throws Exception;

	public void deleteNote(Note note);

	// CRUD-Methoden nach User, nur nach Login verwendbar!
	public Vector<Note> getAllNotesForCurrentUser();
	
	public Vector<Note> getAllNotesForUser(UserInfo user);
	
	public Vector<Notebook> getAllNoteBooksForCurrentUser();
	
	public Vector<Notebook> getAllNoteBooksFor(UserInfo u);

	// Methoden mit Freigabe dabei
	public Vector<Note> getAllSharedNotesForCurrentUser();

	public Vector<Notebook> getAllSharedNoteBooksForCurrentUser();

	public Vector<Note> getAllNotesSharedByCurrentUser();

	public Vector<Notebook> getAllNoteBooksSharedByCurrentUser();

	// Zusätzliche Methoden zu NoteBook
	public Vector<Notebook> getAllNoteBooks();

	public Vector<Note> getAllFrom(Notebook nb);

	public Vector<Note> getAllNotes();
	
	public Vector<Note> getDueNotesForCurrentUser();

	public UserInfo saveUser(UserInfo user);

	public String getSource();

	public UserInfo getCurrentUser();
	
	public Vector<String> getAllEmails();

	void deleteUserInfo(UserInfo ui);

}
