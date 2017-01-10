package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.shared.PermissionServiceAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;

public class ShowThisPermission extends BasicView {

	private VerticalPanel alignPanel = new VerticalPanel();
	private Button deleteBtn = new Button("Unshare");
	private Label level;
	private Label user;
	private Label object;
	
	PermissionServiceAsync permissionVerwaltung = ClientsideSettings.getPermissionVerwaltung();
	private Permission p;
	private Note note;
	private NoteBook notebook;

	public ShowThisPermission() {

	}

	public ShowThisPermission(Permission p) {
		this.p = p;
	}

	@Override
	public String getHeadlineText() {
		
		// Nicht null returnen! Java ist nur unbeliebt in der Welt 
		// weil es die NullPointerException gibt (und StackOverflowError bei alten Leuten)
		// (Und weil alle mit JavaScript "entwicklen" heutzutage)
		return "";
	}

	@Override
	public String getSubHeadlineText() {

		return "";
	}

	@Override
	public void run() {
		
		// TODO Zu viel Logik im Client
		String levelAsString = null;
		if (p.getLevelAsInt() == 10) {
			levelAsString = "Read";
		} else if (p.getLevelAsInt() == 20) {
			levelAsString = "Edit";
		} else if (p.getLevelAsInt() == 30) {
			levelAsString = "Delete";
		}

		String noteB = String.valueOf(p.getSharedObject().getType());
		
		// TODO zu viel Logik im Client
		String typeAsVerboseString;
		if (noteB == "b") {
			notebook = (NoteBook) p.getSharedObject();
			typeAsVerboseString = "Notebook: " + notebook.getTitle();
		} else {
			note = (Note) p.getSharedObject();
			typeAsVerboseString = "Note: " + note.getTitle();
		}
		
		setUpLabels(levelAsString, typeAsVerboseString);
		
		setUpAlignPanel();
		
		RootPanel.get("main").add(alignPanel);
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();

	}

	private void setUpLabels(String levelAsString, String typeAsVerboseString) {
		
		user = new Label("Shared with: " + p.getBeneficiary().getNickname());
		level = new Label("The User can: " + levelAsString);
		object = new Label("the following entity: " + typeAsVerboseString);
		
	}

	private void setUpAlignPanel() {
		alignPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		alignPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		alignPanel.add(user);
		alignPanel.add(level);
		alignPanel.add(object);
		
		alignPanel.add(deleteBtn);
		deleteBtn.addClickHandler(new DeleteClickHandler());
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			// TODO durch AlertDialog (oder wie es in GWT genau heißt) ersetzen
			if (Window.confirm("Do you really want to unshare this?")) {
				permissionVerwaltung.deletePermission(p, new DeleteCallback());
			}

		}
	}

	private class DeleteCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			
			Notificator.getNotificator().showError("Unsharing failed!");
			GWT.log(caught.toString());

		}

		@Override
		public void onSuccess(Void result) {
			
			Notificator.getNotificator().showSuccess("Unshared successfully!");
			
			ShowAllNotes san = new ShowAllNotes();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(san);

		}

	}
}
