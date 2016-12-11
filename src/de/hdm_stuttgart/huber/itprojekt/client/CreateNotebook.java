package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
	private EditorAsync editorVerwaltung  = ClientsideSettings.getEditorVerwaltung();

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
		createButton.addClickHandler(new CreateClickHandler());
		RootPanel.get().add(vPanel);

	}

	@Override
	public String getHeadlineText() {

		return "Create New Notebook";
	}

	@Override
	public String getSubHeadlineText() {

		return "Sinnvoller Text!";
	}
	//Clickhandler für CreateButton 
	private class CreateClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			createNotebook();
			
		}
	}
	//Neues Notizbuch wird erstellt 
	public void createNotebook(){
		NoteBook nb = new NoteBook();
//		nb.setTitle(titleTextBox.getText());
//		nb.setSubtitle(subtitleTextBox.getText());
		editorVerwaltung.createNoteBook(nb, new CreateNotebookCallback());
	}
	/**
	 * 
	 * Klasse die den callback zum Notizbuch anlegen implementiert. Das angelegte Notizbuch wird
	 * an die EditorImpl übergeben. 
	 *
	 */
	private class CreateNotebookCallback implements AsyncCallback<NoteBook>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(NoteBook result) {
			
		
			
		}
		
	}

}
