package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
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
	private Button deleteBtn = new Button("Delete");
	private Button editBtn = new Button("Update");
	private Button releseBtn = new Button("Release");
	
	private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	private RichTextArea noteArea = new RichTextArea();
	
	private TextBox titleTextBox = new TextBox();
	private TextBox subtitleTextBox = new TextBox();
	private RichTextToolbar richTextToolbar = new RichTextToolbar(noteArea);
	private DateBox dueDateBox = new DateBox();
	private Label empty = new Label ("-    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -");
	private Label title = new Label("Title");
	private Label subtitle = new Label("Subtitle");
	private Label dueDate = new Label("Due Date");
	private Grid grid = new Grid(2,1);
	private Note n = null;

	public ShowNote () {
		
	}
	
	public ShowNote(Note note) {
		this.n = note;
		
	}

	@Override
	public String getSubHeadlineText() {
		if (n.getNoteBook()==null){
			return "There is no notebook deposited for the note.";
		}
		else{
			return "Notebook:"+ n.getNoteBook().getTitle();
		}
		
	}

	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Note: "+n.getTitle();
	}
	

	@Override
	public void run() {
		
		//ButtonPanel buttonPanel = new ButtonPanel();
		FlowPanel gesamtPanel = new FlowPanel();
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(releseBtn);
		buttonPanel.add(deleteBtn);
		deleteBtn.addClickHandler(new DeleteClickHandler());
		
		alignPanel.add(empty);
		alignPanel.add(title);
		alignPanel.add(titleTextBox);

		alignPanel.add(subtitle);
		alignPanel.add(subtitleTextBox);

		alignPanel.add(dueDate);
		alignPanel.add(dueDateBox);
		grid.setWidget(0, 0, richTextToolbar);
		noteArea.setSize("100%", "100%px");
		grid.setWidget(1, 0, noteArea);
		
		alignPanel.add(editBtn);
		editBtn.addClickHandler(new UpdateClickHandler());
		
		
		contentPanel.add(alignPanel);
		//alignPanel.add(buttonPanel);
//		contentPanel.add(titleTextBox);
//		contentPanel.add(subtitleTextBox);
//		contentPanel.add(dueDateBox);
		contentPanel.add(grid);
//		contentPanel.add(noteArea);
		noteArea.setStyleName("noteArea");
		
//		gesamtPanel.add(contentPanel);
//		gesamtPanel.add(alignPanel);
//		gesamtPanel.setStyleName("gesamtPanel");
//		
//		RootPanel.get("main").add(gesamtPanel);
		RootPanel.get("main").add(buttonPanel);
		RootPanel.get("main").add(contentPanel);
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();
		
	}
	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		if(	Window.confirm("Möchten Sie die Notiz "+ n.getTitle()+ " wirklich löschen?")){
			editorVerwaltung.deleteNote(n, new DeleteCallback());
		}
		MenuView navigation = new MenuView();
		RootPanel.get("menu").clear();
		RootPanel.get("menu").add(navigation);	
		
		ShowAllNotes san =  new ShowAllNotes();
		RootPanel.get("main").clear();
		RootPanel.get("main").add(san);
			
		}
		
	}
	private class DeleteCallback implements AsyncCallback<Void>{

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
		if(Window.confirm("Möchten Sie die Änderungen speichern?")){
		updateNote();	
		}
		
			
		}
		
	}
	public void updateNote(){
		n.setTitle(titleTextBox.getText());
		n.setSubtitle(subtitleTextBox.getText());
		n.setContent(noteArea.getText());
		java.util.Date utilDate = dueDateBox.getValue();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    n.setDueDate(sqlDate);
		//n.setNoteBook(nb);	
		editorVerwaltung.saveNote(n, new UpdateCallback());
		
	}
	private class UpdateCallback implements AsyncCallback<Note>{

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
