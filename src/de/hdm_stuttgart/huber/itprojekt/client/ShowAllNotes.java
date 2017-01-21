package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import de.hdm_stuttgart.huber.itprojekt.client.gui.NoteTable;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

/**
 * Klasse zur Darstellung der Notizen, die vom Nutzer nicht explizit einem
 * ordner zugeordnet sind.
 * 
 * @author Nikita Nalivayko
 *
 */
public class ShowAllNotes extends BasicVerticalView {
	
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	AllNotesCallback callback = new AllNotesCallback();
	private Vector<Note> notes = new Vector<Note>();
	
	private NoteTable currentTable;

	public ShowAllNotes(Vector<Note> nList) {
		notes = nList;
	}

	/**
	 * No-Argument Konstruktor
	 */
	public ShowAllNotes() {

	}

	public ShowAllNotes(NoteBook selected) {

	}

	// Gibt alle Notizen zurück
	public Vector<Note> getAllNotesListe() {
		
		return notes;

	}

	public void setAllNotesListe(Vector<Note> liste) {
		this.notes = liste;

	}

	@Override
	public String getHeadlineText() {

		return "MY NOTES";
	}

	@Override
	public String getSubHeadlineText() {
		return "Select a note for more information!";
	}

	final Button sharedByBtn = new Button("Stuff I shared");
	final Button sharedWithBtn = new Button("Stuff shared with me");
	final Button allNotesBtn = new Button("All Notes");
	Button addNoteButton = new Button(IconConstants.ICON_ADD_NOTE);

	@Override
	public void run() {

		FlowPanel contentPanel = new FlowPanel();
		FlowPanel fPanel2 = new FlowPanel();
		FlowPanel buttonsPanel = new FlowPanel();
		
		sharedWithBtn.addClickHandler(new SharedWithClickHandler());
		allNotesBtn.addClickHandler(new AllNotesClickHandler());
		sharedByBtn.addClickHandler(new SharedByClickHandler());
		addNoteButton.addClickHandler(new CreateNoteHandler());
		
		buttonsPanel.add(sharedByBtn);
		buttonsPanel.add(sharedWithBtn);
		buttonsPanel.add(allNotesBtn);
		buttonsPanel.add(addNoteButton);
		
		fPanel2.add(buttonsPanel);
		fPanel2.add(contentPanel);

		editorVerwaltung.getAllNotesForCurrentUser(callback);
		this.add(fPanel2);
		
	}

	private class AllNotesClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			editorVerwaltung.getAllNotesForCurrentUser(callback);

		}

	}

	private class AllNotesCallback implements AsyncCallback<Vector<Note>> {
		@Override
		public void onSuccess(Vector<Note> result) {

			fillTableWith(result);
			
		}

		@Override
		public void onFailure(Throwable caught) {

			GWT.log(caught.toString());
			
		}

	}

	private class SharedWithClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			SharedWithCallback callback = new SharedWithCallback();
			editorVerwaltung.getAllSharedNotesForCurrentUser(callback);

		}

	}

	private class SharedWithCallback implements AsyncCallback<Vector<Note>> {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log(caught.toString());
		}

		@Override
		public void onSuccess(Vector<Note> result) {
			fillTableWith(result);

		}

	}

	private class SharedByClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			SharedByCallback callback = new SharedByCallback();
			editorVerwaltung.getAllNotesSharedByCurrentUser(callback);

		}

	}

	private class SharedByCallback implements AsyncCallback<Vector<Note>> {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log(caught.toString());
		}

		@Override
		public void onSuccess(Vector<Note> result) {
			fillTableWith(result);
		}

	}

	public void fillTableWith(Vector<Note> result) {
		
		if (currentTable != null) {
			this.remove(currentTable);
		}
		
		NoteTable nt = new NoteTable(result);
		nt.addClickNote();
		
		this.add(nt);
		currentTable = nt;

	}

}
