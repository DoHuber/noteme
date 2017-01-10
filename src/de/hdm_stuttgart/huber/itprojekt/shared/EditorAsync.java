package de.hdm_stuttgart.huber.itprojekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public interface EditorAsync {

	void createNote(Note note, AsyncCallback<Note> callback);

	void createNoteBook(NoteBook noteBook, AsyncCallback<NoteBook> callback);

	void deleteNote(Note note, AsyncCallback<Void> callback);

	void deleteNoteBook(NoteBook noteBook, AsyncCallback<Void> callback);

	void getAllNoteBooks(AsyncCallback<Vector<NoteBook>> callback);

	void getAllNotes(AsyncCallback<Vector<Note>> callback);

	void getHelloWorld(AsyncCallback<String> callback);

	void getNoteBookById(NoteBook noteBook, AsyncCallback<NoteBook> callback);

	void getNoteById(Note note, AsyncCallback<Note> callback);

	void init(AsyncCallback<Void> callback);

	void saveNote(Note note, AsyncCallback<Note> callback);

	void saveNoteBook(NoteBook noteBook, AsyncCallback<NoteBook> callback);

	void getAllNotesForCurrentUser(AsyncCallback<Vector<Note>> callback);
	void getAllNotesForUser(AsyncCallback<Vector<Note>> callback);

	void getAllNoteBooksForCurrentUser(AsyncCallback<Vector<NoteBook>> callback);

	void getAllFrom(NoteBook nb, AsyncCallback<Vector<Note>> callback);

	void saveUser(UserInfo user, AsyncCallback<UserInfo> callback);

	void getAllSharedNotesForCurrentUser(AsyncCallback<Vector<Note>> callback);

	void getAllSharedNoteBooksForCurrentUser(AsyncCallback<Vector<NoteBook>> callback);

	void getAllNoteBooksSharedByCurrentUser(AsyncCallback<Vector<NoteBook>> callback);

	void getAllNotesSharedByCurrentUser(AsyncCallback<Vector<Note>> callback);

	void getSource(AsyncCallback<String> callback);

	void getCurrentUser(AsyncCallback<UserInfo> callback);

	void deleteUserInfo(UserInfo ui, AsyncCallback<Void> callback);
}
