package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.NoteTable;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;

public class ShowNotebook extends BasicView {

	/**
	 * @author Erdmann, Nalivayko
	 */
	/**
	 * Funktionen: Löschen, Editieren, Freigeben,
	 */
	private HorizontalPanel horizontalPanel = new HorizontalPanel();
	private Button deleteButton = new Button(IconConstants.ICON_DELETE);
	private Button updateConfirmButton = new Button("Save");
	private Button shareButton = new Button(IconConstants.ICON_SHARE);

	private Button createButton = new Button(IconConstants.ICON_ADD_NOTE);
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	
	NoteBook displayedNoteBook = new NoteBook();
	private TextBox title = new TextBox();
	private TextBox subtitle = new TextBox();
	AllNotesCallback callback = new AllNotesCallback();

	private Vector<Note> notes = new Vector<Note>();
	private HorizontalPanel actionButtons;
	private VerticalPanel titlesEditInterface;
	private HorizontalPanel wrapper = new HorizontalPanel();

	public ShowNotebook(NoteBook noteBookToDisplay) {
		this.displayedNoteBook = noteBookToDisplay;
	}

	@Override
	public String getHeadlineText() {

		return "Notizbuch: " + displayedNoteBook.getTitle();
	}

	@Override
	public String getSubHeadlineText() {
		
		return "Subtitle: " + displayedNoteBook.getSubtitle();
	
	}

	@Override
	public void run() {
		
		setupButtonClickHandlers();
		
		actionButtons = setupActionButtons();
		titlesEditInterface = setupEditField();
		
		setUpLayoutWithWrapperPanel();
		
		addEverythingToRootPanel();
	
		
		if (displayedNoteBook.hasRuntimePermission()) {
			setUpForPermissions();
		}
		
		editorVerwaltung.getAllFrom(displayedNoteBook, callback);
	}
	
	private void setUpForPermissions() {
		
		shareButton.setEnabled(false);
		Permission p = displayedNoteBook.getRuntimePermission();
		
		if (!p.isUserAllowedTo(Level.DELETE)) {
			deleteButton.setEnabled(false);
		}
		
		if (!p.isUserAllowedTo(Level.EDIT)) {
			createButton.setEnabled(false);
			title.setEnabled(false);
			subtitle.setEnabled(false);
			updateConfirmButton.setVisible(false);
		}
		
	}

	private void addEverythingToRootPanel() {
		RootPanel.get("main").add(wrapper);
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();
	}

	private void setUpLayoutWithWrapperPanel() {
		wrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		wrapper.add(actionButtons);
		wrapper.add(titlesEditInterface);
		wrapper.setWidth("100%");
	}
	
	private VerticalPanel setupEditField() {
		
		title.setTitle("Title");
		subtitle.setTitle("Subtitle");
		
		title.setValue(displayedNoteBook.getTitle());
		subtitle.setValue(displayedNoteBook.getSubtitle());
		
		VerticalPanel vp = new VerticalPanel();
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		vp.add(new Label("Edit this notebook:"));
		vp.add(title);
		vp.add(subtitle);
		vp.add(updateConfirmButton);

		return vp;
	}

	private void setupButtonClickHandlers() {
		deleteButton.addClickHandler(new DeleteClickHandler());
		shareButton.addClickHandler(new ShareNotebookClickHandler());
		createButton.addClickHandler(new CreateNoteClickHandler());
		updateConfirmButton.addClickHandler(new UpdateClickHandler());
	}
	
	private HorizontalPanel setupActionButtons() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		hp.add(deleteButton);
		hp.add(createButton);
		hp.add(shareButton);
		
		return hp;
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

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			// TODO AlertDialog statt Window. -> Bekannte GWT-Issue, friert alles ein im Super Dev Mode
			// Wegen nichtkonformer JavaScript-Implementation in Firefox (Es ist 2017!!)
			if (Window.confirm("Möchten Sie das Notizbuch " + displayedNoteBook.getTitle() + " wirklich löschen?")) {
				editorVerwaltung.deleteNoteBook(displayedNoteBook, new DeleteCallback());
			}
			
		}
	}

	public void addNotesToTable(Vector<Note> result) {
		
		notes = result;
		
		NoteTable nt = new NoteTable(notes);
		nt.addClickNote();
		RootPanel.get("tableNotebook").add(nt.start());
	
	}

	private class DeleteCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notificator.getNotificator().showError("Löschen des Notizbuches fehlgeschlagen!");
			GWT.log(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			Notificator.getNotificator().showSuccess("Notizbuch gelöscht.");
			
			// Das muss hier geschehen, da sonst eine Race Condition
			// zwischen Delete-Callback und ShowAllNotes-Callback auftritt,
			// die zu "Geisternotizbüchern" führen kann, d.h. es werden eigentlich gelöschte
			// Notizbücher clientseitig noch angezeigt, da der ShowAllNotes-Callback schneller
			// als der Delete-Callback war
			ShowAllNotebooks san = new ShowAllNotebooks();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(san);

		}
	}

	private class UpdateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			if (Window.confirm("Möchten Sie die Änderungen speichern?")) {

			}
			displayedNoteBook.setTitle(title.getText());
			displayedNoteBook.setSubtitle(subtitle.getText());
			editorVerwaltung.saveNoteBook(displayedNoteBook, new UpdateCallback());

		}

	}

	private class UpdateCallback implements AsyncCallback<NoteBook> {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("Speichern des Notizbuches fehlgeschlagen! s.u.:");
			GWT.log(caught.toString());
		}

		@Override
		public void onSuccess(NoteBook result) {
			Window.alert("Saved");

		}

	}

	private class CreateNoteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MenuView mView = new MenuView();
			RootPanel.get("menu").clear();
			RootPanel.get("menu").add(mView);

			CreateNote cN = new CreateNote(displayedNoteBook);
			RootPanel.get("main").clear();
			RootPanel.get("main").add(cN);

		}
	}

	private class ShareNotebookClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			ShareShareable sNb = new ShareShareable(displayedNoteBook);
			RootPanel.get("main").clear();
			RootPanel.get("main").add(sNb);

		}

	}
}
