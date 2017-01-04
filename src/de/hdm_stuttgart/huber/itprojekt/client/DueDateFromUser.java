package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.NoteTable;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public class DueDateFromUser extends BasicView {
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	AllNotesCallback callback = new AllNotesCallback();
	private Vector<Note> notes = new Vector<Note>();
	private UserInfo ui = null;
	public DueDateFromUser(Vector<Note> nList) {
		notes = nList;
	}
	
	public DueDateFromUser(UserInfo ui ){
		this.ui = ui;
	}
	public DueDateFromUser (){
		
	}
	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Welcome: "+ ui.getFirstName() +" " + ui.getSurName();
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		editorVerwaltung.getAllNotesForUser(callback);
	}
	private class AllNotesCallback implements AsyncCallback<Vector<Note>> {
		@Override
		public void onSuccess(Vector<Note> result) {
			addNotesToTable(result);
		
		}

		@Override
		public void onFailure(Throwable caught) {


		}

	}
	public void addNotesToTable(Vector<Note> result) {
		notes = result;
		NoteTable nt = new NoteTable(notes);
		nt.addClickNote();
		RootPanel.get("table").clear();
		RootPanel.get("table").add(nt.start());

	}

	public Vector<Note> getNotes() {
		return notes;
	}

	public void setNotes(Vector<Note> notes) {
		this.notes = notes;
	}

}
