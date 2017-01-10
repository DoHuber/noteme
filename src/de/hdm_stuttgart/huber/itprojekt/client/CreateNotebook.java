package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

/**
 * Notizbuch anlegen
 * 
 * @author Nikita Nalivayko
 *
 */
public class CreateNotebook extends BasicView {

	private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	private VerticalPanel vPanel = new VerticalPanel();
	private TextBox titleTextBox = new TextBox();
	private TextBox subtitleTextBox = new TextBox();
	private Button createButton = new Button("Create");
	private Label title = new Label("Title");
	private Label subtitle = new Label("Subtitle");
	// Label lb = new Label("Hier wird ein neues Notizbuch erstellt ");

	@Override
	public void run() {
		/*
		 * Notizbuch anlegen Widgets
		 * 
		 */
		vPanel.add(title);
		vPanel.add(titleTextBox);
		vPanel.add(subtitle);
		vPanel.add(subtitleTextBox);
		vPanel.add(createButton);
		// hPanel.add(vPanel);
		createButton.addClickHandler(new CreateClickHandler());

		RootPanel.get("main").add(vPanel);
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();
	}

	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return "CREATE A NOTEBOOK";
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return "Give your notebook a title and subtitle to complete!";
	}

	// Clickhandler für CreateButton
	private class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			createNotebook();

		}
	}

	// Neues Notizbuch wird erstellt
	public void createNotebook() {

		NoteBook nb = new NoteBook();

		nb.setTitle(titleTextBox.getText());
		nb.setSubtitle(subtitleTextBox.getText());

		editorVerwaltung.createNoteBook(nb, new CreateNotebookCallback());

	}

	/**
	 * 
	 * Klasse die den callback zum Notizbuch anlegen implementiert. Das
	 * angelegte Notizbuch wird an die EditorImpl übergeben.
	 *
	 */
	private class CreateNotebookCallback implements AsyncCallback<NoteBook> {
		
		private Notificator notificator = Notificator.getNotificator();

		@Override
		public void onFailure(Throwable caught) {

			notificator.showError("Erstellen fehlgeschlagen.");
			GWT.log(caught.toString());
			
		}

		@Override
		public void onSuccess(NoteBook result) {

			notificator.showSuccess("NoteBook " + result.getTitle() + " created successfully.");
			
			ShowAllNotebooks san = new ShowAllNotebooks();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(san);

		}

	}

}
