package de.hdm_stuttgart.huber.itprojekt.client.Report;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm_stuttgart.huber.itprojekt.client.BasicView;

/**
 * 
 * @author dominik erdmann
 *
 */

public class ShowUsers extends BasicViewR{

	HorizontalPanel hPanel = new HorizontalPanel();

	@Override
	public void run() {
		
	
		Label lb = new Label("Alle Nutzer");
		
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
