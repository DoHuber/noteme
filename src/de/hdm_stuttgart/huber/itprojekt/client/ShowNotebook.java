package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import de.hdm_stuttgart.huber.itprojekt.client.gui.NoteTable;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

public class ShowNotebook extends BasicView{
	
	/**
	 * @author Erdmann, Nalivayko
	 */
	/**
	 * Funktionen: Löschen, Editieren, Freigeben, 
	 */
	private HorizontalPanel vp = new HorizontalPanel();
	private Button deleteBtn = new Button("Delete");
	private Button editBtn = new Button("Update");
	private Button releseBtn = new Button("Relese");
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	NoteBook nb = null;
	AllNotesCallback callback = new AllNotesCallback();
	
	
	private Vector<Note> notes = new Vector<Note>();

	public ShowNotebook(NoteBook selected) {
	this.nb =selected;
	}

	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Notizbuch: "+ nb.getTitle();
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return "Subtitle: "+ nb.getSubtitle();
	}

	@Override
	public void run() {

		FlowPanel contentPanel = new FlowPanel();
		vp.add(deleteBtn);
		vp.add(editBtn);
		vp.add(releseBtn);
		editorVerwaltung.getAllNotes(callback);
	    NoteTable nt = new NoteTable(notes);
	    nt.addClickNote();
	    RootPanel.get("main").clear();
	   
		contentPanel.add(vp);
		contentPanel.add(nt.start());
		RootPanel.get("main").add(contentPanel);


	}
	private class AllNotesCallback implements AsyncCallback<Vector<Note>> {
	    @Override
	    public void onSuccess(Vector<Note> result) {
	      addNotesToTable(result);
	    }

	    @Override
	    public void onFailure(Throwable caught) {}

		
	  }
	public void addNotesToTable(Vector<Note> result) {
	notes = result;
	NoteTable nt = new NoteTable(notes);
	nt.addClickNote();
	RootPanel.get("main").clear();
	RootPanel.get("main").add(nt.start());
}

}



