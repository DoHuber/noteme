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

public class ShowAllNotebooksR extends BasicView{

	HorizontalPanel hPanel = new HorizontalPanel();

	public void run() {
		
	
		Label lb = new Label("Alle Notizbücher");
		
		hPanel.add(lb);
		
		

		
	    RootPanel.get().add(hPanel);
		
		
		
	}

	public String getSubHeadlineText() {
		return null;
	}

	public String getHeadlineText() {
		return null;
	}

}

