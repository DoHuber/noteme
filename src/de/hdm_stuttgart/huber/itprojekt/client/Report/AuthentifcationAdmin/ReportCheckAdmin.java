package de.hdm_stuttgart.huber.itprojekt.client.Report.AuthentifcationAdmin;



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
import de.hdm_stuttgart.huber.itprojekt.client.MenuView;
import de.hdm_stuttgart.huber.itprojekt.client.Report.BasicViewR;
import de.hdm_stuttgart.huber.itprojekt.client.Report.ReportFilter;
import de.hdm_stuttgart.huber.itprojekt.client.Report.ReportLanding;

/**
 * Startseite des Report Gernerators mit Abfrage für Admin Zugang
 * und anschließende Weiterleitung zur Auswahlseite
 * @author dominik erdmann
 *
 */

public class ReportCheckAdmin extends VerticalPanel{
	
	protected void onLoad(){
	
	VerticalPanel vPanel = new VerticalPanel();
	TextBox tb = new TextBox();
	Label loginN = new Label("Admin-Zugangsname");
	PasswordTextBox ptb = new PasswordTextBox();
	Label psswrd = new Label("Passwort");
	Button confirmButton = new Button("Login");
	Button cancelButton = new Button("Abbruch");
	

		vPanel.add(loginN);
		vPanel.add(tb);
		
		vPanel.add(psswrd);
		vPanel.add(ptb);
		
		vPanel.add(confirmButton);
		vPanel.add(cancelButton);
		
		RootPanel.get().add(vPanel);
		
		confirmButton.addClickHandler(new LoginHandler());
		cancelButton.addClickHandler(new CancelHandler());
	}
	/**
	 * "fake" Login, momentan nicht funktionsfähig
	 * @author domin
	 *
	 */
	private class LoginHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			ReportFilter rpF = new ReportFilter();
			RootPanel.get().clear();
			RootPanel.get().add(rpF);
			

			
		}


			
		}

	private class CancelHandler implements ClickHandler {
		
		public void onClick(ClickEvent event) {
			MenuView mV = new MenuView();
			RootPanel.get().clear();
			RootPanel.get().add(mV);
		}
	}
	
	public String getHeadlineText(){
		return "Report Generator";
		// TODO Auto-generated method stub
	}

	public String getSubHeadlineText() {
		// TODO Auto-generated method stub
		return "Bitte anmelden";
	}


	
	

}
