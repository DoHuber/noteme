package de.hdm_stuttgart.huber.itprojekt.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
/**
 * 
 * @author Nikita Nalivayko
 *
 */
public abstract class BasicView extends FlowPanel {
	
	
	@Override 
	public  void onLoad(){
		
	
	    super.onLoad();
	    //RootPanel.get().clear();
	    run();
	    
		
	}
	
	public abstract void run();

}
