package de.hdm_stuttgart.huber.itprojekt.shared;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

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

	void createNote(String title, String subtitle, String content, String source, Date due_date, int notebook_id,
			int author_id, AsyncCallback<Void> callback);

}
