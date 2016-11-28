package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.NotebookTable;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
/**
 * Klasse zur Darstellung der Notizbücher, die einem bestimmten  Nutzer gehören. 
 * @author Nikita Nalivayko
 *
 */
public class ShowAllNotebooks extends BasicView {
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	
	private Vector<NoteBook> notebooks = new Vector<NoteBook>();
	/**
	 * No-Argument Konstruktor
	 */
	public ShowAllNotebooks(){
		
	}
	//Gibt alle Notizbücher zurück 
	public Vector<NoteBook> getAllNotebookListe(){
		return notebooks;
		
	}
	public void setAllNotebookListe(Vector<NoteBook> liste){
		this.notebooks=liste;
		
	}
	

	@Override
	public void run() {
		FlowPanel contentPanel = new FlowPanel();
		FlowPanel contentPanel2 = new FlowPanel();
		contentPanel2.add(contentPanel);
		NotebookTable nbt= new NotebookTable(notebooks);
		nbt.addClickNotebook();
		RootPanel.get().add(contentPanel);
		RootPanel.get().add(nbt.start());

		
		
		
		

		
	    

		
		
		
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return "Alle Notizbücher des Nutzers X";
	}

	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Wähle ein Notizbuch aus";
	}

}
