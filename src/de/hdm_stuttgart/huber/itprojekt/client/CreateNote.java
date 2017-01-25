package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import de.hdm_stuttgart.huber.itprojekt.client.gui.RichTextToolbar;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Notebook;

import java.util.Vector;

/**
 * Notiz anlegen!
 *
 * @author Nikita Nalivayko
 */
public class CreateNote extends BasicVerticalView {

    HorizontalPanel contentPanel = new HorizontalPanel();
    VerticalPanel leftPanel = new VerticalPanel();
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

    private CheckBox intoNoteBook = new CheckBox("Put new note into notebook?");
    private NoteBookListBox noteBookSelector = new NoteBookListBox();

    public CreateNote() {

    }

    public CreateNote(Notebook nb) {

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
    	
    	leftPanel.setHorizontalAlignment(ALIGN_CENTER);
    	leftPanel.setVerticalAlignment(ALIGN_MIDDLE);
    	leftPanel.setWidth("100%");
    	leftPanel.add(createHeadline(getHeadlineText(), getSubHeadlineText()));

        leftPanel.add(title);
		leftPanel.add(titleTextBox);
		
		leftPanel.add(subtitle);
		leftPanel.add(SubtitleTextBox);
		
		if (sourceProvided) {
		    Label sourceLabel = new Label("Source: " + source);
		    leftPanel.add(sourceLabel);
		}
		
		leftPanel.add(dueDate);
		leftPanel.add(dueDateBox);
		
		leftPanel.add(test);
		
		intoNoteBook.setValue(false);
		intoNoteBook.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
		
		    @Override
		    public void onValueChange(ValueChangeEvent<Boolean> event) {
		
		        noteBookSelector.setEnabled(event.getValue());
		
		    }
		
		});
		
		noteBookSelector.setEnabled(false);
		leftPanel.add(noteBookSelector);
		addNoteBooksToListBox();
		
		leftPanel.add(intoNoteBook);
		leftPanel.add(createButton);
		createButton.addClickHandler(new CreateClickHandler());

        VerticalPanel rightPanel = new VerticalPanel();
        rightPanel.setWidth("100%");
        rightPanel.setHorizontalAlignment(ALIGN_CENTER);
        rightPanel.setVerticalAlignment(ALIGN_MIDDLE);
        
        int heightPixels = (int) (Window.getClientHeight() * 0.6);
        noteArea.setStyleName("noteArea");
        noteArea.setWidth("100%");
        noteArea.setHeight(Integer.toString(heightPixels) + "px");
        
        VerticalPanel sandwich = new VerticalPanel();
        sandwich.add(richTextToolbar);
        sandwich.add(noteArea);
        rightPanel.add(sandwich);
      
        contentPanel.setSize("100%", "100%");
        contentPanel.setHorizontalAlignment(ALIGN_CENTER);
        contentPanel.setVerticalAlignment(ALIGN_MIDDLE);

        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);
        
        this.clear();
        this.add(contentPanel);

   

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
     * Neue Notiz wird erstellt
     */
    public void createNote() {

        Note note = new Note();
        note.setTitle(titleTextBox.getText());
        note.setSubtitle(SubtitleTextBox.getText());
        note.setContent(noteArea.getHTML());

        if (dueDateBox.getValue() != null) {

            java.util.Date utilDate = dueDateBox.getValue();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            note.setDueDate(sqlDate);

        }

        if (intoNoteBook.getValue()) {

            Notebook nb;
            nb = noteBookSelector.getSelectedItem();
            GWT.log("Returned notebook:" + nb.toString());
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

    /**
     * ClickHandler zum anlegen einer Notiz
     */
    private class CreateClickHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            createNote();
        }

    }

    private class AddToListBoxCallback implements AsyncCallback<Vector<Notebook>> {

        @Override
        public void onFailure(Throwable caught) {
            GWT.log("RPC failed, see reason below:");
            GWT.log(caught.toString());
        }

        @Override
        public void onSuccess(Vector<Notebook> result) {
            noteBookSelector.addAll(result);
        }

    }

}
