package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
	private RichTextToolbar richTextToolbar = new RichTextToolbar(noteArea);
	private DateBox dueDateBox = new DateBox();
	private Label title = new Label("Title");
	private Label subtitle = new Label("Subtitle");
	private Label dueDate = new Label("Due Date");
	private Label test = new Label();
	private String source;
	private boolean sourceProvided = false;
	private Grid grid = new Grid(2, 1);

	private CheckBox intoNoteBook = new CheckBox("Put new note into notebook?");
	private NoteBookListBox noteBookSelector = new NoteBookListBox();

	public CreateNote() {

	}

	public CreateNote(NoteBook nb) {
		
		intoNoteBook.setValue(true);
		noteBookSelector.addItem(nb);
		noteBookSelector.setSelectedIndex(0);

	}

	public CreateNote(String source) {

		this.source = source;
		sourceProvided = true;

	}

	@Override
	public void run() {
	
		setupAlignPanel();
		
		createButton.addClickHandler(new CreateClickHandler());
		grid.setWidget(0, 0, richTextToolbar);

		setUpNoteBookSelection();

		noteArea.setSize("100%", "100%");
		grid.setWidget(1, 0, noteArea);

		contentPanel.add(alignPanel);
		contentPanel.add(grid);
		RootPanel.get("main").add(contentPanel);

		noteArea.setStyleName("noteArea");
		RootPanel.get("table").clear();
		RootPanel.get("tableNotebook").clear();
		
	}

	private void setupAlignPanel() {
		alignPanel.add(title);
		alignPanel.add(titleTextBox);

		alignPanel.add(subtitle);
		alignPanel.add(SubtitleTextBox);

		if (sourceProvided) {
			Label sourceLabel = new Label("Source: " + source);
			alignPanel.add(sourceLabel);
		}

		alignPanel.add(dueDate);
		alignPanel.add(dueDateBox);

		alignPanel.add(test);
		alignPanel.add(createButton);
	}

	private void setUpNoteBookSelection() {
		
		noteBookSelector.setEnabled(false);
		alignPanel.add(noteBookSelector);
		addNoteBooksToListBox();

		intoNoteBook.setValue(false);
		intoNoteBook.addValueChangeHandler(new ValueChangeHandler<Boolean>(){

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				
				noteBookSelector.setEnabled(event.getValue());
				
			}
			
		});
	
		alignPanel.add(intoNoteBook);
		
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
	private class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			createNote();
		}

	}

	/**
	 * Neue Notiz wird erstellt
	 */
	public void createNote() {

		Note note = new Note();
		note.setTitle(titleTextBox.getText());
		note.setSubtitle(SubtitleTextBox.getText());
		note.setContent(noteArea.getHTML());
		java.util.Date utilDate = dueDateBox.getValue();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		note.setDueDate(sqlDate);
		
		if (intoNoteBook.getValue()) {
			
			NoteBook nb;
			nb = noteBookSelector.getSelectedItem();
			note.setNoteBook(nb);
			
		}

		if (sourceProvided) {
			note.setSource(source);
		}

		editorVerwaltung.createNote(note, new CreateNoteCallback());

	}

	private void addNoteBooksToListBox() {

		editorVerwaltung.getAllNoteBooksForCurrentUser(new AddToListBoxCallback());
		editorVerwaltung.getAllSharedNoteBooksForCurrentUser(new AddToListBoxCallback());

	}

	private class AddToListBoxCallback implements AsyncCallback<Vector<NoteBook>> {

		@Override
		public void onFailure(Throwable caught) {
			GWT.log("RPC failed, see reason below:");
			GWT.log(caught.toString());
		}

		@Override
		public void onSuccess(Vector<NoteBook> result) {
			noteBookSelector.addAll(result);
		}

	}

}
