package de.hdm_stuttgart.huber.itprojekt.server;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm_stuttgart.huber.itprojekt.server.db.NoteBookMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.NoteMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.UserInfoMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.BullshitException;
import de.hdm_stuttgart.huber.itprojekt.shared.Editor;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public class EditorImpl extends RemoteServiceServlet implements Editor {
	
	/**
	 * AUTO-GENERATED
	 */
	private static final long serialVersionUID = 1L;
	
	
	private NoteMapper noteMapper;
	private NoteBookMapper notebookmapper;
	private UserInfoMapper userInfoMapper;
	
	@Override
	public void init() throws IllegalArgumentException {
		
		try {
			this.noteMapper = NoteMapper.getNoteMapper();
			this.notebookmapper = NoteBookMapper.getNoteBookMapper();
			this.userInfoMapper = UserInfoMapper.getUserInfoMapper();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	@Deprecated
	public String getHelloWorld() {
		// Sinnlose Methode, gibt einen zufälligen String zurück
		// Zu Testzwecken, wird dann bezeiten rausgeworfen
		SecureRandom random = new SecureRandom();
		return new BigInteger(256, random).toString();
	}

	@Override
	public NoteBook createNoteBook(NoteBook noteBook) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoteBook saveNoteBook(NoteBook noteBook) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoteBook getNoteBookById(NoteBook notebook) throws Exception {
		
		return notebookmapper.findById(notebook.getId());
	}
	@Override
	public void deleteNoteBook(NoteBook noteBook) {
		// TODO Auto-generated method stub

	}

	@Override
	public Note createNote(Note note) {
		
		Note newNote = note;
	
		try {
			
			noteMapper.create(newNote);
			newNote = noteMapper.findById(newNote.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return newNote;
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
		
		try {
			noteMapper.delete(note);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}

	@Override
	public Vector<Note> getAllNotes() throws BullshitException {
		
		try {
			
			return noteMapper.getAllNotes();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new BullshitException(e.toString());
		}
		
	}

	@Override
	public Vector<NoteBook> getAllNoteBooks() throws BullshitException{
		
		try {
			return notebookmapper.getAllNoteBooks();
		} catch (Exception e){
			
			e.printStackTrace();
			throw new BullshitException(e.toString());
		}
	}

	@Override
	public void createNote(String title, String subtitle, String content, String source, Date due_date, int notebook_id,
			int author_id) {
		
		// TODO
		
	}
	
	@Override
	public Vector<Note> getAllNotesForCurrentUser() {
		
		UserInfo currentUser = getCurrentUser();
		return noteMapper.getAllNotesForUserId(currentUser.getId());
		
	}
	
	private UserInfo getCurrentUser() {
		
		UserService userService = UserServiceFactory.getUserService();
		if (!userService.isUserLoggedIn()) {
			throw new InvalidLoginStatusException("Kein User eingeloggt. Funktion an falscher Stelle verwendet?");
		}
		
		User currentGoogleUser = userService.getCurrentUser();
		
		// Da durch die Logik sichergestellt ist, dass jeder eingeloggte User registriert ist,
		// ist das hier problemlos möglich
		
		return userInfoMapper.findUserByGoogleId(currentGoogleUser.getUserId());
		
	}
	

}
