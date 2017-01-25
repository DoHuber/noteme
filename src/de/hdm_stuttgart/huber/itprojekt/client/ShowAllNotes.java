package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.IconConstants;
import de.hdm_stuttgart.huber.itprojekt.client.gui.NoteTable;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Notebook;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Klasse zur Darstellung der Notizen, die vom Nutzer nicht explizit einem
 * ordner zugeordnet sind.
 *
 * @author Nikita Nalivayko
 */
public class ShowAllNotes extends BasicVerticalView {

    final Button sharedByBtn = new Button("Shared by me");
    final Button sharedWithBtn = new Button("Shared with me");
    final Button allNotesBtn = new Button("All Notes");
    EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
    Button addNoteButton = new Button(IconConstants.ICON_ADD_NOTE);

    private NoteTable currentTable;

    /**
     * No-Argument Konstruktor
     */
    public ShowAllNotes() {

    }

    public ShowAllNotes(Notebook selected) {

    }

    @Override
    public String getHeadlineText() {

        return "MY NOTES";
    }

    @Override
    public String getSubHeadlineText() {
        return "Select a note for more information!";
    }

    @Override
    public void run() {

        FlowPanel contentPanel = new FlowPanel();
        VerticalPanel fPanel2 = new VerticalPanel();
        FlowPanel buttonsPanel = new FlowPanel();

        sharedWithBtn.addClickHandler(new SharedWithClickHandler());
        allNotesBtn.addClickHandler(new AllNotesClickHandler());
        sharedByBtn.addClickHandler(new SharedByClickHandler());
        addNoteButton.addClickHandler(new CreateNoteHandler());

        buttonsPanel.add(sharedByBtn);
        buttonsPanel.add(sharedWithBtn);
        buttonsPanel.add(allNotesBtn);
        buttonsPanel.add(addNoteButton);

        fPanel2.add(buttonsPanel);
        fPanel2.add(contentPanel);

        editorVerwaltung.getAllNotesForCurrentUser(new AllNotesCallback());
        this.add(fPanel2);

    }

    public void fillTableWith(Vector<Note> result) {

        if (currentTable != null) {
            this.remove(currentTable);
        }

        NoteTable nt = new NoteTable(result);
        nt.addClickNote();

        this.add(nt);
        currentTable = nt;
       
    }

    private class AllNotesClickHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
        	
            editorVerwaltung.getAllNotesForCurrentUser(new AllNotesCallback());

        }

    }

    private class AllNotesCallback implements AsyncCallback<Vector<Note>> {
    	
    	private ArrayList<Note> allNotes = new ArrayList<>();
    	private boolean finished = false;
    	
    	public AllNotesCallback() {
    		
    	}
    	
    	private AllNotesCallback(boolean finished, ArrayList<Note> dataSet) {
    		this.finished = finished;
    		allNotes = dataSet;
    	}
    	
        @Override
        public void onSuccess(Vector<Note> result) {

            if (!finished) {
            	
            	allNotes.addAll(result);
            	editorVerwaltung.getAllSharedNotesForCurrentUser(new AllNotesCallback(true, allNotes));
            	
            } else {
            	
            	allNotes.addAll(result);
            	fillTableWith(new Vector<Note>(allNotes));
            	
            }

        }

        @Override
        public void onFailure(Throwable caught) {

            GWT.log(caught.toString());

        }

    }

    private class SharedWithClickHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {

            SharedWithCallback callback = new SharedWithCallback();
            editorVerwaltung.getAllSharedNotesForCurrentUser(callback);

        }

    }

    private class SharedWithCallback implements AsyncCallback<Vector<Note>> {

        @Override
        public void onFailure(Throwable caught) {
            GWT.log(caught.toString());
        }

        @Override
        public void onSuccess(Vector<Note> result) {
            fillTableWith(result);

        }

    }

    private class SharedByClickHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {

            SharedByCallback callback = new SharedByCallback();
            editorVerwaltung.getAllNotesSharedByCurrentUser(callback);

        }

    }

    private class SharedByCallback implements AsyncCallback<Vector<Note>> {

        @Override
        public void onFailure(Throwable caught) {
            GWT.log(caught.toString());
        }

        @Override
        public void onSuccess(Vector<Note> result) {
            fillTableWith(result);
        }

    }

}
