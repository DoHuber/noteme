package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;

import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.NotebookTable;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
/**
 * Klasse zur Darstellung von Notizb�chern, gleiche Funktionsweise wie "ShowAllNotes"
 * @author erdmann, nalivayko
 *
 */
public class ShowAllNotebooks extends BasicView {
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	AllNotebooksCallback callback = new AllNotebooksCallback();
	
	private Vector<NoteBook> notebook = new Vector<NoteBook>();
	
	public ShowAllNotebooks(Vector<NoteBook> nList){
		notebook=nList;
	}

	/**
	 * No-Argument Konstruktor
	 */
	public ShowAllNotebooks() {

	}

	public ShowAllNotebooks(NoteBook selected) {

	}

	// Gibt alle Notizb�cher zurück
	public Vector<NoteBook> getAllNotebooksListe() {
		return notebook;

	}

	public void setAllNotesListe(Vector<NoteBook> liste) {
		this.notebook = liste;

	}
	@Override
	public void run(){
		  	FlowPanel contentPanel = new FlowPanel();
		    FlowPanel fPanel2 = new FlowPanel();
		   
		    fPanel2.add(contentPanel);
		    editorVerwaltung.getAllNoteBooksForCurrentUser(callback);
		    NotebookTable nbt = new NotebookTable(notebook);
		    nbt.addClickNote();
		    RootPanel.get("main").add(contentPanel);
		    RootPanel.get("main").add(nbt.start());
	}
	private class AllNotebooksCallback implements AsyncCallback<Vector<NoteBook>> {
    @Override
    public void onSuccess(Vector<NoteBook> result) {
      addNoteBooksToTable(result);
    }

    @Override
    public void onFailure(Throwable caught) {}

	
  }

	@Override
	public String getSubHeadlineText() {
		return "Notizbuch xy";
	}

	public void addNoteBooksToTable(Vector<NoteBook> result) {
	notebook = result;
	NotebookTable ntB = new NotebookTable(notebook);
	ntB.addClickNote();
	RootPanel.get("main").clear();
	RootPanel.get("main").add(ntB.start());
}

	@Override
	public String getHeadlineText() {

		return "My Notebooks";
	}

}
