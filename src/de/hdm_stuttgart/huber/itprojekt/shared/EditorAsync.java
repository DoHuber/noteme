package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Notebook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.util.Vector;

public interface EditorAsync {

    void createNote(Note note, AsyncCallback<Note> callback);

    void createNoteBook(Notebook noteBook, AsyncCallback<Notebook> callback);

    void deleteNote(Note note, AsyncCallback<Void> callback);

    void deleteNoteBook(Notebook noteBook, AsyncCallback<Void> callback);

    void getAllNoteBooks(AsyncCallback<Vector<Notebook>> callback);

    void getAllNotes(AsyncCallback<Vector<Note>> callback);

    void getHelloWorld(AsyncCallback<String> callback);

    void getNoteBookById(Notebook noteBook, AsyncCallback<Notebook> callback);

    void getNoteById(Note note, AsyncCallback<Note> callback);

    void init(AsyncCallback<Void> callback);

    void saveNote(Note note, AsyncCallback<Note> callback);

    void saveNoteBook(Notebook noteBook, AsyncCallback<Notebook> callback);

    void getAllNotesForCurrentUser(AsyncCallback<Vector<Note>> callback);

    void getAllNotesForUser(UserInfo user, AsyncCallback<Vector<Note>> callback);

    void getAllNoteBooksForCurrentUser(AsyncCallback<Vector<Notebook>> callback);

    void getAllFrom(Notebook nb, AsyncCallback<Vector<Note>> callback);

    void saveUser(UserInfo user, AsyncCallback<UserInfo> callback);

    void getAllSharedNotesForCurrentUser(AsyncCallback<Vector<Note>> callback);

    void getAllSharedNoteBooksForCurrentUser(AsyncCallback<Vector<Notebook>> callback);

    void getAllNoteBooksSharedByCurrentUser(AsyncCallback<Vector<Notebook>> callback);

    void getAllNotesSharedByCurrentUser(AsyncCallback<Vector<Note>> callback);

    void getSource(AsyncCallback<String> callback);

    void getCurrentUser(AsyncCallback<UserInfo> callback);

    void deleteUserInfo(UserInfo ui, AsyncCallback<Void> callback);

    void getAllEmails(AsyncCallback<Vector<String>> callback);

    void getDueNotesForCurrentUser(AsyncCallback<Vector<Note>> callback);

    void getAllNoteBooksFor(UserInfo u, AsyncCallback<Vector<Notebook>> callback);

}
