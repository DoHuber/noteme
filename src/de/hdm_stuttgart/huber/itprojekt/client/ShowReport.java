package de.hdm_stuttgart.huber.itprojekt.client;


import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Klasse zum Anzeigen von Reports
 * @author dominik erdmann
 *
 */

public class ShowReport extends BasicView{
	
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private TextBox tb = new TextBox();
	private Label loginN = new Label("Admin-Zugangsname");
	private PasswordTextBox ptb = new PasswordTextBox();
	private Label psswrd = new Label("Passwort");
	private Button confirmButton = new Button("Login");
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		vPanel.add(loginN);
		vPanel.add(tb);
		
		vPanel.add(psswrd);
		vPanel.add(ptb);
		
		vPanel.add(confirmButton);

		
		RootPanel.get().add(vPanel);
		
	}
	
	public String getHeadlineText(){
		return "Report Generator";
		// TODO Auto-generated method stub
	}

	@Override
	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return "Bitte anmelden";
	}


	
	

}
