package de.hdm_stuttgart.huber.itprojekt.client;


import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Klasse zum Anzeigen von Reports
 * @author dominik erdmann
 *
 */

public class ShowReport extends BasicView{
	
	private VerticalPanel vPanel = new VerticalPanel();
	private TextBox tb = new TextBox();
	private PasswordTextBox ptb = new PasswordTextBox();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		vPanel.add(tb);
		vPanel.add(ptb);
		
	}
	
	public String getHeadlineText(){
		return "Zum Anzeigen von Reports einloggen";
		// TODO Auto-generated method stub
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}


	
	

}
