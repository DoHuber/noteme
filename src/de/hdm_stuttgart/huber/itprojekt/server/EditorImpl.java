package de.hdm_stuttgart.huber.itprojekt.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm_stuttgart.huber.itprojekt.server.db.NoteBookMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.NoteMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.PermissionMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.UserInfoMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.Editor;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Notebook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Implementation der Editor-Funktionalität serverseitig. Die Dokumenation im {@link Editor} - Interface
 * sollte folglich bis auf Helfermethoden auch für diese Klasse genügen.
 *
 * @see Editor
 */
public class EditorImpl extends RemoteServiceServlet implements Editor {

    /**
     * Hat eclipse automatisch generiert
     */
    private static final long serialVersionUID = 1L;

    private NoteMapper noteMapper;
    private NoteBookMapper noteBookMapper;
    private UserInfoMapper userInfoMapper;
    private PermissionMapper permissionMapper;

    @Override
    public void init() throws IllegalArgumentException {

        this.noteMapper = NoteMapper.getNoteMapper();
        this.noteBookMapper = NoteBookMapper.getNoteBookMapper();
        this.userInfoMapper = UserInfoMapper.getUserInfoMapper();
        this.permissionMapper = PermissionMapper.getPermissionMapper();

    }

    @Override
    @Deprecated
    public String getHelloWorld() {

        // Sinnlose Methode für Testzwecke (Paradoxon), gibt einen zufälligen String zurück
        SecureRandom random = new SecureRandom();
        return new BigInteger(256, random).toString();

    }

    @Override
    public Notebook createNoteBook(Notebook noteBook) {

        Date currentDate = new Date(System.currentTimeMillis());
        noteBook.setCreationDate(currentDate);
        noteBook.setModificationDate(currentDate);
        noteBook.setOwner(getCurrentUser());

        return noteBookMapper.create(noteBook);
    }

