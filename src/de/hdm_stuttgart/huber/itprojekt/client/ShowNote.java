package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import de.hdm_stuttgart.huber.itprojekt.client.gui.IconConstants;
import de.hdm_stuttgart.huber.itprojekt.client.gui.Notificator;
import de.hdm_stuttgart.huber.itprojekt.client.gui.RichTextToolbar;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;

public class ShowNote extends BasicVerticalView {
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
    private Label empty = new Label("                   ");
    private Label title = new Label("Title");
    private Label subtitle = new Label("Subtitle");
    private Label dueDate = new Label("Due Date");
    private Grid grid = new Grid(2, 1);
    private Note currentlyDisplayedNote = null;


    public ShowNote() {

    }

    public ShowNote(Note note) {
        this.currentlyDisplayedNote = note;

    }

    @Override
    public String getSubHeadlineText() {

        if (currentlyDisplayedNote.getNoteBook() == null) {
            return "There is no notebook deposited for the note.";
        } else {
            return "Notebook:" + currentlyDisplayedNote.getNoteBook().getTitle();
        }

    }

    @Override
    public String getHeadlineText() {
        return "Note: " + currentlyDisplayedNote.getTitle();
    }

    @Override
    public void run() {

        setUpButtons();

        setUpButtonPanel();

        setUpPanels();

        empty.getElement().getStyle().setColor("#660033");

        this.add(contentPanel);

        if (currentlyDisplayedNote.hasRuntimePermission()) {
            processNoteWithPermissions();
        }

    }

    private void processNoteWithPermissions() {

        shareButton.setEnabled(false);

        Permission p = currentlyDisplayedNote.getRuntimePermission();
        if (!p.isUserAllowedTo(Level.DELETE)) {
            deleteButton.setEnabled(false);
        }

        if (!p.isUserAllowedTo(Level.EDIT)) {
            disableEditFields();
        }

        // Fehlerkondition: User darf dann eigentlich gar nicht hier sein
        if (!p.isUserAllowedTo(Level.READ)) {

            ApplicationPanel.getApplicationPanel().replaceContentWith(new ShowAllNotes());

        }


    }

    private void disableEditFields() {

        titleTextBox.setEnabled(false);
        subtitleTextBox.setEnabled(false);
        dueDateBox.setEnabled(false);
        updateConfirmButton.setVisible(false);
        noteArea.setEnabled(false);

    }

    private void setUpPanels() {

        populateAndDisplayTitles();
        setUpAlignPanel();
        setUpContentPanel();

    }

    private void setUpAlignPanel() {

        alignPanel.add(empty);
        alignPanel.add(dueDate);
        alignPanel.add(dueDateBox);
        alignPanel.add(updateConfirmButton);
        dueDateBox.setValue(currentlyDisplayedNote.getDueDate());

    }

    private void setUpContentPanel() {

        noteArea.setHTML(currentlyDisplayedNote.getContent());
        noteArea.setSize("100%", "100%px");
        noteArea.setStyleName("noteArea");

        grid.setWidget(1, 0, noteArea);
        grid.setWidget(0, 0, richTextToolbar);
        contentPanel.add(alignPanel);
        contentPanel.add(grid);
    }

    private void populateAndDisplayTitles() {
        alignPanel.add(title);
        alignPanel.add(titleTextBox);
        titleTextBox.setText(currentlyDisplayedNote.getTitle());
        alignPanel.add(subtitle);
        alignPanel.add(subtitleTextBox);
        subtitleTextBox.setText(currentlyDisplayedNote.getSubtitle());
    }

    private void setUpButtonPanel() {

        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.add(shareButton);
        buttonPanel.add(deleteButton);
        this.add(buttonPanel);
    }

    private void setUpButtons() {

        deleteButton.addClickHandler(new DeleteClickHandler());
        shareButton.addClickHandler(new ShareClickHandler());
        updateConfirmButton.addClickHandler(new UpdateClickHandler());

        shareButton.setStyleName("pure-button");
        deleteButton.setStyleName("pure-button");
        updateConfirmButton.setStyleName("pure-button");
    }

    public void updateNote() {

        currentlyDisplayedNote.setTitle(titleTextBox.getText());
        currentlyDisplayedNote.setSubtitle(subtitleTextBox.getText());
        currentlyDisplayedNote.setContent(noteArea.getHTML());
        
        if (dueDateBox.getValue() != null) {
        	
        	java.util.Date utilDate = dueDateBox.getValue();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            currentlyDisplayedNote.setDueDate(sqlDate);
            
        }
        
        editorVerwaltung.saveNote(currentlyDisplayedNote, new UpdateCallback());

    }

    private class ShareClickHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {

            ShareShareable shareStuff = new ShareShareable(currentlyDisplayedNote);
            ApplicationPanel.getApplicationPanel().replaceContentWith(shareStuff);

        }

    }

    private class DeleteClickHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {

            if (Window.confirm("Möchten Sie die Notiz " + currentlyDisplayedNote.getTitle() + " wirklich löschen?")) {
                editorVerwaltung.deleteNote(currentlyDisplayedNote, new DeleteCallback());
            }

        }

    }

    private class DeleteCallback implements AsyncCallback<Void> {

        Notificator n = Notificator.getNotificator();

        @Override
        public void onFailure(Throwable caught) {

            n.showError("Fehler! Grund:" + caught.toString());

        }

        @Override
        public void onSuccess(Void result) {

            n.showSuccess("Notiz " + currentlyDisplayedNote.getTitle() + " wurde gelöscht");

            ApplicationPanel.getApplicationPanel().replaceContentWith(new ShowAllNotes());

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

    private class UpdateCallback implements AsyncCallback<Note> {

        private Notificator n = Notificator.getNotificator();

        @Override
        public void onFailure(Throwable caught) {

            n.showError("Could not save");
            n.showError(caught.toString());
            GWT.log("Update failed because of:");
            GWT.log(caught.toString());

        }

        @Override
        public void onSuccess(Note result) {

            n.showSuccess("Note was saved.");

        }

    }

}
