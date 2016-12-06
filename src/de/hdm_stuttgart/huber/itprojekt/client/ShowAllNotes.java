package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.client.gui.NoteTable;
import de.hdm_stuttgart.huber.itprojekt.client.gui.NotebookTable;
import de.hdm_stuttgart.huber.itprojekt.shared.Editor;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;

/**
 * Klasse zur Darstellung der Notizen, die vom Nutzer nicht explizit einem
 * ordner zugeordnet sind.
 * 
 * @author Nikita Nalivayko
 *
 */
public class ShowAllNotes extends BasicView {
	EditorAsync editorVerwaltung = ClientsideSettings.getEditorVerwaltung();
	HorizontalPanel hPanel = new HorizontalPanel();
	private Vector<Note> notes = new Vector<Note>();

	/**
	 * No-Argument Konstruktor
	 */
	public ShowAllNotes() {

	}

	public ShowAllNotes(NoteBook selected) {

	}

	// Gibt alle Notizen zurück
	public Vector<Note> getAllNotesListe() {
		return notes;

	}

	public void setAllNotesListe(Vector<Note> liste) {
		this.notes = liste;

	}

	@Override
	public void run() {
//		FlowPanel contentPanel = new FlowPanel();
//		FlowPanel contentPanel2 = new FlowPanel();
//		contentPanel2.add(contentPanel);
//		NoteTable nt = new NoteTable(notes);
//		nt.addClickNote();
//		RootPanel.get().add(contentPanel);
//		RootPanel.get().add(nt.start());
		

		hPanel.add(new HTML("<p> Hier kommt der <b>Huber</b>, <i>obacht!</i> </p>"));

		AsyncCallback<Vector<Note>> callback = new AsyncCallback<Vector<Note>>() {

			@Override
			public void onFailure(Throwable caught) {

				caught.printStackTrace();
				hPanel.add(new Label(caught.toString()));
			}

			@Override
			public void onSuccess(Vector<Note> result) {

				hPanel.add(new HTML("<p> Als nächstes die Hubermethode! </p>"));
				huberMethode(result);

			}

		};

		editorVerwaltung.getAllNotes(callback);

		RootPanel.get().add(hPanel);

	}

	private void huberMethode(Vector<Note> result) {

		StringBuilder html = new StringBuilder();

		for (Note row : result) {

			html.append(row.toHtmlString() + "<br>");

		}

		RootPanel.get("main").add(new HTMLPanel(html.toString()));

	}

	@Override
	public String getSubHeadlineText() {
		return "Notizbuch xy";
	}

	@Override
	public String getHeadlineText() {

		return "Überschrift";
	}

}
