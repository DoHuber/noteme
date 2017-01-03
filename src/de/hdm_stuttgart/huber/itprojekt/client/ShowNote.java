package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm_stuttgart.huber.itprojekt.client.gui.RichTextToolbar;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;

public class ShowNote extends BasicView {
	/**
	 * @author Nikita Nalivayko
	 */
	/**
	 * Funktionen: Löschen, Editieren, Freigeben, Fähligkeitsdatum setzen -
	 * Ebene: einzelne Notizen
	 */
	
	private HorizontalPanel contentPanel = new HorizontalPanel();
	private VerticalPanel alignPanel = new VerticalPanel();
	private Button deleteButton = new Button(IconConstants.ICON_DELETE);
	private Button updateConfirmButton = new Button("Update");
	private Button shareButton = new Button(IconConstants.ICON_SHARE);

	private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	private RichTextArea noteArea = new RichTextArea();

	private TextBox titleTextBox = new TextBox();
	private TextBox subtitleTextBox = new TextBox();
	private RichTextToolbar richTextToolbar = new RichTextToolbar(noteArea);
	private DateBox dueDateBox = new DateBox();
	private Label empty = new Label("---                   ");
	private Label title = new Label("Title");
	private Label subtitle = new Label("Subtitle");
	private Label dueDate = new Label("Due Date");
	private Grid grid = new Grid(2, 1);
	private Note n = null;

	public ShowNote() {

	}

	public ShowNote(Note note) {
		this.n = note;

	}

	@Override
	public String getSubHeadlineText() {
		if (n.getNoteBook() == null) {
			return "There is no notebook deposited for the note.";
		} else {
			return "Notebook:" + n.getNoteBook().getTitle();
		}

	}

	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Note: " + n.getTitle();
	}

	@Override
	public void run() {

		// ButtonPanel buttonPanel = new ButtonPanel();
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(shareButton);
		buttonPanel.add(deleteButton);
		deleteButton.addClickHandler(new DeleteClickHandler());
		shareButton.addClickHandler(new ShareClickHandler());
		alignPanel.add(empty);

		alignPanel.add(title);
		alignPanel.add(titleTextBox);
		titleTextBox.setText(n.getTitle());
		alignPanel.add(subtitle);
		alignPanel.add(subtitleTextBox);
		subtitleTextBox.setText(n.getSubtitle());
		alignPanel.add(dueDate);
		alignPanel.add(dueDateBox);
		dueDateBox.setValue(n.getDueDate());
		noteArea.setText(n.getContent());
		grid.setWidget(0, 0, richTextToolbar);
		noteArea.setSize("100%", "100%px");
		grid.setWidget(1, 0, noteArea);

		alignPanel.add(updateConfirmButton);
		updateConfirmButton.addClickHandler(new UpdateClickHandler());

		contentPanel.add(alignPanel);
		contentPanel.add(grid);
		noteArea.setStyleName("noteArea");
	

		shareButton.setStyleName("pure-button");
		deleteButton.setStyleName("pure-button");
		updateConfirmButton.setStyleName("pure-button");

		empty.getElement().getStyle().setColor("#660033");

		RootPanel.get("main").add(buttonPanel);
		RootPanel.get("main").add(contentPanel);
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();

	}

	private class ShareClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MenuView mView = new MenuView();
			RootPanel.get("menu").clear();
			RootPanel.get("menu").add(mView);

			ShareNote sN = new ShareNote(n);
			RootPanel.get("main").clear();
			RootPanel.get("main").add(sN);

		}

	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("Möchten Sie die Notiz " + n.getTitle() + " wirklich löschen?")) {
				editorVerwaltung.deleteNote(n, new DeleteCallback());
			}
			MenuView navigation = new MenuView();
			RootPanel.get("menu").clear();
			RootPanel.get("menu").add(navigation);

			ShowAllNotes san = new ShowAllNotes();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(san);

		}

	}

	private class DeleteCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			contentPanel.add(new Label(caught.toString()));

		}

		@Override
		public void onSuccess(Void result) {

		}

	}

	private class UpdateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("Möchten Sie die Änderungen speichern?")) {
				updateNote();
			}

		}

	}

	public void updateNote() {
		n.setTitle(titleTextBox.getText());
		n.setSubtitle(subtitleTextBox.getText());
		n.setContent(noteArea.getText());
		java.util.Date utilDate = dueDateBox.getValue();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		n.setDueDate(sqlDate);
		// n.setNoteBook(nb);
		editorVerwaltung.saveNote(n, new UpdateCallback());

	}

	private class UpdateCallback implements AsyncCallback<Note> {

		@Override
		public void onFailure(Throwable caught) {

			GWT.log("Update failed because of:");
			GWT.log(caught.toString());

		}

		@Override
		public void onSuccess(Note result) {
			Window.alert("Saved");

		}

	}

}
