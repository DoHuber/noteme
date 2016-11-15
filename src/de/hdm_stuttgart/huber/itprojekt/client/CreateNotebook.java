package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
/**
 * Notizbuch anlegen
 * @author Nikita Nalivayko
 *
 */
public class CreateNotebook extends BasicView {
	

	HorizontalPanel hPanel = new HorizontalPanel();
	
	

	@Override
	public void run() {
		
	
		Label lb = new Label("Hier wird ein neues Notizbuch erstellt ");
		
		hPanel.add(lb);
		RootPanel.get().add(hPanel);
		
		
		
	}



	
	
}
