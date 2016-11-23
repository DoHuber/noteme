package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.shared.Editor;
import de.hdm_stuttgart.huber.itprojekt.shared.EditorAsync;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
/**
 * Klasse zur Darstellung der Notizen, die vom Nutzer nicht explizit einem ordner zugeordnet sind.  
 * @author Nikita Nalivayko
 *
 */
public class ShowAllNotes extends BasicView {

	HorizontalPanel hPanel = new HorizontalPanel();

	@Override
	public void run() {
		
	
		Label lb = new Label("Hier werden Notizen angezeigt. Die nicht einem Ordner zugeordnet sind");
		
		hPanel.add(lb);
		hPanel.add(new HTML("<p> Hier kommt der <b>Huber</b>, <i>obacht!</i> </p>"));
		
		
	    EditorAsync editor = GWT.create(Editor.class);
		
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
	
		editor.getAllNotes(callback);
		
		RootPanel.get().add(hPanel);
		
	}
	
	private void huberMethode(Vector<Note> result) {
		
		StringBuilder html = new StringBuilder();
		
		for (Note row : result) {
			
			html.append(row.toString() + "<br>");
			
		}
		
		RootPanel.get().add(new HTMLPanel(html.toString()));
		
	}

	@Override
	public String getSubHeadlineText() {
		return "Unterüberschrift";
	}

	@Override
	public String getHeadlineText() {
		
		return "Überschrift";
	}

}
