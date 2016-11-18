package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
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
