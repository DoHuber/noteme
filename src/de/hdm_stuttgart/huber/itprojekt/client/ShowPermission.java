package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CustomButton;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.NoteTable;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

/**
 * Klasse zur Darstellung der Notizen, die vom Nutzer nicht explizit einem
 * ordner zugeordnet sind.
 * 
 * @author Lisa Kuechler
 *
 */
public class ShowPermission extends BasicView {
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	AllNotesCallback callback = new AllNotesCallback();
	
	//HorizontalPanel hPanel = new HorizontalPanel();
	private Vector<Note> notes = new Vector<Note>();
	
	public ShowPermission(Vector<Note> nList){
		notes=nList;
	}

	/**
	 * No-Argument Konstruktor
	 */
	public ShowPermission() {

	}

	public ShowPermission(NoteBook selected) {

	}

	// Gibt alle Notizen zurück
	public Vector<Note> getAllNotesListe() {
		// new ShowAllNotes().getHeadlineText();
		return notes;

	}

	public void setAllNotesListe(Vector<Note> liste) {
		this.notes = liste;

	}
	
	@Override
	public String getHeadlineText() {

		return "MY NOTES";
	}

	@Override
	public String getSubHeadlineText() {
		return "Select a note for more information!";
	}
	
	final Button freigabeButton = new Button("Freigegebene Notizen");
	final Button nichtFreigabeButton = new Button("Nicht freigegebene Notizen");
	final Button notizenAnzeigenButton = new Button("Alle");
	
	@Override
	public void run(){
		  	freigabeButton.setStyleName("pure-button");
		  	nichtFreigabeButton.setStyleName("pure-button");
		  	notizenAnzeigenButton.setStyleName("pure-button");
		  	// notizenAnzeigenButton.setPushed(true);
		  	
		  			
			FlowPanel contentPanel = new FlowPanel();
		    FlowPanel fPanel2 = new FlowPanel();
		    FlowPanel buttonsPanel = new FlowPanel();
		  
		    fPanel2.add(contentPanel);
		    
		    editorVerwaltung.getAllNotesForCurrentUser(callback);
		    
//		    NoteTable nt = new NoteTable(notes);
//		    nt.addClickNote();
		    RootPanel.get("main").add(contentPanel);
//		    RootPanel.get("main").add(nt.start());
		    
		   // freigabeButton.addClickHandler(new freigabeButtonClickHandler());
		   // BUTTONS !!
	}
	
	
	private class AllNotesCallback implements AsyncCallback<Vector<Note>> {
    @Override
    public void onSuccess(Vector<Note> result) {
    	
    	//Logger logger = Logger.getLogger("test");
    	//logger.log(Level.INFO, Arrays.toString(result.toArray()));
    	
      addNotesToTable(result);
    }

    @Override
    public void onFailure(Throwable caught) {
    	
    	// Logger logger = Logger.getLogger("test");
    	// logger.log(Level.SEVERE, caught.toString());
    	
    }

		
  }


//	@Override
//	public void run() {

//		
//
//		hPanel.add(new HTML("<p> Hier kommt der <b>Huber</b>, <i>obacht!</i> </p>"));
//
//		AsyncCallback<Vector<Note>> callback = new AsyncCallback<Vector<Note>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//
//				caught.printStackTrace();
//				hPanel.add(new Label(caught.toString()));
//			}
//
//			@Override
//			public void onSuccess(Vector<Note> result) {
//
//				hPanel.add(new HTML("<p> Als nächstes die Hubermethode! </p>"));
//				huberMethode(result);
//
//			}
//
//		};
//
//		editorVerwaltung.getAllNoteBooks(callback);
//
//		RootPanel.get().add(hPanel);
//
//	}

//	private void huberMethode(Vector<Note> result) {
//
//		StringBuilder html = new StringBuilder();
//
//		for (Note row : result) {
//
//			html.append(row.toHtmlString() + "<br>");
//
//		}
//
//		RootPanel.get("main").add(new HTMLPanel(html.toString()));
//
//	}
	

	public void addNotesToTable(Vector<Note> result) {
	
	notes = result;
	NoteTable nt = new NoteTable(notes);
	nt.addClickNote();
	RootPanel.get("table").clear();
	RootPanel.get("table").add(nt.start());
	
}

	

}