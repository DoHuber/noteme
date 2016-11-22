package de.hdm_stuttgart.huber.itprojekt.server;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;

import de.hdm_stuttgart.huber.itprojekt.shared.Editor;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

public class EditorImpl implements Editor {

	public EditorImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
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
	public NoteBook getNoteBookById(NoteBook noteBook) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteNoteBook(NoteBook noteBook) {
		// TODO Auto-generated method stub

	}

	@Override
	public Note createNote(Note note) {
		
		// Gibt die ak
		
		return null;
	}

	@Override
	public Note saveNote(Note note) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note getNoteById(Note note) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteNote(Note note) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector<NoteBook> getAllNoteBooks() {
		// TODO Auto-generated method stub
		return null;
	}

}