    @Override
    public Notebook saveNoteBook(Notebook noteBook) {

        try {

            return noteBookMapper.save(noteBook);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Notebook getNoteBookById(Notebook notebook) throws Exception {

        return noteBookMapper.findById(notebook.getId());
    }

    @Override
    public void deleteNoteBook(Notebook noteBook) {

        Vector<Note> notesToDelete = getAllFrom(noteBook);
        for (Note row : notesToDelete) {
            noteMapper.delete(row);
        }

        noteBookMapper.delete(noteBook);

    }

    @Override
    public Note createNote(Note note) {

        Date currentDate = new Date(System.currentTimeMillis());

        note.setCreationDate(currentDate);
        note.setModificationDate(currentDate);
        note.setOwner(this.getCurrentUser());

        try {

            return noteMapper.create(note);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // TODO
    }

    @Override
    public Note saveNote(Note note) {

        // Aus Gründen der Sichtbarkeit (oder war das PHP?)
        Note newNote = note;

        try {

            noteMapper.save(newNote);
            newNote = noteMapper.findById(newNote.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newNote;
    }

    @Override
    public Note getNoteById(Note note) throws Exception {

        return noteMapper.findById(note.getId());

    }

    @Override
    public void deleteNote(Note note) {

        noteMapper.delete(note);

    }

    @Override
    public Vector<Note> getAllNotes() {

        try {

            return noteMapper.getAllNotes();

        } catch (Exception e) {

            e.printStackTrace();
            return new Vector<Note>();
        }

    }

    @Override
    public Vector<Notebook> getAllNoteBooks() {

        try {
            return noteBookMapper.getAllNoteBooks();
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Vector<Note> getAllNotesForCurrentUser() {

        UserInfo currentUser = getCurrentUser();
        return noteMapper.getAllNotesForUserId(currentUser.getId());

    }

    @Override
    public Vector<Notebook> getAllNoteBooksFor(UserInfo u) {

        return noteBookMapper.getAllNoteBooksForUserId(u.getId());
    }

    @Override
    public UserInfo getCurrentUser() {

        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            throw new InvalidLoginStatusException("Kein User eingeloggt. Funktion an falscher Stelle verwendet?");
        }

        User currentGoogleUser = userService.getCurrentUser();

        // Da durch die Logik sichergestellt ist, dass jeder eingeloggte User
        // registriert ist,
        // ist das hier problemlos möglich

        return userInfoMapper.findUserByGoogleId(currentGoogleUser.getUserId());

    }

    @Override
    public Vector<Notebook> getAllNoteBooksForCurrentUser() {

        UserInfo currentUser = getCurrentUser();
        return noteBookMapper.getAllNoteBooksForUserId(currentUser.getId());

    }

    @Override
    public Vector<Note> getAllFrom(Notebook nb) {

        int noteBookId = nb.getId();
        return noteMapper.getAllNotesForNoteBookId(noteBookId);

    }

    @Override
    public UserInfo saveUser(UserInfo user) {

        try {

            return userInfoMapper.save(user);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Erläuterungen zu den beiden folgenden Methoden: Mit den Objekten von Note
     * oder NoteBook wird jeweils noch ein Permission-Objekt, dass die aktuellen
     * Berechtigungen des eingeloggten Users beschreibt, mitgegeben.
     * <p>
     * Somit ist dem Client ermöglicht, komfortabel zu prüfen, ob der aktuelle
     * User gewisse Operationen für bestimmte Objekte durchführen darf.
     */

    @Override
    public Vector<Note> getAllSharedNotesForCurrentUser() {

        Vector<Note> v = noteMapper.getAllNotesSharedWith(getCurrentUser());
        for (Note row : v) {

            Permission p = permissionMapper.getPermissionFor(getCurrentUser(), row);
            row.setRunTimePermission(p);

        }

        return v;
    }

    @Override
    public Vector<Notebook> getAllSharedNoteBooksForCurrentUser() {

        Vector<Notebook> v = noteBookMapper.getAllNoteBooksSharedWith(getCurrentUser());
        for (Notebook row : v) {

            Permission p = permissionMapper.getPermissionFor(getCurrentUser(), row);
            row.setRunTimePermission(p);

        }

        return v;
    }

    @Override
    public Vector<Note> getAllNotesSharedByCurrentUser() {

        UserInfo u = getCurrentUser();
        return noteMapper.getAllNotesSharedBy(u);

    }

    @Override
    public Vector<Notebook> getAllNoteBooksSharedByCurrentUser() {

        return noteBookMapper.getAllNoteBooksSharedBy(getCurrentUser());

    }

    @Override
    public String getSource() {

        HttpSession session = this.getThreadLocalRequest().getSession();
        String source = (String) session.getAttribute("source");
        
        System.out.println("Session object: " + session.toString());

        if (source == null) {
        	
        	System.out.println("Source string found to be null.");
            source = "none";
            
        }

        // Attribut entfernen, damit der Client nicht verwirrt wird
        session.removeAttribute("source");

        return source;

    }

    @Override
    public Vector<String> getAllEmails() {
        return userInfoMapper.getAllEmailAdresses();
    }

    @Override
    public Vector<Note> getAllNotesForUser(UserInfo user) {

        return noteMapper.getAllNotesForUser(user.getId());
    }

    @Override
    public void deleteUserInfo(UserInfo ui) {
        // TODO Auto-generated method stub
        Vector<Notebook> vector = noteBookMapper.getAllNoteBooksForUserId(ui.getId());
        for (Notebook nb : vector) {
            deleteNoteBook(nb);
        }

        Vector<Note> vectorNotes = noteMapper.getAllNotesForUserId(ui.getId());
        for (Note n : vectorNotes) {
            deleteNote(n);
        }

        Vector<Permission> vectorPermissions = permissionMapper.getAllPermissionsCreatedBy(ui);
        for (Permission p : vectorPermissions) {
            permissionMapper.deletePermission(p);
        }

        Vector<Permission> vectorPerm = permissionMapper.getAllPermissionsFor(ui);
        for (Permission p : vectorPerm) {
            permissionMapper.deletePermission(p);
        }

        try {
            userInfoMapper.delete(ui);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vector<Note> getDueNotesForCurrentUser() {

        Date today = new Date(System.currentTimeMillis());
        Vector<Note> dueNotes = new Vector<>();

        Vector<Note> allNotesCurrentUser = getAllNotesForCurrentUser();

        if (allNotesCurrentUser == null || allNotesCurrentUser.isEmpty()) {
            return new Vector<Note>();
        }

        for (Note n : allNotesCurrentUser) {

            if (n.getDueDate() != null) {

                if (n.getDueDate().before(today) || n.getDueDate().equals(today)) {
                    dueNotes.add(n);
                }

            }

        }

        return dueNotes;

    }

}
