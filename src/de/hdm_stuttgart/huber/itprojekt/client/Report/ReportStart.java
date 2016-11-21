package de.hdm_stuttgart.huber.itprojekt.client.Report;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm_stuttgart.huber.itprojekt.client.BasicView;

/**
 * Startseite des Report Gernerators
 * @author dominik erdmann
 *
 */

public class ReportStart extends BasicView{
	
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
		
		confirmButton.addClickHandler(new LoginHandler());
	}
	/**
	 * "fake" Login, momentan nicht funktionsfähig
	 * @author domin
	 *
	 */
	private class LoginHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ReportStart rpStrt = new ReportStart();
			RootPanel.get().clear();
			RootPanel.get().add(rpStrt);
			
			ShowReportPara shrp = new ShowReportPara();
			//hPanel.add(shrp);
			
		}}
	
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
