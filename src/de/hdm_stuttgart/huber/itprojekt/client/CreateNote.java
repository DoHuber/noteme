package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

/**
 * Notiz anlegen! 
 * 
 * @author Nikita Nalivayko
 *
 */
public class CreateNote extends BasicView {
	
	HorizontalPanel contentPanel = new HorizontalPanel();
	VerticalPanel alignPanel = new VerticalPanel();
	Button createButton = new Button("Create");
	
	private EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	private RichTextArea noteArea = new RichTextArea();

	private TextBox titleTextBox = new TextBox();
	private TextBox SubtitleTextBox = new TextBox();
	private RichTextToolbar richTextToolbar=new RichTextToolbar(noteArea);
	private DateBox dueDateBox = new DateBox();
	private Label title = new Label("Title");
	private Label subtitle = new Label("Subtitle");
	private Label dueDate = new Label("Due Date");
	private Label test = new Label();
	private Grid grid = new Grid(2,1);
	private NoteBook nb = null; 
	public CreateNote (){
		
	}
	public CreateNote (NoteBook nb ){
		this.nb = nb;
		
	}
	@Override
	public void run() {
		/*
		 * Widgets
		 * 
		 */
		alignPanel.add(title);
		alignPanel.add(titleTextBox);

		alignPanel.add(subtitle);
		alignPanel.add(SubtitleTextBox);

		alignPanel.add(dueDate);
		alignPanel.add(dueDateBox);
		
		alignPanel.add(test);
		alignPanel.add(createButton);
		createButton.addClickHandler(new CreateClickHandler());
		grid.setWidget(0, 0, richTextToolbar);
	
		// noteArea.setSize("475px", "100px");
		noteArea.setSize("100%", "10%px");
		grid.setWidget(1, 0, noteArea);
		
		//contentPanel.add(richTextToolbar);
		contentPanel.add(alignPanel);
		//contentPanel.add(noteArea);
		contentPanel.add(grid);
		RootPanel.get("main").add(contentPanel);
		
		noteArea.setStyleName("noteArea");
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();
	}

	@Override
	public String getHeadlineText() {

		return "CREATE A NOTE";
	}

	@Override
	public String getSubHeadlineText() {

		return "Give your note a title and subtitle to complete!";
	}
	/**
	 * 
	 * ClickHandler zum anlegen einer Notiz 
	 *
	 */
	private class CreateClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			createNote();
			ShowAllNotes san = new ShowAllNotes();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(san);
		}
		
	}
	/**
	 * Neue Notiz wird erstellt 
	 */
	public void createNote(){
		
		Note note = new Note();
		note.setTitle(titleTextBox.getText());
		note.setSubtitle(SubtitleTextBox.getText());
		note.setContent(noteArea.getText());
		note.setNoteBook(nb);
		editorVerwaltung.createNote(note, new CreateNoteCallback());

	}

	/**
	 * Klasse die den callback zum Notiz anlegen implementiert. Die angelegte Notiz wird
	 * an die EditorImpl übergeben. Später soll die angelegte Notiz noch dem Nutzer angezeigt werden  
	 * 
	 *
	 */
	private class CreateNoteCallback implements AsyncCallback<Note>{

		@Override
		public void onFailure(Throwable caught) {
			
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Note result) {
			MenuView mw = new MenuView();
			String test ="Erfolgreich";
			Label lb=new Label(test);	
			RootPanel.get("main").clear();
			RootPanel.get("menu").clear();
			RootPanel.get("main").add(mw);
			RootPanel.get("main").add(lb);
			
		}
		
	}

	


}
