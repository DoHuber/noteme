package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
		contentPanel.add(vp);
		RootPanel.get("main").add(contentPanel);

	}

}



