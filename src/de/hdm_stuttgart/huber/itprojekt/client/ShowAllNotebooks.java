package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
/**
 * Klasse zur Darstellung der Notizbücher, die einem bestimmten  Nutzer gehören. 
 * @author Nikita Nalivayko
 *
 */
public class ShowAllNotebooks extends BasicView {
	
	HorizontalPanel hPanel = new HorizontalPanel();

	@Override
	public void run() {
		
	
		Label lb = new Label("Hier werden Notizbücher eines Nutzers angezeigt ");
		
		hPanel.add(lb);
		
		

		
	    RootPanel.get().add(hPanel);
		
		
		
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
