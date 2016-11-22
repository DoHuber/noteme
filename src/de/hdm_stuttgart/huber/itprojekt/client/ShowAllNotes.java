package de.hdm_stuttgart.huber.itprojekt.client;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
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
		
		
		
		
	    RootPanel.get().add(hPanel);
		
	    EditorAsync editor = GWT.create(Editor.class);
		
	    AsyncCallback<Vector<Note>> callback = new AsyncCallback<Vector<Note>>() {

			@Override
			public void onFailure(Throwable caught) {
				
				caught.printStackTrace();
				
			}

			@Override
			public void onSuccess(Vector<Note> result) {
				
				huberMethode(result);
				
			}
	    	
	    };
	
		editor.getAllNotes(callback);
		
	}
	
	private void huberMethode(Vector<Note> result) {
		
		for (Note row : result) {
			
			hPanel.add(new HTML(row.toString()));
			
		}
		
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}

}
