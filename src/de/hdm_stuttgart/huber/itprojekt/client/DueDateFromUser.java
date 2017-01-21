package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import de.hdm_stuttgart.huber.itprojekt.client.gui.NoteTable;
import de.hdm_stuttgart.huber.itprojekt.client.gui.Notificator;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

public class DueDateFromUser extends BasicVerticalView {
	
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	DueNotesCallback callback = new DueNotesCallback();
	private Vector<Note> notes = new Vector<Note>();
	private UserInfo ui = null;

	public DueDateFromUser(Vector<Note> nList) {
		notes = nList;
	}

	public DueDateFromUser(UserInfo ui) {
		this.ui = ui;
	}

	public DueDateFromUser() {

	}

	@Override
	public String getHeadlineText() {

		return "Welcome: " + ui.getFirstName() + " " + ui.getSurName();
	}

	@Override
	public String getSubHeadlineText() {

		return "Notes that are due today or in the past:";
	}

	@Override
	public void run() {

		editorVerwaltung.getDueNotesForCurrentUser(callback);

	}

	private class DueNotesCallback implements AsyncCallback<Vector<Note>> {

		@Override
		public void onSuccess(Vector<Note> result) {

			if (result.isEmpty()) {
	
				Label l = new Label("Nothing due today or in the past!");
				DueDateFromUser.this.add(l);
				
			} else {

				addNotesToTable(result);
			}

		}

		@Override
		public void onFailure(Throwable caught) {

			Notificator.getNotificator().showError("Connection or Server Problems!");
			GWT.log(caught.toString());

		}

	}

	public void addNotesToTable(Vector<Note> result) {
		
		notes = result;
		NoteTable nt = new NoteTable(notes);
		nt.addClickNote();
		
		this.add(nt);

	}

	public Vector<Note> getNotes() {
		return notes;
	}

	public void setNotes(Vector<Note> notes) {
		this.notes = notes;
	}

}
