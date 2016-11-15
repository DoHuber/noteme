/**
 * 
 */
package de.hdm_stuttgart.huber.itprojekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

/**
 * @author elcpt
 *
 */
public interface EditorAsync {

	/**
	 * 
	 * @see de.hdm_stuttgart.huber.itprojekt.shared.Editor#createNoteBook(de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook)
	 */
	void createNoteBook(NoteBook noteBook, AsyncCallback<NoteBook> callback);

	/**
	 * 
	 * @see de.hdm_stuttgart.huber.itprojekt.shared.Editor#deleteNoteBook(de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook)
	 */
	void deleteNoteBook(NoteBook noteBook, AsyncCallback<Void> callback);

	/**
	 * 
	 * @see de.hdm_stuttgart.huber.itprojekt.shared.Editor#getAllNoteBooks()
	 */
	void getAllNoteBooks(AsyncCallback<Vector<NoteBook>> callback);

	/**
	 * 
	 * @see de.hdm_stuttgart.huber.itprojekt.shared.Editor#getHelloWorld()
	 */
	void getHelloWorld(AsyncCallback<String> callback);

	/**
	 * 
	 * @see de.hdm_stuttgart.huber.itprojekt.shared.Editor#getNoteBookById(de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook)
	 */
	void getNoteBookById(NoteBook noteBook, AsyncCallback<NoteBook> callback);

	/**
	 * 
	 * @see de.hdm_stuttgart.huber.itprojekt.shared.Editor#init()
	 */
	void init(AsyncCallback<Void> callback);

	/**
	 * 
	 * @see de.hdm_stuttgart.huber.itprojekt.shared.Editor#saveNoteBook(de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook)
	 */
	void saveNoteBook(NoteBook noteBook, AsyncCallback<NoteBook> callback);

}
