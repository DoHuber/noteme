package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
/**
 * Notizanlegen 
 * @author Nikita Nalivayko
 *
 */
public class CreateNote extends BasicView {

	
	HorizontalPanel hPanel = new HorizontalPanel();

	@Override
	public void run() {
		
	
		Label lb = new Label("Hier wird eine neue Notiz  erstellt ");
		
		hPanel.add(lb);
		
		

		
	    RootPanel.get().add(hPanel);
		
		
		
	}
}
