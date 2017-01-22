package de.hdm_stuttgart.huber.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Notebook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.util.Vector;

/**
 * @author elcpt
 */

@RemoteServiceRelativePath("testEditor")
public interface Editor extends RemoteService {

    void init() throws IllegalArgumentException;

    String getHelloWorld();

    // CRUD-Methoden für NoteBook
    Notebook createNoteBook(Notebook notebook);

    Notebook saveNoteBook(Notebook noteBook);

    Notebook getNoteBookById(Notebook notebook) throws Exception;

    void deleteNoteBook(Notebook noteBook);

    // CRUD-Methoden für Note
    Note createNote(Note note);

    Note saveNote(Note note);

    Note getNoteById(Note note) throws Exception;

    void deleteNote(Note note);

    // CRUD-Methoden nach User, nur nach Login verwendbar!
    Vector<Note> getAllNotesForCurrentUser();

    Vector<Note> getAllNotesForUser(UserInfo user);

    Vector<Notebook> getAllNoteBooksForCurrentUser();

    Vector<Notebook> getAllNoteBooksFor(UserInfo u);

    // Methoden mit Freigabe dabei
    Vector<Note> getAllSharedNotesForCurrentUser();

    Vector<Notebook> getAllSharedNoteBooksForCurrentUser();

    Vector<Note> getAllNotesSharedByCurrentUser();

    Vector<Notebook> getAllNoteBooksSharedByCurrentUser();

    // Zusätzliche Methoden zu NoteBook
    Vector<Notebook> getAllNoteBooks();

    Vector<Note> getAllFrom(Notebook nb);

    Vector<Note> getAllNotes();

    Vector<Note> getDueNotesForCurrentUser();

    UserInfo saveUser(UserInfo user);

    String getSource();

    UserInfo getCurrentUser();

    Vector<String> getAllEmails();

    void deleteUserInfo(UserInfo ui);

}
