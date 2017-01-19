package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;

import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.IconConstants;
import de.hdm_stuttgart.huber.itprojekt.client.gui.NotebookTable;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

/**
 * Klasse zur Darstellung von Notizb�chern, gleiche Funktionsweise wie
 * "ShowAllNotes"
 * 
 * @author erdmann, nalivayko
 *
 */
public class ShowAllNotebooks extends BasicView {
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	AllNotebooksCallback callback = new AllNotebooksCallback();
	final Button sharedByBtn = new Button("Shared by me");
	final Button sharedWithBtn = new Button("Shared with me");
	final Button allNoteBooksBtn = new Button("All Notebooks");
	Button createNoteBookButton = new Button(IconConstants.ICON_ADD_NOTE);
	private Vector<NoteBook> notebook = new Vector<NoteBook>();

	public ShowAllNotebooks(Vector<NoteBook> nList) {
		notebook = nList;
	}

	/**
	 * No-Argument Konstruktor
	 */
	public ShowAllNotebooks() {

	}

	public ShowAllNotebooks(NoteBook selected) {

	}

	// Gibt alle Notizb�cher zurück
	public Vector<NoteBook> getAllNotebooksListe() {
		return notebook;

	}

	public void setAllNotesListe(Vector<NoteBook> liste) {
		this.notebook = liste;

	}

	@Override
	public String getHeadlineText() {

		return "MY NOTEBOOKS";
	}

	@Override
	public String getSubHeadlineText() {
		return "Select a notebook to have a look on your belonging notes";
	}

	@Override
	public void run() {
		FlowPanel contentPanel = new FlowPanel();
		FlowPanel fPanel2 = new FlowPanel();
		FlowPanel buttonsPanel = new FlowPanel();
		buttonsPanel.setStyleName("buttonsPanel");
		sharedWithBtn.addClickHandler(new SharedWithClickHandler());
		allNoteBooksBtn.addClickHandler(new AllNoteBooksClickHandler());
		sharedByBtn.addClickHandler(new SharedByClickHandler());
		createNoteBookButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				
				RootPanel.get("main").clear();
				RootPanel.get("main").add(new CreateNotebook());
				
			}
			
		});
		buttonsPanel.add(sharedByBtn);
		buttonsPanel.add(sharedWithBtn);
		buttonsPanel.add(allNoteBooksBtn);
		buttonsPanel.add(createNoteBookButton);
		fPanel2.add(buttonsPanel);
		fPanel2.add(contentPanel);

		editorVerwaltung.getAllNoteBooksForCurrentUser(callback);

		// NotebookTable nbt = new NotebookTable(notebook);
		// nbt.addClickNote();
		RootPanel.get("main").add(fPanel2);
		// RootPanel.get("main").add(nbt.start());
		// RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();
		RootPanel.get("tableNotebook1").clear();
	}

	private class AllNoteBooksClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			editorVerwaltung.getAllNoteBooksForCurrentUser(callback);

		}

	}

	private class AllNotebooksCallback implements AsyncCallback<Vector<NoteBook>> {
		@Override
		public void onSuccess(Vector<NoteBook> result) {
			addNoteBooksToTable(result);
		}

		@Override
		public void onFailure(Throwable caught) {
		}

	}

	private class SharedWithClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			SharedWithCallback callback = new SharedWithCallback();
			editorVerwaltung.getAllSharedNoteBooksForCurrentUser(callback);

		}

	}

	private class SharedWithCallback implements AsyncCallback<Vector<NoteBook>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<NoteBook> result) {
			addNoteBooksToTable(result);

		}

	}

	private class SharedByClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			SharedByCallback callback = new SharedByCallback();
			editorVerwaltung.getAllNoteBooksSharedByCurrentUser(callback);

		}

	}

	private class SharedByCallback implements AsyncCallback<Vector<NoteBook>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<NoteBook> result) {
			addNoteBooksToTable(result);

		}

	}

	public void addNoteBooksToTable(Vector<NoteBook> result) {
		notebook = result;
		NotebookTable ntB = new NotebookTable(notebook);
		ntB.addClickNote();
		RootPanel.get("table").clear();
		RootPanel.get("table").add(ntB.start());
	}

}
