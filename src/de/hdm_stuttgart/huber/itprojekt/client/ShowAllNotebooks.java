package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import de.hdm_stuttgart.huber.itprojekt.client.gui.IconConstants;
import de.hdm_stuttgart.huber.itprojekt.client.gui.NotebookTable;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Notebook;

import java.util.Vector;

/**
 * Klasse zur Darstellung von Notizb�chern, gleiche Funktionsweise wie
 * "ShowAllNotes"
 *
 * @author erdmann, nalivayko
 */
public class ShowAllNotebooks extends BasicVerticalView {

    final Button sharedByBtn = new Button("Shared by me");
    final Button sharedWithBtn = new Button("Shared with me");
    final Button allNoteBooksBtn = new Button("All Notebooks");
    EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
    AllNotebooksCallback callback = new AllNotebooksCallback();
    Button createNoteBookButton = new Button(IconConstants.ICON_ADD_NOTE);
    private Vector<Notebook> notebook = new Vector<>();

    private NotebookTable currentTable;

    public ShowAllNotebooks(Vector<Notebook> nList) {
        notebook = nList;
    }

    /**
     * No-Argument Konstruktor
     */
    public ShowAllNotebooks() {

    }

    public ShowAllNotebooks(Notebook selected) {

    }

    // Gibt alle Notizb�cher zurück
    public Vector<Notebook> getAllNotebooksListe() {
        return notebook;

    }

    public void setAllNotesListe(Vector<Notebook> liste) {
        this.notebook = liste;

    }

    @Override
    public String getHeadlineText() {

        return "MY NOTEBOOKS";
    }

    @Override
    public String getSubHeadlineText() {
        return "Select a notebook to have a look on your belonging notes";
    }

    @Override
    public void run() {
        FlowPanel contentPanel = new FlowPanel();
        FlowPanel fPanel2 = new FlowPanel();
        FlowPanel buttonsPanel = new FlowPanel();
        buttonsPanel.setStyleName("buttonsPanel");
        sharedWithBtn.addClickHandler(new SharedWithClickHandler());
        allNoteBooksBtn.addClickHandler(new AllNoteBooksClickHandler());
        sharedByBtn.addClickHandler(new SharedByClickHandler());
        createNoteBookButton.addClickHandler(new CreateNoteBookClickHandler());
        buttonsPanel.add(sharedByBtn);
        buttonsPanel.add(sharedWithBtn);
        buttonsPanel.add(allNoteBooksBtn);
        buttonsPanel.add(createNoteBookButton);
        fPanel2.add(buttonsPanel);
        fPanel2.add(contentPanel);

        editorVerwaltung.getAllNoteBooksForCurrentUser(callback);

        this.add(fPanel2);
    }

    public void fillTableWith(Vector<Notebook> result) {

        if (currentTable != null) {
            this.remove(currentTable);
        }

        NotebookTable ntB = new NotebookTable(result);
        ntB.addClickNote();

        this.add(ntB);
        currentTable = ntB;

    }

    private class AllNoteBooksClickHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            editorVerwaltung.getAllNoteBooksForCurrentUser(callback);

        }

    }

    private class AllNotebooksCallback implements AsyncCallback<Vector<Notebook>> {
        @Override
        public void onSuccess(Vector<Notebook> result) {
            fillTableWith(result);
        }

        @Override
        public void onFailure(Throwable caught) {
        }

    }

    private class SharedWithClickHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            SharedWithCallback callback = new SharedWithCallback();
            editorVerwaltung.getAllSharedNoteBooksForCurrentUser(callback);

        }

    }

    private class SharedWithCallback implements AsyncCallback<Vector<Notebook>> {

        @Override
        public void onFailure(Throwable caught) {
            GWT.log(caught.toString());
        }

        @Override
        public void onSuccess(Vector<Notebook> result) {
            fillTableWith(result);

        }

    }

    private class SharedByClickHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            SharedByCallback callback = new SharedByCallback();
            editorVerwaltung.getAllNoteBooksSharedByCurrentUser(callback);

        }

    }

    private class SharedByCallback implements AsyncCallback<Vector<Notebook>> {

        @Override
        public void onFailure(Throwable caught) {
            GWT.log(caught.toString());
        }

        @Override
        public void onSuccess(Vector<Notebook> result) {

            fillTableWith(result);

        }

    }

    private final class CreateNoteBookClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {

            ApplicationPanel.getApplicationPanel().replaceContentWith(new CreateNotebook());

        }
    }

}
